package com.gmsmartplanner.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GoogleLoginDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

//    @NotBlank(message = "Name is required")
    @Size(
            min = 2,
            max = 50,
            message = "Name must be between 2 and 50 characters"
    )
    private String name;

    private String profileImageUrl;

    @NotBlank(message = "FCM token is required")
    private String fcmToken;

    @NotBlank(message = "Firebase UID is required")
    private String firebaseUid;
}