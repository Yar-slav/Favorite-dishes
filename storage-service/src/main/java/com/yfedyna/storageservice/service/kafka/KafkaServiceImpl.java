package com.yfedyna.storageservice.service.kafka;

import com.yfedyna.storageservice.dto.dish.DishResponseDto;
import com.yfedyna.storageservice.dto.kafkaDto.FileNameDto;
import com.yfedyna.storageservice.service.client.DishClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, FileNameDto> saveKafkaTemplate;
    private final KafkaTemplate<String, Long> deleteKafkaTemplate;
    private final DishClient dishClient;

    private final NewTopic saveFilesTopic;
    private final NewTopic deleteFilesTopic;

    @Override
    public void sendFileNamesToDishService(List<String> fileNames, Long dishId, String token) {
        log.info("Sending FileNameDto Json Serializer");
        DishResponseDto dish = getDishResponseDto(dishId, token);

        FileNameDto fileNamesDto = FileNameDto.builder()
                .fileNames(fileNames)
                .dishId(dish.getId())
                .build();
        saveKafkaTemplate.send(saveFilesTopic.name(), fileNamesDto);
        log.info("Send FileNameDto {}", fileNamesDto);
    }

    @Override
    public void sendDishIdToDishServiceForDeleteFiles(Long dishId, String token) {
        log.info("Deleting files by dish id. Sent Json Serializer");
        DishResponseDto dish = getDishResponseDto(dishId, token);
        deleteKafkaTemplate.send(deleteFilesTopic.name(), dish.getId() );
        log.info("Send dishId {}", dish.getId());
    }

    private DishResponseDto getDishResponseDto(Long dishId, String token) {
        DishResponseDto dish;
        try {
            dish = dishClient.getDishById(dishId, token);
        } catch (FeignException feignException) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(feignException.status()), feignException.getMessage());
        }
        return dish;
    }
}

