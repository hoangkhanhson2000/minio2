package com.example.minio2.controler;

import com.example.minio2.entity.File;
import com.example.minio2.service.FileService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final FileService minioService;

    public FileController(FileService minioService) {
        this.minioService = minioService;
    }

    @GetMapping(value = "/get")
    public ResponseEntity<Object> getFile(HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(minioService.getListObjects());
    }
    @GetMapping(value = "/download")
    public ResponseEntity<Object> download(HttpServletRequest request) throws IOException {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());

        return ResponseEntity.status(HttpStatus.OK).body(minioService.getObject(filename));
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<Object> upload(@ModelAttribute File request) {
        return ResponseEntity.ok().body(minioService.uploadFile(request));
    }

}

