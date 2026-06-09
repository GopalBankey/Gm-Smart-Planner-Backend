package com.gmsmartplanner.dto.request.budget;

import com.gmsmartplanner.enums.todo.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CreatePaymentMethodRequestDTO {

    @NotBlank
    private String name;

    private MultipartFile icon;

    @NotNull
    private CategoryType type;
}