package com.gmsmartplanner.dto.request;

import com.gmsmartplanner.enums.AccessModule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OwnerAccessResponseDTO {

    private Long id;

    private AccessModule module;

    // =====================================
    // MEMBER
    // =====================================

    private Long memberId;

    private String memberName;

    private String memberMobile;

    private String countryCode;

    private String displayName;

    // =====================================
    // VERIFY
    // =====================================

    private Boolean otpVerified;

    // =====================================
    // PERMISSIONS
    // =====================================

    private Boolean viewPermission;

    private Boolean createPermission;

    private Boolean updatePermission;

    private Boolean deletePermission;

    private Boolean takePermission;

    // =====================================
    // ACTIVE
    // =====================================

    private Boolean active;
}