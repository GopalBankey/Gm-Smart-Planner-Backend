package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateFamilyMemberRequestDTO;
import com.gmsmartplanner.dto.response.health.FamilyMemberResponseDTO;
import com.gmsmartplanner.entity.health.FamilyMember;
import org.springframework.stereotype.Component;

@Component
public class FamilyMemberMapper {

    // =====================================
    // CREATE
    // =====================================

    public FamilyMember createFamilyMember(

            CreateFamilyMemberRequestDTO dto

    ) {

        FamilyMember member =
                new FamilyMember();

        member.setFullName(
                dto.getFullName()
        );

        member.setRelation(
                dto.getRelation()
        );

        member.setBloodGroup(
                dto.getBloodGroup()
        );

        member.setCountryCode(

                dto.getCountryCode() == null
                        || dto.getCountryCode().isBlank()

                        ?

                        "+91"

                        :

                        dto.getCountryCode()
        );

        member.setMobileNumber(
                dto.getMobileNumber()
        );

        member.setDateOfBirth(
                dto.getDateOfBirth()
        );

        member.setAge(
                dto.getAge()
        );

        member.setNotes(
                dto.getNotes()
        );

        member.setActive(
                true
        );

        return member;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateFamilyMember(

            FamilyMember member,

            UpdateFamilyMemberRequestDTO dto

    ) {

        if (dto.getFullName() != null) {

            member.setFullName(
                    dto.getFullName()
            );
        }

        if (dto.getRelation() != null) {

            member.setRelation(
                    dto.getRelation()
            );
        }

        if (dto.getBloodGroup() != null) {

            member.setBloodGroup(
                    dto.getBloodGroup()
            );
        }

        if (dto.getCountryCode() != null
                && !dto.getCountryCode().isBlank()) {

            member.setCountryCode(
                    dto.getCountryCode()
            );
        }

        if (dto.getMobileNumber() != null) {

            member.setMobileNumber(
                    dto.getMobileNumber()
            );
        }

        if (dto.getDateOfBirth() != null) {

            member.setDateOfBirth(
                    dto.getDateOfBirth()
            );
        }

        if (dto.getAge() != null) {

            member.setAge(
                    dto.getAge()
            );
        }

        if (dto.getNotes() != null) {

            member.setNotes(
                    dto.getNotes()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public FamilyMemberResponseDTO mapToResponse(

            FamilyMember member

    ) {

        return FamilyMemberResponseDTO
                .builder()

                .id(
                        member.getId()
                )

                .profileImage(
                        member.getProfileImage()
                )

                .fullName(
                        member.getFullName()
                )

                .relation(
                        member.getRelation()
                )

                .bloodGroup(
                        member.getBloodGroup()
                )

                .countryCode(
                        member.getCountryCode()
                )

                .mobileNumber(
                        member.getMobileNumber()
                )

                .dateOfBirth(
                        member.getDateOfBirth()
                )

                .age(
                        member.getAge()
                )

                .notes(
                        member.getNotes()
                )

                .build();
    }
}