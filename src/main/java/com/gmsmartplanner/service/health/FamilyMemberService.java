package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.response.health.FamilyMemberResponseDTO;

import java.util.List;

public interface FamilyMemberService {

    // =====================================
    // CREATE
    // =====================================

    FamilyMemberResponseDTO createFamilyMember(

            String username,

            CreateFamilyMemberRequestDTO dto
    );

    // =====================================
    // GET ALL
    // =====================================

    List<FamilyMemberResponseDTO>
    getFamilyMembers(

            String username
    );

    // =====================================
    // GET BY ID
    // =====================================

    FamilyMemberResponseDTO getFamilyMember(

            String username,

            Long memberId
    );

    // =====================================
    // UPDATE
    // =====================================

    FamilyMemberResponseDTO updateFamilyMember(

            String username,

            Long memberId,

            UpdateFamilyMemberRequestDTO dto
    );

    // =====================================
    // DELETE
    // =====================================

    void deleteFamilyMember(

            String username,

            Long memberId
    );
}