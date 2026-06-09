package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MobileOnboardingDTO {

    @Size(
            min = 2,
            max = 50,
            message = "Name must be between 2 and 50 characters"
    )
    private String name;

    // OPTIONAL FOR MOBILE LOGIN USER
    @Email(message = "Invalid email format")
    private String email;

    // REQUIRED FOR GOOGLE LOGIN USER
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Mobile number must be exactly 10 digits"
    )
    private String mobileNumber;
}