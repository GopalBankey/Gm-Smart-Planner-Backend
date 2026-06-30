package com.gmsmartplanner.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmsmartplanner.entity.emi.EmiCategory;

import java.util.List;
import java.util.Optional;

public interface EmiCategoryRepository
        extends JpaRepository<EmiCategory, Long> {

    List<EmiCategory>
    findAllByActiveTrue();

    Optional<EmiCategory>
    findByIdAndActiveTrue(
            Long id
    );
}