package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.entity.RefreshToken;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.exception.TokenExpiredException;
import com.gmsmartplanner.exception.TokenNotFoundException;
import com.gmsmartplanner.repository.RefreshTokenRepository;
import com.gmsmartplanner.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenServiceImpl
        implements RefreshTokenService {

    private final RefreshTokenRepository
            refreshTokenRepository;

    private static final long
            REFRESH_TOKEN_EXPIRY_DAYS = 7;

    // =========================================
    // CREATE REFRESH TOKEN
    // =========================================
    @Override
    public RefreshToken create(
            UserAuth auth,
            String token
    ) {

        log.info(
                "Creating refresh token for user id : {}",
                auth.getUser().getId()
        );

        // DELETE OLD TOKEN
        Optional<RefreshToken> existingToken =
                refreshTokenRepository
                        .findByUserAuth(auth);

        existingToken.ifPresent(
                refreshTokenRepository::delete
        );

        // FLUSH DELETE QUERY
        refreshTokenRepository.flush();

        // CREATE NEW TOKEN
        RefreshToken refreshToken =
                RefreshToken.builder()

                        .token(token)

                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(
                                                REFRESH_TOKEN_EXPIRY_DAYS
                                        )
                        )

                        .userAuth(auth)

                        .build();

        RefreshToken savedToken =
                refreshTokenRepository.save(
                        refreshToken
                );

        log.info(
                "Refresh token created successfully for user id : {}",
                auth.getUser().getId()
        );

        return savedToken;
    }

    // =========================================
    // VERIFY REFRESH TOKEN
    // =========================================
    @Override
    public RefreshToken verify(
            String token
    ) {

        log.info("Verifying refresh token");

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(token)
                        .orElseThrow(() -> {

                            log.error(
                                    "Refresh token not found"
                            );

                            return new TokenNotFoundException(
                                    "Refresh token not found"
                            );
                        });

        // CHECK EXPIRY
        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            log.error(
                    "Refresh token expired for user id : {}",
                    refreshToken.getUserAuth()
                            .getUser()
                            .getId()
            );

            refreshTokenRepository.delete(
                    refreshToken
            );

            throw new TokenExpiredException(
                    "Refresh token expired"
            );
        }

        return refreshToken;
    }

    // =========================================
    // DELETE REFRESH TOKEN
    // =========================================
    @Override
    public void deleteByAuth(
            UserAuth auth
    ) {

        log.info(
                "Deleting refresh token for user id : {}",
                auth.getUser().getId()
        );

        refreshTokenRepository
                .deleteByUserAuth(auth);
    }
}