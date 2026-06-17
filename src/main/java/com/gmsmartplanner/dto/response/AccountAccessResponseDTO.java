package com.gmsmartplanner.dto.response;

import com.gmsmartplanner.enums.AccessModule;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountAccessResponseDTO {

    // =====================================
    // ACCESS
    // =====================================

    private Long id;

    private AccessModule module;

    // =====================================
    // OWNER
    // =====================================

    private Long ownerId;

    private String ownerName;

    // =====================================
    // DISPLAY
    // =====================================

    private String displayName;

    // =====================================
    // VERIFY
    // =====================================

    private String otp;

    private Boolean otpVerified;

    // =====================================
    // PERMISSIONS
    // =====================================

    private Boolean viewPermission;

    private Boolean createPermission;

    private Boolean updatePermission;

    private Boolean deletePermission;

    private Boolean takePermission;

    private String countryCode;
    private String mobileNumber;

    // =====================================
    // ACTIVE
    // =====================================

    private Boolean active;
}