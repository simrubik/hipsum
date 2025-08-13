package com.hipsum.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ServiceConfig {

  @Bean
  public NewTopic wordsProcessedTopic() {
    return TopicBuilder.name("words.processed")
        .partitions(1)
        .replicas(1)
        .build();
  }

}
