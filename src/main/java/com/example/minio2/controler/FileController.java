package com.example.minio2.controler;

import com.example.minio2.entity.File;
import com.example.minio2.service.FileService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final FileService minioService;

    public FileController(FileService minioService) {
        this.minioService = minioService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getFile() {
        return ResponseEntity.status(HttpStatus.OK).body(minioService.getListObjects());
    }

//    @PostMapping("/uploadFile")
//    public ResponseEntity<File> uploadFile(
//            @RequestParam("file") MultipartFile multipartFile)
//            throws IOException {
//
//        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//        long size = multipartFile.getSize();
//
//        String decode = FileService.saveFile(fileName, multipartFile);
//
//        File response = new File();
//        response.setFilename(fileName);
//        response.setSize(size);
//        response.setUrl("/downloadFile/" + decode);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
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

}

