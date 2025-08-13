package com.hipsum.service;

import com.hipsum.exception.HipsumException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class TextInfoServiceImpl implements TextInfoService {

  private static final String HIPSUM_ERROR_CODE = "H1011";
  public static final String HIPSUM_CLIENT_URL = "https://hipsum.co/api/?type=hipster-centric&paras=1";
  private final RestTemplate restTemplate;
  private final KafkaProducerService kafkaProducerService;

  @Override
  public TextInfo getTextInfo(Integer paragraphNumber) {
    long startTime = System.currentTimeMillis();

    List<String> paragraphs = new ArrayList<>();
    List<Long> times = new ArrayList<>();
    List<Integer> sizes = new ArrayList<>();

    for (int i = 0; i < paragraphNumber; i++) {
      try {
        long startRequestTime = System.currentTimeMillis();
        String paragraph = restTemplate.getForObject(HIPSUM_CLIENT_URL, String.class);
        long endRequestTime = System.currentTimeMillis();

        paragraphs.add(paragraph);
        times.add(endRequestTime - startRequestTime);
        sizes.add(paragraph.length());
      } catch (Exception e) {
        throw new HipsumException("Error when getting paragraph from client", e, HIPSUM_ERROR_CODE);
      }
    }

    double avgSize = sizes.stream().mapToInt(i -> i).average().orElse(0.0);
    double avgTime = times.stream().mapToLong(l -> l).average().orElse(0.0);
    long totalTime = System.currentTimeMillis() - startTime;

    TextInfo response = new TextInfo(processMostFrequentWord(paragraphs),
        Double.valueOf(avgSize).longValue(),
        Double.valueOf(avgTime).longValue(), totalTime);
    kafkaProducerService.send(response);
    return response;
  }

  private String processMostFrequentWord(List<String> paragraphs) {
    String allText = String.join(" ", paragraphs);
    String[] words = allText.toLowerCase().replaceAll("[^a-z\\s]", "").split("\\s+");

    Map<String, Long> frequencyMap = Arrays.stream(words)
        .filter(w -> !w.isBlank())
        .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

    String mostFrequent = frequencyMap.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse("");

    return mostFrequent;
  }
}
