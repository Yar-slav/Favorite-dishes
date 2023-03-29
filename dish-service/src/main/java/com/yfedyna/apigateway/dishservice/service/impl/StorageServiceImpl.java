package com.yfedyna.apigateway.dishservice.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.yfedyna.apigateway.dishservice.dto.LInksToImagesDto;
import com.yfedyna.apigateway.dishservice.model.Dish;
import com.yfedyna.apigateway.dishservice.model.Image;
import com.yfedyna.apigateway.dishservice.service.DishService;
import com.yfedyna.apigateway.dishservice.service.ImageService;
import com.yfedyna.apigateway.dishservice.service.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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

    private final DishService dishService;
    private final ImageService imageService;

    @Transactional
    @Override
    public void addDishImage(List<MultipartFile> files, Long dishId, Long userId) {
        Dish dish = dishService.findDishById(dishId);
        for (MultipartFile file: files) {
            String originalFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            imageService.saveImageToDb(originalFilename, dish);
        String folderName = bucketName + "/dishId_" + dishId;
        File fileObj = convertMultipartFileToFile(file);
//        s3Client.putObject(new PutObjectRequest(folderName, fileName, fileObj)); // закомітив щоб файли не заливались на s3
        }
    }

    @Override
    public LInksToImagesDto generateLinksForDownloadImages(Long dishId, Long userId) {
        Dish dish = dishService.findDishById(dishId);

        // TODO: 3/29/23  : make validation

        List<URL> urls = gatAllNameImagesForDish(dish)
                .stream()
                .map(this::downloadFile)
                .toList();
        return LInksToImagesDto.builder()
                .dishId(dishId)
                .imageUrls(urls)
                .build();

//        return null;
    }

    @Override
    public void deleteAllFilesByDishId(Long dishId) {
        String folderName = "dishId_" + dishId + "/";
        ObjectListing objectListing = s3Client.listObjects(bucketName, folderName);
        while (true) {
            for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
                s3Client.deleteObject(new DeleteObjectRequest(bucketName, summary.getKey()));
            }

            if (objectListing.isTruncated()) {
                objectListing = s3Client.listNextBatchOfObjects(objectListing);
            } else {
                break;
            }
        }
        s3Client.deleteObject(bucketName, folderName);
    }

    @Override
    public void updateDishImage(List<MultipartFile> files, Long dishId, Long userId) {
        deleteAllFilesByDishId(dishId);
        imageService.deleteAllImagesFromDbByDishId(dishId);
        addDishImage(files, dishId, userId);
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertFile;
    }

    private List<String> gatAllNameImagesForDish(Dish dish) {
        return dish.getImages()
                .stream()
                .map(Image::getImage)
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
}
