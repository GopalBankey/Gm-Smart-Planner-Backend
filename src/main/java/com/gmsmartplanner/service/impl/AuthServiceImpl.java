package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.config.JwtService;
import com.gmsmartplanner.dto.request.*;
import com.gmsmartplanner.dto.response.LoginResponseDTO;
import com.gmsmartplanner.entity.RefreshToken;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.enums.LoginType;
import com.gmsmartplanner.exception.InvalidOtpException;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.AuthMapper;
import com.gmsmartplanner.repository.UserAuthRepository;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.service.AuthService;
import com.gmsmartplanner.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository
            userRepository;

    private final UserAuthRepository
            userAuthRepository;

    private final JwtService
            jwtService;

    private final RefreshTokenService
            refreshTokenService;

    private final AuthMapper
            authMapper;

    private static final SecureRandom
            SECURE_RANDOM = new SecureRandom();

    // =========================================
    // MOBILE LOGIN
    // =========================================

    @Override
    public String initiateMobileAuth(
            MobileAuthDTO dto
    ) {

        validateMobileRequest(dto);

        log.info(
                "Initiating mobile login for mobile : {}",
                dto.getMobileNumber()
        );

        String generatedOtp =
                generateOtp();

        Optional<User> existingUser =
                userRepository.findByMobileNumber(
                        dto.getMobileNumber()
                );

        User user;

        UserAuth userAuth;

        // =====================================
        // NEW USER
        // =====================================

        if (existingUser.isEmpty()) {

            log.info(
                    "Creating new mobile user : {}",
                    dto.getMobileNumber()
            );

            user = authMapper.createMobileUser(
                    dto.getMobileNumber()
            );

            user.setProfileCompleted(false);

            user = userRepository.save(user);

            userAuth = authMapper.createUserAuth(

                    user,

                    LoginType.MOBILE,

                    dto.getFcmToken(),

                    null
            );

            userAuth.setOtpVerified(false);

            userAuth.setEmailVerified(false);

            userAuth.setEmailOtpVerified(false);

            userAuth = userAuthRepository.save(
                    userAuth
            );
        }

        // =====================================
        // EXISTING USER
        // =====================================

        else {

            user = existingUser.get();

            userAuth = getUserAuth(user);

            // LOGIN TYPE VALIDATION

            if (userAuth.getLoginType()
                    != LoginType.MOBILE) {

                throw new InvalidRequestException(
                        "This account is registered with Google login"
                );
            }

            // UPDATE FCM TOKEN

            authMapper.updateFcmToken(

                    userAuth,

                    dto.getFcmToken()
            );

            userAuthRepository.save(userAuth);
        }

        // =====================================
        // SAVE OTP
        // =====================================

        userAuth.setOtp(generatedOtp);

        userAuth.setOtpVerified(false);

        userAuthRepository.save(userAuth);

        log.info(
                "OTP generated successfully for mobile : {}",
                dto.getMobileNumber()
        );

        // TODO
        // SEND OTP USING SMS PROVIDER

        // DEV ONLY

        return generatedOtp;
    }

    // =========================================
    // VERIFY MOBILE LOGIN OTP
    // =========================================

    @Override
    public LoginResponseDTO verifyMobileOtp(
            AuthOtpVerifyDTO dto
    ) {

        log.info(
                "Verifying mobile login OTP : {}",
                dto.getMobileNumber()
        );

        User user =
                userRepository.findByMobileNumber(
                                dto.getMobileNumber()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        UserAuth userAuth =
                getUserAuth(user);

        // LOGIN TYPE VALIDATION

//        if (userAuth.getLoginType()
//                != LoginType.MOBILE) {
//
//            throw new InvalidRequestException(
//                    "Invalid login type"
//            );
//        }

        validateOtp(
                userAuth,
                dto.getOtp()
        );

        // =====================================
        // MOBILE VERIFIED
        // =====================================

        userAuth.setLoginType(LoginType.MOBILE);
        userAuth.setOtpVerified(true);

        userAuth.setOtp(null);

        userAuthRepository.save(userAuth);

        return buildLoginResponse(

                user,

                userAuth,

                "Mobile verified successfully"
        );
    }

    // =========================================
    // GOOGLE LOGIN
    // =========================================

    @Override
    public LoginResponseDTO googleLogin(
            GoogleLoginDTO dto
    ) {

        validateGoogleRequest(dto);

        log.info(
                "Google login initiated for email : {}",
                dto.getEmail()
        );

        Optional<User> existingUser =
                userRepository.findByEmail(
                        dto.getEmail()
                );

        User user;

        UserAuth userAuth;

        // =====================================
        // NEW GOOGLE USER
        // =====================================

        if (existingUser.isEmpty()) {

            log.info(
                    "Creating new Google user : {}",
                    dto.getEmail()
            );

            user = authMapper.createGoogleUser(

                    dto.getEmail(),

                    dto.getName(),

                    dto.getProfileImageUrl()
            );

            user.setProfileCompleted(false);

            user = userRepository.save(user);

            userAuth = authMapper.createUserAuth(

                    user,

                    LoginType.GOOGLE,

                    dto.getFcmToken(),

                    dto.getFirebaseUid()
            );

            // GOOGLE EMAIL VERIFIED

            userAuth.setEmailVerified(true);

            // MOBILE VERIFICATION PENDING

            userAuth.setOtpVerified(false);

            userAuth.setEmailOtpVerified(false);

            userAuth = userAuthRepository.save(
                    userAuth
            );
        }

        // =====================================
        // EXISTING USER
        // =====================================

        else {

            user = existingUser.get();

            userAuth = getUserAuth(user);
            userAuth.setLoginType(LoginType.GOOGLE);


            // LOGIN TYPE VALIDATION
//
//            if (userAuth.getLoginType()
//                    != LoginType.GOOGLE) {
//
//                throw new InvalidRequestException(
//                        "This account is registered with mobile login"
//                );
//            }

            // EXTRA SECURITY

            if (!user.getEmail().equals(
                    dto.getEmail()
            )) {

                throw new InvalidRequestException(
                        "Invalid Google account"
                );
            }

            // GOOGLE EMAIL ALWAYS VERIFIED

            if (!userAuth.isEmailVerified()) {

                userAuth.setEmailVerified(true);
            }

            // UPDATE FCM TOKEN

            authMapper.updateFcmToken(

                    userAuth,

                    dto.getFcmToken()
            );

            userAuthRepository.save(userAuth);
        }

        return buildLoginResponse(

                user,

                userAuth,

                "Google login successful"
        );
    }


    // =========================================
// RESEND OTP
// =========================================

    @Override
    public String resendOtp(

            ResendOtpRequestDTO dto

    ) {

        User user =

                userRepository

                        .findByMobileNumber(
                                dto.getMobileNumber()
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "User not found"
                                        )
                        );

        UserAuth userAuth =
                getUserAuth(
                        user
                );

        if (

                userAuth.isOtpVerified()

        ) {

            throw new InvalidRequestException(

                    "Mobile already verified"
            );
        }

        String otp =
                generateOtp();

        userAuth.setOtp(
                otp
        );

        userAuth.setOtpVerified(
                false
        );

        userAuthRepository
                .save(
                        userAuth
                );

        log.info(
                "OTP resent : {}",
                dto.getMobileNumber()
        );

        // TODO SEND SMS

        return otp;
    }

    // =========================================
    // REFRESH TOKEN
    // =========================================

    @Override
    public LoginResponseDTO refreshToken(
            RefreshTokenDTO dto
    ) {

        log.info(
                "Refreshing access token"
        );

        RefreshToken refreshEntity =
                refreshTokenService.verify(
                        dto.getRefreshToken()
                );

        UserAuth auth =
                refreshEntity.getUserAuth();

        User user =
                auth.getUser();

        // UPDATE FCM TOKEN

        authMapper.updateFcmToken(

                auth,

                dto.getFcmToken()
        );

        userAuthRepository.save(auth);

        return buildLoginResponse(

                user,

                auth,

                "Token refreshed successfully"
        );
    }

    // =========================================
    // LOGOUT
    // =========================================

    @Override
    public void logout(
            String refreshToken
    ) {

        RefreshToken refreshEntity =
                refreshTokenService.verify(
                        refreshToken
                );

        UserAuth auth =
                refreshEntity.getUserAuth();

        if (auth.getJwtToken() == null) {

            throw new InvalidRequestException(
                    "User already logged out"
            );
        }

        auth.setJwtToken(null);

        userAuthRepository.save(auth);

        refreshTokenService.deleteByAuth(auth);

        log.info(
                "Logout successful for user id : {}",
                auth.getUser().getId()
        );
    }

    // =========================================
    // GET USER AUTH
    // =========================================

    private UserAuth getUserAuth(
            User user
    ) {

        return userAuthRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User auth not found"
                        )
                );
    }

    // =========================================
    // GENERATE OTP
    // =========================================

    private String generateOtp() {

        int otp =
                SECURE_RANDOM.nextInt(9000)
                        + 1000;

        return String.valueOf(otp);
    }

    // =========================================
    // VALIDATE OTP
    // =========================================

    private void validateOtp(

            UserAuth userAuth,

            String enteredOtp

    ) {

        if (enteredOtp == null
                || enteredOtp.isBlank()) {

            throw new InvalidOtpException(
                    "OTP is required"
            );
        }

        if (userAuth.getOtp() == null
                || !userAuth.getOtp().equals(
                enteredOtp
        )) {

            throw new InvalidOtpException(
                    "Invalid OTP"
            );
        }
    }

    // =========================================
    // VALIDATE MOBILE REQUEST
    // =========================================

    private void validateMobileRequest(
            MobileAuthDTO dto
    ) {

        if (dto.getMobileNumber() == null
                || dto.getMobileNumber().isBlank()) {

            throw new InvalidRequestException(
                    "Mobile number is required"
            );
        }

        if (dto.getFcmToken() == null
                || dto.getFcmToken().isBlank()) {

            throw new InvalidRequestException(
                    "FCM token is required"
            );
        }
    }

    // =========================================
    // VALIDATE GOOGLE REQUEST
    // =========================================

    private void validateGoogleRequest(
            GoogleLoginDTO dto
    ) {

        if (dto.getEmail() == null
                || dto.getEmail().isBlank()) {

            throw new InvalidRequestException(
                    "Email is required"
            );
        }

//        if (dto.getName() == null
//                || dto.getName().isBlank()) {
//
//            throw new InvalidRequestException(
//                    "Name is required"
//            );
//        }

        if (dto.getFcmToken() == null
                || dto.getFcmToken().isBlank()) {

            throw new InvalidRequestException(
                    "FCM token is required"
            );
        }

        if (dto.getFirebaseUid() == null
                || dto.getFirebaseUid().isBlank()) {

            throw new InvalidRequestException(
                    "Firebase UID is required"
            );
        }
    }

    // =========================================
    // BUILD LOGIN RESPONSE
    // =========================================

    private LoginResponseDTO buildLoginResponse(

            User user,

            UserAuth userAuth,

            String message

    ) {

        String identifier =
                user.getId().toString();

        // DELETE OLD TOKENS

        refreshTokenService.deleteByAuth(
                userAuth
        );

        // ACCESS TOKEN

        String accessToken =
                jwtService.generateToken(
                        identifier
                );

        // REFRESH TOKEN

        String refreshToken =
                jwtService.generateRefreshToken(
                        identifier
                );

        RefreshToken refresh =
                refreshTokenService.create(

                        userAuth,

                        refreshToken
                );

        userAuth.setJwtToken(
                accessToken
        );

        userAuthRepository.save(
                userAuth
        );

        log.info(
                "Access token generated for user id : {}",
                user.getId()
        );

        return authMapper.buildLoginResponse(

                accessToken,

                refresh,

                user,

                userAuth,

                message
        );
    }
}