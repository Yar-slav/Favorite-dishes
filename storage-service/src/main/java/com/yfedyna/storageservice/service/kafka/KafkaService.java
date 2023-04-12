package com.yfedyna.storageservice.service.kafka;

import java.util.List;

public interface KafkaService {

    void sendFileNamesToDishService(List<String> fileNames, Long dishId, String token);

    void sendDishIdToDishServiceForDeleteFiles(Long dishId, String token);
}
