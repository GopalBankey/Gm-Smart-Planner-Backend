package com.gmsmartplanner.service.impl;

import com.gmsmartplanner.dto.request.*;
import com.gmsmartplanner.dto.response.AccountAccessResponseDTO;
import com.gmsmartplanner.dto.response.OwnerAccessResponseDTO;
import com.gmsmartplanner.entity.AccountAccess;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.AccessModule;
import com.gmsmartplanner.exception.InvalidOtpException;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.AccountAccessMapper;
import com.gmsmartplanner.repository.AccountAccessRepository;
import com.gmsmartplanner.repository.UserRepository;
import com.gmsmartplanner.service.AccountAccessService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.entity.UserAuth;
import com.gmsmartplanner.repository.UserAuthRepository;
import com.gmsmartplanner.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountAccessServiceImpl
        implements AccountAccessService {

    private final AccountAccessRepository
            repository;

    private final UserRepository
            userRepository;

    private final UserHelperService
            userHelperService;

    private final AccountAccessMapper
            mapper;

    private final AuthService
            authService;

    private final UserAuthRepository
            userAuthRepository;

    // =====================================
    // SEND OTP
    // =====================================

    // =====================================
// SEND OTP
// =====================================

    @Override
    @Transactional
    public AccountAccessResponseDTO
    sendOtp(

            String username,

            SendAccessOtpRequestDTO dto

    ) {

        // ==========================
        // MEMBER
        // ==========================

        User member =
                getUser(
                        username
                );

        // ==========================
        // COUNTRY CODE
        // ==========================

        String countryCode =

                dto.getCountryCode() == null
                        ||

                        dto.getCountryCode().isBlank()

                        ?

                        "+91"

                        :

                        dto.getCountryCode();

        // ==========================
        // OWNER
        // ==========================

        User owner =

                userRepository

                        .findByCountryCodeAndMobileNumber(

                                countryCode,

                                dto.getMobileNumber()

                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "User not found"
                                        )
                        );

        // ==========================
        // SELF ACCESS
        // ==========================

        if (

                owner
                        .getId()

                        .equals(

                                member
                                        .getId()
                        )

        ) {

            throw new InvalidRequestException(

                    "You cannot add your own account"
            );
        }

        // ==========================
        // EXISTING ACCESS
        // ==========================

        AccountAccess access =

                repository

                        .findByOwnerAndMemberAndModule(

                                owner,

                                member,

                                AccessModule
                                        .HEALTH
                        )

                        .orElse(
                                null
                        );

        // ==========================
        // ALREADY VERIFIED
        // ==========================

        if (

                access != null

                        &&

                        Boolean.TRUE.equals(

                                access
                                        .getOtpVerified()
                        )

        ) {

            throw new InvalidRequestException(

                    "You already have access to this account"
            );
        }

        // ==========================
        // USER AUTH
        // ==========================

        UserAuth auth =

                userAuthRepository

                        .findByUser(
                                owner
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "User auth not found"
                                        )
                        );

        // ==========================
        // SEND OTP
        // ==========================

        MobileAuthDTO mobile =
                new MobileAuthDTO();

        mobile.setCountryCode(
                countryCode
        );

        mobile.setMobileNumber(
                dto.getMobileNumber()
        );

        mobile.setFcmToken(

                auth.getFcmToken()
        );

        authService
                .initiateMobileAuth(
                        mobile
                );

        // ==========================
        // GET GENERATED OTP
        // ==========================

        String otp =

                userAuthRepository

                        .findByUser(
                                owner
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "OTP not found"
                                        )
                        )

                        .getOtp();

        // ==========================
        // UPDATE
        // ==========================

        if (

                access != null

        ) {

            mapper.updateOtp(

                    access,

                    otp,

                    dto.getDisplayName()
            );

            access.setCountryCode(
                    countryCode
            );

            repository.save(
                    access
            );
        }

        // ==========================
        // CREATE
        // ==========================

        else {

            access =

                    mapper.toEntity(

                            owner,

                            member,

                            dto,

                            otp
                    );

            repository.save(
                    access
            );
        }

        return mapper.toResponse(
                access
        );
    }
    // =====================================
    // RESEND OTP
    // =====================================
    @Override
    public AccountAccessResponseDTO
    resendOtp(

            String username,

            String mobileNumber

    ) {

        User member =
                getUser(
                        username
                );

        User owner =

                userRepository

                        .findByMobileNumber(
                                mobileNumber
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "User not found"
                                        )
                        );

        AccountAccess access =

                repository

                        .findByOwnerAndMemberAndModuleAndActiveTrue(

                                owner,

                                member,

                                AccessModule.HEALTH
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "Access request not found"
                                        )
                        );

        if (

                Boolean.TRUE.equals(

                        access
                                .getOtpVerified()
                )

        ) {

            throw new InvalidRequestException(

                    "Access already verified"
            );
        }

        String otp =

                authService

                        .resendOtp(

                                buildResendDto(
                                        mobileNumber
                                )
                        );

        mapper.updateOtp(

                access,

                otp,

                access.getDisplayName()
        );

        repository.save(
                access
        );

        return mapper.toResponse(
                access
        );
    }

    // =====================================
    // VERIFY OTP
    // =====================================

    @Override
    public AccountAccessResponseDTO
    verifyOtp(

            String username,

            VerifyAccessOtpRequestDTO dto

    ) {

        User member =
                getUser(
                        username
                );

        User owner =

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

        AccountAccess access =

                repository

                        .findByOwnerAndMemberAndModuleAndActiveTrue(

                                owner,

                                member,

                                AccessModule.HEALTH
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "Access not found"
                                        )
                        );

        if (

                Boolean.TRUE.equals(

                        access.getOtpVerified()
                )

        ) {

            throw new InvalidRequestException(

                    "Access already verified"
            );
        }

        if (

                access.getOtp() == null

                        ||

                        !access
                                .getOtp()
                                .trim()

                                .equals(

                                        dto
                                                .getOtp()
                                                .trim()
                                )

        ) {

            throw new InvalidOtpException(

                    "Invalid OTP"
            );
        }

        mapper.verify(
                access
        );

        repository.save(
                access
        );

        return mapper.toResponse(
                access
        );
    }

