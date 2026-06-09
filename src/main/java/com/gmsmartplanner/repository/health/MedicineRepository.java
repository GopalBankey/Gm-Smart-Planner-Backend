package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicineRepository
        extends JpaRepository<Medicine, Long> {

    // =====================================
    // GET ALL
    // =====================================

    List<Medicine>
    findAllByUserAndActiveTrueOrderByCreatedAtDesc(

            User user
    );

    // =====================================
    // GET BY ID
    // =====================================

    Optional<Medicine>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );

    List<Medicine>
    findAllByUserAndActiveTrue(

            User user
    );


}