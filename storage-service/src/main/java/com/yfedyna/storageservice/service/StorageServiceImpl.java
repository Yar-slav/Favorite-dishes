package com.yfedyna.storageservice.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.yfedyna.storageservice.dto.ImageResponseDto;
import com.yfedyna.storageservice.dto.LInksToImagesDto;
import com.yfedyna.storageservice.dto.dish.DishResponseDto;
import com.yfedyna.storageservice.service.client.DishClient;
import com.yfedyna.storageservice.service.kafka.KafkaService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Value("${bucketName}")
    private String bucketName;

    private final AmazonS3 s3Client;
    private final DishClient dishClient;

    private final KafkaService kafkaService;

    @Transactional
    public void addDishImages(List<MultipartFile> files, Long dishId, String token) {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            fileNames.add(fileName);
//            String folderName = bucketName + "/dishId_" + dishId;
//            File fileObj = convertMultipartFileToFile(file);
//            s3Client.putObject(new PutObjectRequest(folderName, fileName, fileObj));
        }

        kafkaService.sendFileNamesToDishService(fileNames, dishId, token);
    }

    @Override
    public LInksToImagesDto generateLinksForDownloadImages(Long dishId, String token) {
        DishResponseDto dishResponseDto = getDishResponseDto(dishId, token);

        // TODO: 3/29/23  : make validation

        List<URL> urls = gatAllNameImagesForDish(dishResponseDto)
                .stream()
                .map(this::downloadFile)
                .toList();
        return LInksToImagesDto.builder()
                .dishId(dishId)
                .imageUrls(urls)
                .build();
    }

    @Override
    public void deleteAllFilesByDishId(Long dishId, String token) {
//        String folderName = "dishId_" + dishId + "/";
//        ObjectListing objectListing = s3Client.listObjects(bucketName, folderName);
//        while (true) {
//            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
//                s3Client.deleteObject(new DeleteObjectRequest(bucketName, summary.getKey()));
//            }
//
//            if (objectListing.isTruncated()) {
//                objectListing = s3Client.listNextBatchOfObjects(objectListing);
//            } else {
//                break;
//            }
//        }
//        s3Client.deleteObject(bucketName, folderName);
        kafkaService.sendDishIdToDishServiceForDeleteFiles(dishId, token);
    }

    @Transactional
    @Override
    public void updateDishImage(List<MultipartFile> files, Long dishId, String token) {
        deleteAllFilesByDishId(dishId, token);
        addDishImages(files, dishId, token);
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
            throw new ResponseStatusException(HttpStatusCode.valueOf(403),
                    "Error converting multipartFile to file");
        }
        return convertFile;
    }

    private List<String> gatAllNameImagesForDish(DishResponseDto dishResponseDto) {
        return dishResponseDto.getImages()
                .stream()
                .map(ImageResponseDto::getImage)
                .toList();
    }

    private URL downloadFile(String path) {

        // TODO: 3/29/23 add folderName to path

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;

        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, path)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        return s3Client.generatePresignedUrl(generatePresignedUrlRequest);

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
