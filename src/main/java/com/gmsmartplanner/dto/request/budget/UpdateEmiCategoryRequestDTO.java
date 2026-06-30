package com.gmsmartplanner.dto.request.budget;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateEmiCategoryRequestDTO {

    private String name;

    private MultipartFile icon;
}