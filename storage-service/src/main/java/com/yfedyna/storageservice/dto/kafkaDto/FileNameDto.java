package com.yfedyna.storageservice.dto.kafkaDto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileNameDto {

    private List<String> fileNames;
    private Long dishId;
}
