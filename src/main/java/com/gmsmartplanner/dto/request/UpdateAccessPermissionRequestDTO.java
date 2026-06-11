package com.gmsmartplanner.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccessPermissionRequestDTO {

    // =====================================
    // VIEW
    // =====================================

    private Boolean viewPermission =
            true;

    // =====================================
    // CREATE
    // =====================================

    private Boolean createPermission =
            false;

    // =====================================
    // UPDATE
    // =====================================

    private Boolean updatePermission =
            false;

    // =====================================
    // DELETE
    // =====================================

    private Boolean deletePermission =
            false;

    // =====================================
    // TAKE / SKIP
    // =====================================

    private Boolean takePermission =
            false;
}