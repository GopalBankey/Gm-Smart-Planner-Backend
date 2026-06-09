package com.gmsmartplanner.dto.request.health;

import com.gmsmartplanner.enums.BloodGroup;
import com.gmsmartplanner.enums.health.FamilyRelation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class CreateFamilyMemberRequestDTO {

    private MultipartFile image;

    @NotBlank
    private String fullName;

    @NotNull
    private FamilyRelation relation;

    private BloodGroup bloodGroup;

    private String countryCode;

    private String mobileNumber;

    private LocalDate dateOfBirth;

    private Integer age;

    private String notes;
}