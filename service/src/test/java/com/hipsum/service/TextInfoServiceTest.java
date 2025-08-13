package com.hipsum.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hipsum.exception.HipsumException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class TextInfoServiceTest {

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private KafkaProducerService kafkaProducerService;

  @InjectMocks
  private TextInfoServiceImpl service;


  @Test
  void getTextInfo_successfully() {
    when(restTemplate.getForObject(anyString(), any()))
        .thenReturn("This is my test text paragraph, for the text hipsun application");

    TextInfo response = service.getTextInfo(1);

    assertEquals("text", response.getFrequentWord());
    verify(kafkaProducerService).send(response);
  }

  @Test
  void getTextInfo_exception() {
    when(restTemplate.getForObject(anyString(), any())).thenThrow(new RestClientException(""));

    var exception = assertThrows(HipsumException.class,
        () -> service.getTextInfo(1));

    assertThat(exception.getMessage()).isEqualTo("Error when getting paragraph from client");
    assertThat(exception.getErrorCode()).isEqualTo("H1011");
  }
}
