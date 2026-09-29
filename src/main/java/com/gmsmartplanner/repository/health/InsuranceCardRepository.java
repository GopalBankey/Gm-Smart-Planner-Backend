package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.InsuranceCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InsuranceCardRepository
        extends JpaRepository<InsuranceCard, Long> {

    // ==========================================
    // GET ALL ACTIVE INSURANCE CARDS
    // ==========================================

    List<InsuranceCard>
    findAllByUserAndActiveTrueOrderByCreatedAtDesc(
            User user
    );


    // ==========================================
    // GET ACTIVE INSURANCE CARD BY ID
    // ==========================================

    Optional<InsuranceCard>
    findByIdAndUserAndActiveTrue(
            Long id,
            User user
    );
}