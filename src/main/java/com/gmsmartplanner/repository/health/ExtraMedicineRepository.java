package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.health.ExtraMedicine;
import com.gmsmartplanner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExtraMedicineRepository
        extends JpaRepository<ExtraMedicine, Long> {

    // =====================================
    // GET ALL
    // =====================================

    List<ExtraMedicine>
    findAllByUserAndActiveTrueOrderByMedicineNameAsc(

            User user
    );

    // =====================================
    // GET BY ID
    // =====================================

    Optional<ExtraMedicine>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );
}