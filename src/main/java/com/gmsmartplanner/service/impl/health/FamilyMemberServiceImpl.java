package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.response.health.FamilyMemberResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.FamilyMember;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.FamilyMemberMapper;
import com.gmsmartplanner.repository.health.FamilyMemberRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FamilyMemberServiceImpl
        implements FamilyMemberService {

    private final FamilyMemberRepository
            familyMemberRepository;

    private final FamilyMemberMapper
            familyMemberMapper;

    private final UserHelperService
            userHelperService;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public FamilyMemberResponseDTO createFamilyMember(

            String username,

            CreateFamilyMemberRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        FamilyMember member =
                familyMemberMapper
                        .createFamilyMember(
                                dto
                        );

        member.setUser(
                user
        );

        if (dto.getImage() != null
                && !dto.getImage().isEmpty()) {

            member.setProfileImage(

                    fileUploadService
                            .uploadImage(

                                    dto.getImage(),

                                    "family"
                            )
            );
        }

        FamilyMember saved =
                familyMemberRepository
                        .save(
                                member
                        );

        return familyMemberMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<FamilyMemberResponseDTO>
    getFamilyMembers(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return familyMemberRepository
                .findAllByUserAndActiveTrueOrderByFullNameAsc(
                        user
                )
                .stream()
                .map(
                        familyMemberMapper
                                ::mapToResponse
                )
                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public FamilyMemberResponseDTO getFamilyMember(

            String username,

            Long memberId

    ) {

        return familyMemberMapper
                .mapToResponse(

                        getMember(

                                memberId,

                                username
                        )
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public FamilyMemberResponseDTO updateFamilyMember(

            String username,

            Long memberId,

            UpdateFamilyMemberRequestDTO dto

    ) {

        FamilyMember member =
                getMember(

                        memberId,

                        username
                );

        familyMemberMapper
                .updateFamilyMember(

                        member,

                        dto
                );

        if (dto.getImage() != null
                && !dto.getImage().isEmpty()) {

            member.setProfileImage(

                    fileUploadService
                            .uploadImage(

                                    dto.getImage(),

                                    "family"
                            )
            );
        }

        FamilyMember updated =
                familyMemberRepository
                        .save(
                                member
                        );

        return familyMemberMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteFamilyMember(

            String username,

            Long memberId

    ) {

        FamilyMember member =
                getMember(

                        memberId,

                        username
                );

        member.setActive(
                false
        );

        familyMemberRepository
                .save(
                        member
                );
    }

    // =====================================
    // MEMBER
    // =====================================

    private FamilyMember getMember(

            Long memberId,

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return familyMemberRepository
                .findByIdAndUserAndActiveTrue(

                        memberId,

                        user
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Family member not found"
                        )
                );
    }
}