package com.example.minio2.service;


import com.example.minio2.entity.File;
import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import io.minio.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

//    public File uploadFile(File request) {
//        try {
//            minioClient.putObject(PutObjectArgs.builder()
//                    .bucket(minioConfigurationProperties.getBucket())
//                    .object(request.getFile().getOriginalFilename())
//                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
//                    .build());
//        } catch (Exception e) {
//            log.error("Happened error when upload file: ", e);
//        }
//        return File.builder()
//                .title(request.getTitle())
//                .description(request.getDescription())
//                .size(request.getFile().getSize())
//                .url(getPreSignedUrl(request.getFile().getOriginalFilename()))
//                .filename(request.getFile().getOriginalFilename())
//                .build();
//    }

//    public static String saveFile(String fileName, MultipartFile multipartFile)
//            throws IOException {
//        Path uploadPath = Paths.get("Files-Upload");
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        String fileCode = RandomStringUtils.randomAlphanumeric(8);
//
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
//            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException ioe) {
//            throw new IOException("Could not save file: " + fileName, ioe);
//        }
//
//        return fileCode;
//    }
//
//    @GetMapping("/downloadFile/{fileCode}")
//    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
//        File downloadUtil = new File();
//
//        Resource resource = null;
//        try {
//            resource = downloadUtil.getFileAsResource(fileCode);
//        } catch (IOException e) {
//            return ResponseEntity.internalServerError().build();
//        }
//
//        if (resource == null) {
//            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
//        }
//
//        String contentType = "application/octet-stream";
//        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
//                .body(resource);
//    }
    private String getPreSignedUrl(String filename) {
        return "http://localhost:8080/file/".concat(filename);
    }








}
