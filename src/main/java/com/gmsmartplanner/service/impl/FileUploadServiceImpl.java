package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadServiceImpl
        implements FileUploadService {

    private static final String
            BASE_UPLOAD_DIR = "uploads/";

    private static final long
            MAX_FILE_SIZE = 5 * 1024 * 1024;

    // =====================================
    // UPLOAD IMAGE
    // =====================================

    @Override
    public String uploadImage(

            MultipartFile file,

            String folderName

    ) {

        log.info("Uploading image to file: {}", file.getOriginalFilename());

        validateImage(file);

        try {

            Path uploadPath =
                    Paths.get(
                            BASE_UPLOAD_DIR
                                    + folderName
                    );

            // CREATE DIRECTORY
            if (!Files.exists(uploadPath)) {

                Files.createDirectories(
                        uploadPath
                );
            }

            String originalFileName =
                    file.getOriginalFilename();

            String sanitizedFileName =
                    originalFileName != null

                            ?

                            originalFileName.replaceAll(
                                    "[^a-zA-Z0-9\\.\\-]",
                                    "_"
                            )

                            :

                            "image";

            // FILE NAME
            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + sanitizedFileName;

            Path filePath =
                    uploadPath.resolve(fileName);

            // SAVE FILE
            Files.copy(
                    file.getInputStream(),
                    filePath
            );

            return "/uploads/"
                    + folderName
                    + "/"
                    + fileName;

        } catch (IOException ex) {

            log.error(
                    "Failed to upload image : {}",
                    ex.getMessage()
            );

            throw new InvalidRequestException(
                    "Failed to upload image"
            );
        }
    }

    // =====================================
    // VALIDATE IMAGE
    // =====================================

    private void validateImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {

            throw new InvalidRequestException(
                    "Image file is required"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE) {

            throw new InvalidRequestException(
                    "Image size should not exceed 5 MB"
            );
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null) {

            throw new InvalidRequestException(
                    "Invalid file name"
            );
        }

        String lowerCaseFileName =
                fileName.toLowerCase();

        boolean validExtension =

                lowerCaseFileName.endsWith(".jpg")
                        || lowerCaseFileName.endsWith(".jpeg")
                        || lowerCaseFileName.endsWith(".png")
                        || lowerCaseFileName.endsWith(".webp");

        if (!validExtension) {

            throw new InvalidRequestException(
                    "Only JPG, JPEG, PNG and WEBP images are allowed"
            );
        }

        log.info(
                "File Name : {}, Content Type : {}",
                file.getOriginalFilename(),
                file.getContentType()
        );
    }
}