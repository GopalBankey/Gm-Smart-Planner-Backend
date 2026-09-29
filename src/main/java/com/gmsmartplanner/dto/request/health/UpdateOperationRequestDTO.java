package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UpdateOperationRequestDTO {

    private String operationName;

    private LocalDate date;

    private Long hospitalId;

    private Long doctorId;

    private String reason;

    private List<MultipartFile> files;
}