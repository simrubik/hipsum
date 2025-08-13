package com.hipsum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

  private static final String TOPIC = "words.processed";
  private final KafkaTemplate<String, TextInfo> kafkaTemplate;

  public void send(TextInfo response) {
    kafkaTemplate.send(TOPIC, response);
  }
}
