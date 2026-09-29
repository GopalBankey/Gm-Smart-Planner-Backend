package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AllergyRepository
        extends JpaRepository<Allergy, Long> {

    List<Allergy>
    findAllByUserAndActiveTrueOrderByCreatedAtDesc(
            User user
    );

    Optional<Allergy>
    findByIdAndUserAndActiveTrue(
            Long id,
            User user
    );
}