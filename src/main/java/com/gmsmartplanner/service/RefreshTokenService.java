package com.gmsmartplanner.service;

import com.gmsmartplanner.entity.RefreshToken;
import com.gmsmartplanner.entity.UserAuth;

public interface RefreshTokenService {

    RefreshToken create(
            UserAuth auth,
            String token
    );

    RefreshToken verify(
            String token
    );

    void deleteByAuth(
            UserAuth auth
    );
}