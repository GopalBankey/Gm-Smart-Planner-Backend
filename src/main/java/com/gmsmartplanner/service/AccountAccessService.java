package com.gmsmartplanner.service;

import com.gmsmartplanner.dto.request.SendAccessOtpRequestDTO;
import com.gmsmartplanner.dto.request.UpdateAccessPermissionRequestDTO;
import com.gmsmartplanner.dto.request.VerifyAccessOtpRequestDTO;
import com.gmsmartplanner.dto.response.AccountAccessResponseDTO;
import com.gmsmartplanner.dto.response.OwnerAccessResponseDTO;

import java.util.List;

public interface
AccountAccessService {

    // =====================================
    // SEND OTP
    // =====================================

    AccountAccessResponseDTO
    sendOtp(

            String username,

            SendAccessOtpRequestDTO dto
    );

    // =====================================
    // RESEND OTP
    // =====================================

    AccountAccessResponseDTO
    resendOtp(

            String username,

            String mobileNumber
    );

    // =====================================
    // VERIFY OTP
    // =====================================

    AccountAccessResponseDTO
    verifyOtp(

            String username,

            VerifyAccessOtpRequestDTO dto
    );

    // =====================================
    // UPDATE PERMISSION
    // =====================================

    AccountAccessResponseDTO
    updatePermission(

            String username,

            Long accessId,

            UpdateAccessPermissionRequestDTO dto
    );

    // =====================================
    // GET MY ACCESS
    // =====================================

    List<AccountAccessResponseDTO>
    getMyAccess(

            String username
    );

    // =====================================
    // REMOVE ACCESS
    // =====================================

    void removeAccess(

            String username,

            Long accessId
    );

    OwnerAccessResponseDTO
    getOwnerAccess(

            String username
    );
}