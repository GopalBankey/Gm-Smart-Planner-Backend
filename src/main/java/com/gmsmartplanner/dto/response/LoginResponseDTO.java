package com.gmsmartplanner.dto.response;

import com.gmsmartplanner.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String accessToken;

    private String refreshToken;

    private String message;

    private UserResponseDTO user;
}