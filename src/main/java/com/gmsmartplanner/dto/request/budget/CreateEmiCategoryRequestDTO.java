package com.gmsmartplanner.dto.request.budget;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreateEmiCategoryRequestDTO {

    @NotBlank
    private String name;

    private MultipartFile icon;
}