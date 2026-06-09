package com.gmsmartplanner.controller.health;

import com.gmsmartplanner.dto.request.health.CreateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.response.health.FamilyMemberResponseDTO;
import com.gmsmartplanner.enums.BloodGroup;
import com.gmsmartplanner.enums.health.FamilyRelation;
import com.gmsmartplanner.payload.ApiResponse;
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

    // =====================================
    // CREATE
    // =====================================

    @PostMapping
    public ResponseEntity<ApiResponse<FamilyMemberResponseDTO>>
    create(

            Authentication authentication,

            @ModelAttribute
            @Valid
            CreateFamilyMemberRequestDTO dto

    ) {

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

                                                authentication.getName(),

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

            Authentication authentication

    ) {

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

                                                authentication.getName()
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

            @PathVariable
            Long memberId

    ) {

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

                                                authentication.getName(),

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

            @PathVariable
            Long memberId,

            @ModelAttribute
            UpdateFamilyMemberRequestDTO dto

    ) {

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

                                                authentication.getName(),

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

            @PathVariable
            Long memberId

    ) {

        familyMemberService
                .deleteFamilyMember(

                        authentication.getName(),

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