package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.todo.CategoryType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateCategoryRequestDTO {

    private String name;

    private MultipartFile icon;

    private String colorCode;

    private CategoryType type;
}