// =====================================
// UPDATE PERMISSION
// =====================================

    @Override
    public AccountAccessResponseDTO
    updatePermission(

            String username,

            Long accessId,

            UpdateAccessPermissionRequestDTO dto

    ) {

        User owner =
                getUser(
                        username
                );

        AccountAccess access =
                getAccess(
                        accessId
                );

        // ONLY OWNER

        if (

                !access
                        .getOwner()
                        .getId()
                        .equals(
                                owner.getId()
                        )

        ) {

            throw new InvalidRequestException(

                    "Only owner can update permission"
            );
        }

        // MEMBER MUST VERIFY OTP

        if (

                !Boolean.TRUE.equals(

                        access
                                .getOtpVerified()
                )

        ) {

            throw new InvalidRequestException(

                    "Member must verify OTP first"
            );
        }

        mapper.updatePermission(

                access,

                dto
        );

        repository.save(
                access
        );

        return mapper.toResponse(
                access
        );
    }
    // =====================================
    // MY ACCESS
    // =====================================

// =====================================
// MY ACCESS
// =====================================
@Override
@Transactional(
        readOnly = true
)
public List<AccountAccessResponseDTO>
getMyAccess(

        String username

) {

    User member =
            getUser(
                    username
            );

    return repository

            .findAllByMemberAndOtpVerifiedTrue(

                    member
            )

            .stream()

            .map(

                    mapper
                            ::toResponse
            )

            .toList();
}

    @Override
    @Transactional(
            readOnly = true
    )
    public OwnerAccessResponseDTO
    getOwnerAccess(

            String username

    ) {

        User owner =
                getUser(
                        username
                );

        AccountAccess access =

                repository

                        .findByOwnerAndModuleAndOtpVerifiedTrue(

                                owner,

                                AccessModule
                                        .HEALTH
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "No access found"
                                        )
                        );

        return mapper.toOwnerResponse(
                access
        );
    }

    // =====================================
    // REMOVE
    // =====================================


    @Override
    @Transactional
    public void
    removeAccess(

            String username,

            Long accessId

    ) {

        User owner =
                getUser(
                        username
                );

        AccountAccess access =

                repository

                        .findById(
                                accessId
                        )

                        .orElseThrow(

                                () ->

                                        new ResourceNotFoundException(

                                                "Access not found"
                                        )
                        );

        if (

                !access
                        .getOwner()
                        .getId()

                        .equals(

                                owner
                                        .getId()
                        )

        ) {

            throw new InvalidRequestException(

                    "You cannot remove this access"
            );
        }

        repository.delete(
                access
        );
    }
    // =====================================
    // ENTITY
    // =====================================

    private User
    getUser(

            String username

    ) {

        return userHelperService
                .getCurrentUser(
                        username
                );
    }

    private AccountAccess
    getAccess(

            Long id

    ) {

        return repository
                .findById(
                        id
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "Access not found"
                                )
                );
    }

    private ResendOtpRequestDTO
    buildResendDto(

            String mobile

    ) {

        ResendOtpRequestDTO dto =
                new ResendOtpRequestDTO();

        dto.setMobileNumber(
                mobile
        );

        return dto;
    }

}