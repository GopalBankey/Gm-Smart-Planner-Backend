package com.gmsmartplanner.mapper;

import com.gmsmartplanner.dto.request.SendAccessOtpRequestDTO;
import com.gmsmartplanner.dto.request.UpdateAccessPermissionRequestDTO;
import com.gmsmartplanner.dto.response.AccountAccessResponseDTO;
import com.gmsmartplanner.entity.AccountAccess;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.enums.AccessModule;
import org.springframework.stereotype.Component;

@Component
public class AccountAccessMapper {

    // =====================================
    // RESPONSE
    // =====================================

    public AccountAccessResponseDTO
    toResponse(

            AccountAccess access

    ) {

        return AccountAccessResponseDTO

                .builder()

                .id(
                        access.getId()
                )

                .module(
                        access.getModule()
                )

                .otp(access.getOtp())

                // =========================
                // OWNER
                // =========================

                .ownerId(

                        access
                                .getOwner()
                                .getId()
                )

                .ownerName(

                        access
                                .getOwner()
                                .getName()
                )

                // =========================
                // DISPLAY
                // =========================

                .displayName(
                        access.getDisplayName()
                )

                // =========================
                // VERIFY
                // =========================

                .otpVerified(
                        access.getOtpVerified()
                )

                // =========================
                // PERMISSIONS
                // =========================

                .viewPermission(
                        access.getViewPermission()
                )

                .createPermission(
                        access.getCreatePermission()
                )

                .updatePermission(
                        access.getUpdatePermission()
                )

                .deletePermission(
                        access.getDeletePermission()
                )

                .takePermission(
                        access.getTakePermission()
                )

                // =========================
                // ACTIVE
                // =========================

                .active(
                        access.getActive()
                )

                .build();
    }

    // =====================================
// CREATE ACCESS
// =====================================

    public AccountAccess
    toEntity(

            User owner,

            User member,

            SendAccessOtpRequestDTO dto,

            String otp

    ) {

        AccountAccess access =
                new AccountAccess();

        access.setOwner(
                owner
        );

        access.setMember(
                member
        );

        access.setDisplayName(
                dto.getDisplayName()
        );

        access.setOtp(
                otp
        );

        access.setOtpVerified(
                false
        );

        access.setModule(
                AccessModule.HEALTH
        );

        access.setActive(
                true
        );

        return access;
    }

// =====================================
// UPDATE OTP
// =====================================

    public void
    updateOtp(

            AccountAccess access,

            String otp,

            String displayName

    ) {

        access.setOtp(
                otp
        );

        access.setDisplayName(
                displayName
        );

        access.setOtpVerified(
                false
        );
    }

// =====================================
// VERIFY
// =====================================

    public void
    verify(

            AccountAccess access

    ) {

        access.setOtpVerified(
                true
        );
    }

// =====================================
// PERMISSION
// =====================================

    public void
    updatePermission(

            AccountAccess access,

            UpdateAccessPermissionRequestDTO dto

    ) {

        access.setViewPermission(
                dto.getViewPermission()
        );

        access.setCreatePermission(
                dto.getCreatePermission()
        );

        access.setUpdatePermission(
                dto.getUpdatePermission()
        );

        access.setDeletePermission(
                dto.getDeletePermission()
        );

        access.setTakePermission(
                dto.getTakePermission()
        );
    }
}