package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.response.health.FamilyMemberResponseDTO;
import com.gmsmartplanner.enums.BloodGroup;
import com.gmsmartplanner.enums.health.FamilyRelation;
import com.gmsmartplanner.payload.ApiResponse;
import com.gmsmartplanner.service.AccessUserService;
import com.gmsmartplanner.service.health.FamilyMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/family")
public class FamilyMemberController {

    private final FamilyMemberService
            familyMemberService;

    private final AccessUserService
            accessUserService;

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<FamilyMemberResponseDTO>>
    create(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @ModelAttribute
            @Valid
            CreateFamilyMemberRequestDTO dto

    ) {

        accessUserService
                .checkCreatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<FamilyMemberResponseDTO>builder()

                        .success(true)

                        .message(
                                "Family member created successfully"
                        )

                        .data(

                                familyMemberService
                                        .createFamilyMember(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET ALL
    // =====================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<FamilyMemberResponseDTO>>>
    getAll(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId

    ) {

        accessUserService
                .checkViewPermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<List<FamilyMemberResponseDTO>>builder()

                        .success(true)

                        .message(
                                "Family members fetched successfully"
                        )

                        .data(

                                familyMemberService
                                        .getFamilyMembers(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        )
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // GET BY ID
    // =====================================

    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<FamilyMemberResponseDTO>>
    getById(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long memberId

    ) {

        accessUserService
                .checkViewPermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<FamilyMemberResponseDTO>builder()

                        .success(true)

                        .message(
                                "Family member fetched successfully"
                        )

                        .data(

                                familyMemberService
                                        .getFamilyMember(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                memberId
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // UPDATE
    // =====================================

    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<FamilyMemberResponseDTO>>
    update(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long memberId,

            @ModelAttribute
            UpdateFamilyMemberRequestDTO dto

    ) {

        accessUserService
                .checkUpdatePermission(
                        authentication.getName(),
                        accessId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<FamilyMemberResponseDTO>builder()

                        .success(true)

                        .message(
                                "Family member updated successfully"
                        )

                        .data(

                                familyMemberService
                                        .updateFamilyMember(

                                                accessUserService
                                                        .getEffectiveUsername(

                                                                authentication.getName(),

                                                                accessId
                                                        ),

                                                memberId,

                                                dto
                                        )
                        )

                        .build()
        );
    }

    // =====================================
    // DELETE
    // =====================================

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>>
    delete(

            Authentication authentication,

            @RequestHeader(
                    value = "X-ACCESS-ID",
                    required = false
            )
            Long accessId,

            @PathVariable
            Long memberId

    ) {

        accessUserService
                .checkDeletePermission(
                        authentication.getName(),
                        accessId
                );

        familyMemberService
                .deleteFamilyMember(

                        accessUserService
                                .getEffectiveUsername(

                                        authentication.getName(),

                                        accessId
                                ),

                        memberId
                );

        return ResponseEntity.ok(

                ApiResponse
                        .<Void>builder()

                        .success(true)

                        .message(
                                "Family member deleted successfully"
                        )

                        .build()
        );
    }

    // =====================================
    // RELATIONS
    // =====================================

    @GetMapping("/relations")
    public ResponseEntity<ApiResponse<List<String>>>
    getRelations() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Relations fetched successfully"
                        )

                        .data(

                                Arrays.stream(
                                                FamilyRelation.values()
                                        )
                                        .map(
                                                Enum::name
                                        )
                                        .toList()
                        )

                        .build()
        );
    }

    // =====================================
    // BLOOD GROUPS
    // =====================================

    @GetMapping("/blood-groups")
    public ResponseEntity<ApiResponse<List<String>>>
    getBloodGroups() {

        return ResponseEntity.ok(

                ApiResponse
                        .<List<String>>builder()

                        .success(true)

                        .message(
                                "Blood groups fetched successfully"
                        )

                        .data(

                                Arrays.stream(
                                                BloodGroup.values()
                                        )
                                        .map(
                                                Enum::name
                                        )
                                        .toList()
                        )

                        .build()
        );
    }
}