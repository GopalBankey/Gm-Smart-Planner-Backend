package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.dto.request.EmailOtpVerifyDTO;
import com.gmsmartplanner.dto.request.MobileOnboardingDTO;
import com.gmsmartplanner.dto.request.OtpVerifyDTO;
import com.gmsmartplanner.dto.request.UpdateProfileDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.enums.LoginType;
import com.gmsmartplanner.exception.DuplicateResourceException;
import com.gmsmartplanner.exception.InvalidOtpException;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.UserMapper;
import com.gmsmartplanner.repository.UserAuthRepository;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.service.EmailService;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl
        implements UserService {

    private final UserRepository
            userRepository;

    private final UserAuthRepository
            userAuthRepository;

    private final EmailService
            emailService;

    private final UserMapper
            userMapper;

    private final FileUploadService
            fileUploadService;

    private static final SecureRandom
            SECURE_RANDOM = new SecureRandom();

    private final UserHelperService
            userHelperService;

    // =========================================
    // START ONBOARDING
    // =========================================

    @Override
    public String startOnboarding(

            String username,

            MobileOnboardingDTO dto,

            MultipartFile imageFile

    ) {

        log.info(
                "Starting onboarding for user : {}",
                username
        );

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        UserAuth userAuth =
                getUserAuth(user);

        // =====================================
        // PROFILE ALREADY COMPLETED
        // =====================================

        if (user.isProfileCompleted()) {

            throw new InvalidRequestException(
                    "Profile already completed"
            );
        }

        // =====================================
        // VALIDATIONS
        // =====================================

        validateOnboardingRequest(

                user,

                userAuth,

                dto
        );

        // =====================================
        // UPDATE USER DETAILS
        // =====================================

        userMapper.updateOnboardingDetails(

                user,

                userAuth,

                dto
        );

        // =====================================
        // PROFILE IMAGE
        // =====================================

        if (imageFile != null
                && !imageFile.isEmpty()) {

            String imageUrl =
                    fileUploadService
                            .uploadImage(

                                    imageFile,

                                    "profiles"
                            );

            user.setProfileImageUrl(
                    imageUrl
            );
        }

        // =====================================
        // MOBILE LOGIN FLOW
        // =====================================

        if (userAuth.getLoginType()
                == LoginType.MOBILE) {

            // EMAIL NOT PROVIDED

            if (dto.getEmail() == null
                    || dto.getEmail().isBlank()) {

                user.setProfileCompleted(true);

                userRepository.save(user);

                return "Profile completed successfully";
            }

            // EMAIL OTP FLOW

            String emailOtp =
                    generateOtp();

            userAuth.setEmailOtp(
                    emailOtp
            );

            userAuth.setEmailOtpVerified(
                    false
            );

            userAuth.setEmailVerified(
                    false
            );

            userRepository.save(user);

            userAuthRepository.save(userAuth);

            log.info(
                    "Email OTP generated for user : {}",
                    user.getId()
            );

            // SEND EMAIL OTP

            emailService.sendOtpEmail(

                    user.getEmail(),

                    emailOtp
            );

            return "Email OTP sent successfully";
        }

        // =====================================
        // GOOGLE LOGIN FLOW
        // =====================================

        if (userAuth.getLoginType()
                == LoginType.GOOGLE) {

            String otp =
                    generateOtp();

            userAuth.setOtp(otp);

            userAuth.setOtpVerified(false);

            userRepository.save(user);

            userAuthRepository.save(userAuth);

            log.info(
                    "Mobile OTP generated for user : {}",
                    user.getId()
            );

            // TODO
            // SEND SMS OTP

            // DEV ONLY

            return otp;
        }

        throw new InvalidRequestException(
                "Invalid login type"
        );
    }

    // =========================================
    // VERIFY MOBILE OTP
    // =========================================

    @Override
    public User verifyMobileOtp(

            String username,

            OtpVerifyDTO dto

    ) {

        log.info(
                "Verifying mobile OTP for user : {}",
                username
        );

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        UserAuth userAuth =
                getUserAuth(user);

        // ONLY GOOGLE USER

        if (userAuth.getLoginType()
                != LoginType.GOOGLE) {

            throw new InvalidRequestException(
                    "Mobile verification not required"
            );
        }

        // ALREADY VERIFIED

        if (userAuth.isOtpVerified()) {

            throw new InvalidRequestException(
                    "Mobile already verified"
            );
        }

        validateOtp(

                userAuth,

                dto.getOtp()
        );

        userAuth.setOtpVerified(true);

        userAuth.setOtp(null);

        // PROFILE COMPLETED

        if (userAuth.isEmailVerified()) {

            user.setProfileCompleted(true);
        }

        userAuthRepository.save(userAuth);

        return userRepository.save(user);
    }

    // =========================================
    // VERIFY EMAIL OTP
    // =========================================

    @Override
    public User verifyEmailOtp(

            String username,

            EmailOtpVerifyDTO dto

    ) {

        log.info(
                "Verifying email OTP for user : {}",
                username
        );

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        UserAuth userAuth =
                getUserAuth(user);

        // ONLY MOBILE USER

        if (userAuth.getLoginType()
                != LoginType.MOBILE) {

            throw new InvalidRequestException(
                    "Email verification not required"
            );
        }

        // ALREADY VERIFIED

        if (userAuth.isEmailOtpVerified()) {

            throw new InvalidRequestException(
                    "Email already verified"
            );
        }

        // VALIDATE EMAIL OTP

        if (dto.getOtp() == null
                || dto.getOtp().isBlank()) {

            throw new InvalidOtpException(
                    "OTP is required"
            );
        }

        if (userAuth.getEmailOtp() == null
                || !userAuth.getEmailOtp().equals(
                dto.getOtp()
        )) {

            throw new InvalidOtpException(
                    "Invalid email OTP"
            );
        }

        userAuth.setEmailOtpVerified(true);

        userAuth.setEmailVerified(true);

        userAuth.setEmailOtp(null);

        user.setProfileCompleted(true);

        userAuthRepository.save(userAuth);

        return userRepository.save(user);
    }

    // =========================================
    // GET LOGGED-IN USER
    // =========================================

    @Override
    public User getLoggedInUser(
            String username
    ) {

        return   userHelperService
                .getCurrentUser(
                        username
                );
    }

    // =========================================
    // UPDATE PROFILE
    // =========================================

    @Override
    public User updateProfile(

            String username,

            UpdateProfileDTO dto,

            MultipartFile imageFile

    ) {

        log.info(
                "Updating profile for user : {}",
                username
        );

        User user =
                  userHelperService
                .getCurrentUser(
                        username
                );

        // =====================================
        // AGE VALIDATION
        // =====================================

        if (dto.getAge() != null
                && dto.getAge() < 0) {

            throw new InvalidRequestException(
                    "Invalid age"
            );
        }

        // =====================================
        // UPDATE PROFILE
        // =====================================

        userMapper.updateProfile(
                user,
                dto
        );

        // =====================================
        // UPDATE PROFILE IMAGE
        // =====================================

        if (imageFile != null
                && !imageFile.isEmpty()) {

            String imageUrl =
                    fileUploadService
                            .uploadImage(

                                    imageFile,

                                    "profiles"
                            );

            user.setProfileImageUrl(
                    imageUrl
            );
        }

        User updatedUser =
                userRepository.save(user);

        log.info(
                "Profile updated successfully for user : {}",
                updatedUser.getId()
        );

        return updatedUser;
    }

    // =========================================
    // VALIDATE ONBOARDING REQUEST
    // =========================================

    private void validateOnboardingRequest(

            User user,

            UserAuth userAuth,

            MobileOnboardingDTO dto

    ) {

        // NAME REQUIRED

        if (dto.getName() == null
                || dto.getName().isBlank()) {

            throw new InvalidRequestException(
                    "Name is required"
            );
        }

        // MOBILE LOGIN USER

        if (userAuth.getLoginType()
                == LoginType.MOBILE) {

            if (dto.getMobileNumber() != null
                    && !dto.getMobileNumber().isBlank()) {

                throw new InvalidRequestException(
                        "Mobile number cannot be changed"
                );
            }
        }

        // GOOGLE LOGIN USER

        if (userAuth.getLoginType()
                == LoginType.GOOGLE) {

            if (dto.getEmail() != null
                    && !dto.getEmail().equals(
                    user.getEmail()
            )) {

                throw new InvalidRequestException(
                        "Email cannot be changed"
                );
            }

            if (dto.getMobileNumber() == null
                    || dto.getMobileNumber().isBlank()) {

                throw new InvalidRequestException(
                        "Mobile number is required"
                );
            }
        }

        // DUPLICATE EMAIL

        if (dto.getEmail() != null
                && !dto.getEmail().isBlank()
                && userRepository.existsByEmail(
                dto.getEmail()
        )) {

            if (user.getEmail() == null
                    || !user.getEmail().equals(
                    dto.getEmail()
            )) {

                throw new DuplicateResourceException(
                        "Email already registered"
                );
            }
        }

        // DUPLICATE MOBILE

        if (dto.getMobileNumber() != null
                && userRepository
                .existsByCountryCodeAndMobileNumber(

                        dto.getCountryCode() == null
                                ||

                                dto.getCountryCode().isBlank()

                                ?

                                "+91"

                                :

                                dto.getCountryCode(),

                        dto.getMobileNumber()
                )) {

            if (user.getMobileNumber() == null
                    || !user.getMobileNumber().equals(
                    dto.getMobileNumber()
            )) {

                throw new DuplicateResourceException(
                        "Mobile number already registered"
                );
            }
        }
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
    // GENERATE OTP
    // =========================================

    private String generateOtp() {

        int otp =
                SECURE_RANDOM.nextInt(9000)
                        + 1000;

        return String.valueOf(otp);
    }
}