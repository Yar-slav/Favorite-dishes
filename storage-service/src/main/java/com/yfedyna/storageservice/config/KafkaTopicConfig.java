package com.yfedyna.storageservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic saveFilesTopic() {
        return TopicBuilder.name("save-files-topic").build();
    }

    @Bean
    public NewTopic deleteFilesTopic() {
        return TopicBuilder.name("delete-files-topic").build();
    }
}
