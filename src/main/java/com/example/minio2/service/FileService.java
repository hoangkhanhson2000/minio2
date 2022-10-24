package com.example.minio2.service;


import com.example.minio2.entity.File;
import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import io.minio.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService {
    private final MinioClient minioClient;

    private final MinioConfigurationProperties minioConfigurationProperties;

    public FileService(MinioClient minioClient, MinioConfigurationProperties minioConfigurationProperties) {
        this.minioClient = minioClient;
        this.minioConfigurationProperties = minioConfigurationProperties;
    }

    public List<File> getListObjects() {
        List<File> objects = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .recursive(true)
                    .build());
            for (Result<Item> item : result) {
                objects.add(File.builder()
                        .filename(item.get().objectName())
                        .size(item.get().size())
                        .url(getPreSignedUrl(item.get().objectName()))
                        .build());
            }
            return objects;
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
        }

        return objects;
    }

    private String getPreSignedUrl(String filename) {
        return "http://localhost:8080/file/".concat(filename);
    }

    public InputStream getObject(String filename) {
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(filename)
                    .build());
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
            return null;
        }

        return stream;
    }

    public File uploadFile(File request) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfigurationProperties.getBucket())
                    .object(request.getFile().getOriginalFilename())
                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                    .build());
        } catch (Exception e) {
            log.error("Happened error when upload file: ", e);
        }
        return File.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .size(request.getFile().getSize())
                .url(getPreSignedUrl(request.getFile().getOriginalFilename()))
                .filename(request.getFile().getOriginalFilename())
                .build();
    }

}
