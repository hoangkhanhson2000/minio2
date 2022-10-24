package com.example.minio2.controler;

import com.example.minio2.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final FileService minioService;

    public FileController(FileService minioService) {
        this.minioService = minioService;
    }
//
    @GetMapping("/get-list")
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok(minioService.getListObjects());
    }
    //
    @GetMapping(value = "/get")
    public ResponseEntity<Object> getFile(HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(minioService.getListObjects());
    }

//    @PostMapping(value = "/upload")
//    public ResponseEntity<Object> upload(@ModelAttribute File request) {
//        return ResponseEntity.ok().body(minioService.uploadFile(request));
//    }

}

