package com.gmsmartplanner.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    // =====================================
    // UPLOAD IMAGE
    // =====================================

    String uploadImage(

            MultipartFile file,

            String folderName
    );
}