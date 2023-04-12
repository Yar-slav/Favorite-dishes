package com.yfedyna.dishservice.dto.kafkaDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileNameDto {

    private List<String> fileNames;
    private Long dishId;
}
