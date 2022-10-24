package com.example.minio2.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class File implements Serializable {

    private static final long serialVersionUID = 232836038145089522L;

    private String title;

    private String description;

    @SuppressWarnings("java:S1948")
    private MultipartFile file;

    private String url;

    private Long size;

    private String filename;

//    private Path foundFile;
//
//    public Resource getFileAsResource(String fileCode) throws IOException {
//        Path dirPath = Paths.get("Files-Upload");
//
//        Files.list(dirPath).forEach(file -> {
//            if (file.getFileName().toString().startsWith(fileCode)) {
//                foundFile = file;
//                return;
//            }
//        });
//
//        if (foundFile != null) {
//            return new UrlResource(foundFile.toUri());
//        }
//
//        return null;
//    }
}
