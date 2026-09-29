package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FamilyMemberRepository
        extends JpaRepository<FamilyMember, Long> {

    // =====================================
    // GET ALL
    // =====================================

    List<FamilyMember>
    findAllByUserAndActiveTrueOrderByFullNameAsc(

            User user
    );


    // =====================================
    // GET BY ID
    // =====================================

    Optional<FamilyMember>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );


    // =====================================
    // GET EMERGENCY CONTACTS
    // =====================================

    List<FamilyMember>
    findAllByUserAndEmergencyContactTrueAndActiveTrue(

            User user
    );


    // =====================================
    // COUNT EMERGENCY CONTACTS
    // =====================================

    long
    countByUserAndEmergencyContactTrueAndActiveTrue(

            User user
    );
}