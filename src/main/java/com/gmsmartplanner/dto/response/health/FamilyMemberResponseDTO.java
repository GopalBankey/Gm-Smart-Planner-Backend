package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.BloodGroup;
import com.gmsmartplanner.enums.health.FamilyRelation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class FamilyMemberResponseDTO {

    private Long id;

    private String profileImage;

    private String fullName;

    private FamilyRelation relation;

    private BloodGroup bloodGroup;

    private String countryCode;

    private String mobileNumber;

    private LocalDate dateOfBirth;

    private Integer age;

    private String notes;
}