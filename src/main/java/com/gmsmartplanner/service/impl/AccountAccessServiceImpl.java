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

import java.time.LocalDateTime;
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

    @Override
    @Transactional
    public AccountAccessResponseDTO sendOtp(

            String username,

            SendAccessOtpRequestDTO dto

    ) {

        // ==========================
        // MEMBER
        // ==========================

        User member =
                getUser(username);

        // ==========================
        // COUNTRY CODE
        // ==========================

        String countryCode =

                dto.getCountryCode() == null
                        || dto.getCountryCode().isBlank()

                        ? "+91"

                        : dto.getCountryCode();

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
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        // ==========================
        // SELF ACCESS
        // ==========================

        if (owner.getId().equals(member.getId())) {

            throw new InvalidRequestException(
                    "You cannot add your own account"
            );
        }

        // ==========================
        // EXISTING ACCESS
        // ==========================

        AccountAccess access =

                repository

                        .findByOwnerAndModule(
                                owner,
                                AccessModule.HEALTH
                        )

                        .orElse(null);

        // ==========================
        // ALREADY ADDED
        // ==========================

        if (access != null &&
                Boolean.TRUE.equals(access.getOtpVerified())) {

            throw new InvalidRequestException(
                    "This account has already been added."
            );
        }

        // ==========================
        // USER AUTH
        // ==========================

        UserAuth auth =

                userAuthRepository

                        .findByUser(owner)

                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User auth not found"
                                )
                        );

        // ==========================
        // SEND OTP
        // ==========================

        MobileAuthDTO mobile = new MobileAuthDTO();

        mobile.setCountryCode(countryCode);

        mobile.setMobileNumber(dto.getMobileNumber());

        mobile.setFcmToken(auth.getFcmToken());

        authService.initiateMobileAuth(mobile);

        // ==========================
        // GET GENERATED OTP
        // ==========================

        String otp =

                userAuthRepository

                        .findByUser(owner)

                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "OTP not found"
                                )
                        )

                        .getOtp();

        // ==========================
        // UPDATE EXISTING REQUEST
        // ==========================

        if (access != null) {

            mapper.updateOtp(
                    access,
                    otp,
                    dto.getDisplayName()
            );

            access.setCountryCode(countryCode);

            access.setMember(member);

            repository.save(access);
        }

        // ==========================
        // CREATE NEW REQUEST
        // ==========================

        else {

            access = mapper.toEntity(
                    owner,
                    member,
                    dto,
                    otp
            );

            repository.save(access);
        }

        return mapper.toResponse(access);
    }

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
    public AccountAccessResponseDTO verifyOtp(

            String username,

            VerifyAccessOtpRequestDTO dto

    ) {

        User member =
                getUser(
                        username
                );

        // ==========================
        // OWNER
        // ==========================

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

        // ==========================
        // ACCESS
        // ==========================

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

        // ==========================
        // ALREADY VERIFIED
        // ==========================

        if (

                Boolean.TRUE.equals(
                        access.getOtpVerified()
                )

        ) {

            throw new InvalidRequestException(
                    "Access already verified"
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
        // OTP NOT AVAILABLE
        // ==========================

        if (

                auth.getOtp() == null
                        ||
                        auth.getOtpCreatedAt() == null
        ) {

            throw new InvalidOtpException(
                    "OTP has expired. Please request a new OTP."
            );
        }

        // ==========================
        // OTP EXPIRY - 2 MINUTES
        // ==========================

        LocalDateTime expiryTime =

                auth.getOtpCreatedAt()
                        .plusMinutes(2);

        if (

                !LocalDateTime.now()
                        .isBefore(
                                expiryTime
                        )

        ) {

            auth.setOtp(null);

            auth.setOtpCreatedAt(null);

            auth.setOtpVerified(false);

            userAuthRepository.save(
                    auth
            );

            throw new InvalidOtpException(
                    "OTP has expired. Please request a new OTP."
            );
        }

        // ==========================
        // OTP VALIDATION
        // ==========================

        if (

                dto.getOtp() == null
                        ||
                        dto.getOtp().isBlank()
        ) {

            throw new InvalidOtpException(
                    "OTP is required"
            );
        }

        if (

                !auth.getOtp()
                        .trim()
                        .equals(
                                dto.getOtp()
                                        .trim()
                        )

        ) {

            throw new InvalidOtpException(
                    "Invalid OTP"
            );
        }

        // ==========================
        // VERIFY ACCESS
        // ==========================

        mapper.verify(
                access
        );

        repository.save(
                access
        );

        // ==========================
        // CLEAR OTP
        // ==========================

        auth.setOtp(null);

        auth.setOtpCreatedAt(null);

        userAuthRepository.save(
                auth
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

    // =====================================
// REMOVE ACCESS
// =====================================

    @Override
    @Transactional
    public void removeAccess(

            String username,

            Long accessId

    ) {

        User currentUser =
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

        // =====================================
        // OWNER OR MEMBER
        // =====================================

        boolean isOwner =

                access.getOwner()
                        .getId()
                        .equals(
                                currentUser.getId()
                        );

        boolean isMember =

                access.getMember()
                        .getId()
                        .equals(
                                currentUser.getId()
                        );

        if (
                !isOwner
                        &&
                        !isMember
        ) {

            throw new InvalidRequestException(
                    "You cannot remove this access"
            );
        }

        // =====================================
        // REMOVE ACCESS
        // =====================================

        repository.delete(
                access
        );
    }



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