package com.gmsmartplanner.repository;

import com.gmsmartplanner.entity.RefreshToken;
import com.gmsmartplanner.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(
            String token
    );

    void deleteByUserAuth(
            UserAuth userAuth
    );
    Optional<RefreshToken> findByUserAuth(
            UserAuth userAuth
    );
}