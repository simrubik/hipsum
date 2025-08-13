package com.hipsum.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

  @Mock
  private KafkaTemplate<String, TextInfo> kafkaTemplate;

  @InjectMocks
  private KafkaProducerService producerService;

  @Test
  void sendTest() {
    TextInfo textInfo = new TextInfo("test-message", 1L, 1L, 1L);
    producerService.send(textInfo);

    verify(kafkaTemplate, times(1)).send("words.processed", textInfo);
  }
}
