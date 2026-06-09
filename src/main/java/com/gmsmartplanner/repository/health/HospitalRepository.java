package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository
        extends JpaRepository<Hospital, Long> {

    // =====================================
    // GET ALL
    // =====================================

    List<Hospital>
    findAllByUserAndActiveTrueOrderByHospitalNameAsc(

            User user
    );

    // =====================================
    // GET BY ID
    // =====================================

    Optional<Hospital>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );
}