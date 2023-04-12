package com.yfedyna.dishservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfedyna.dishservice.dto.kafkaDto.FileNameDto;
import com.yfedyna.dishservice.model.Dish;
import com.yfedyna.dishservice.service.DishService;
import com.yfedyna.dishservice.service.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final ImageService imageService;
    private final DishService dishService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    @KafkaListener(topics = "save-files-topic", groupId = "group_id_1")
    public void saveFileName(String data) {
        log.info("Getting FileNameDto {}", data);
        try {
            FileNameDto fileNameDto = objectMapper.readValue(data, FileNameDto.class);
            Dish dish = dishService.findDishById(fileNameDto.getDishId());
            imageService.saveImages(fileNameDto.getFileNames(), dish);
        } catch (JsonProcessingException e) {
            log.info("Incorrect values: {}", data);
        }
    }

    @Override
    @Transactional
    @KafkaListener(topics = "delete-files-topic", groupId = "group_id_1")
    public void deleteFileNames(String data) {
        log.info("Deleting files from db {}", data);
        try {
            Long dishId = objectMapper.readValue(data, Long.class);
            imageService.deleteAllImagesFromDbByDishId(dishId);
        } catch (JsonProcessingException e) {
            log.info("Incorrect values: {}", data);
        }
    }

//    @KafkaListener(topics = "save-files-topic", groupId = "group_id_1")
//    void listener(String data) {
//        log.info("Getting FileNameDto {}", data);
//
//        FileNameDto fileNameDto = null;
//        try {
//            fileNameDto = objectMapper.readValue(data, FileNameDto.class);
//        } catch (JsonProcessingException e) {
//            log.info("Incorrect values: {}", data);
//        }
//        System.out.println("Received data: "+ fileNameDto);
//    }
}
