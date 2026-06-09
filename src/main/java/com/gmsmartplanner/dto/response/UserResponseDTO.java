package com.gmsmartplanner.dto.response;

import com.gmsmartplanner.enums.BloodGroup;
import com.gmsmartplanner.enums.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String mobileNumber;

    private String profileImageUrl;

    private LocalDate dob;

    private Integer age;

    private BloodGroup bloodGroup;

    private String note;

    private boolean profileCompleted;

    // FROM USER AUTH
    private LoginType loginType;

    private boolean emailVerified;

    private boolean mobileVerified;

    private String firebaseUid;

    private LocalDateTime createdAt;
}