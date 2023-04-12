package com.yfedyna.dishservice.service.kafka;

public interface KafkaService {

    void saveFileName(String data);
    void deleteFileNames(String data);
}
