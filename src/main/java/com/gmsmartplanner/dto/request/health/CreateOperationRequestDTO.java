package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CreateOperationRequestDTO {

    @NotBlank
    private String operationName;

    @NotNull
    private LocalDate date;

    @NotNull
    private Long hospitalId;

    @NotNull
    private Long doctorId;

    private String reason;

    private List<MultipartFile> files;
}