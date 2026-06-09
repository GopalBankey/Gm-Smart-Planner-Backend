package com.gmsmartplanner.dto.request;

import com.gmsmartplanner.enums.BloodGroup;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileDTO {

    private String name;

    private LocalDate dob;

    private Integer age;

    private BloodGroup bloodGroup;

    private String note;
}