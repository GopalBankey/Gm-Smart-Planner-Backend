package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiseaseRepository
        extends JpaRepository<Disease, Long> {

    List<Disease>
    findAllByUserAndActiveTrueOrderByCreatedAtDesc(
            User user
    );

    Optional<Disease>
    findByIdAndUserAndActiveTrue(
            Long id,
            User user
    );
}