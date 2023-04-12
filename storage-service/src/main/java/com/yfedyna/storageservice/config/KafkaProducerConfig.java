package com.yfedyna.storageservice.config;

import com.yfedyna.storageservice.dto.kafkaDto.FileNameDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value(("${spring.kafka.bootstrap-servers}"))
    private String bootstrapServers;

    public Map<String, Object> producerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    // FileNameDto
    @Bean
    public ProducerFactory<String, FileNameDto> producerFactoryFileNameDto() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, FileNameDto> saveKafkaTemplate(
            ProducerFactory<String, FileNameDto> producerFactoryFileNameDto
    ) {
        return new KafkaTemplate<>(producerFactoryFileNameDto);
    }


    // Long
    @Bean
    public ProducerFactory<String, Long> producerFactoryLong() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, Long> deleteKafkaTemplate(
            ProducerFactory<String, Long> producerFactoryLong
    ) {
        return new KafkaTemplate<>(producerFactoryLong);
    }
}
