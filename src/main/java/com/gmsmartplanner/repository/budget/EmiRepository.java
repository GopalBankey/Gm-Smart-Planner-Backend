package com.gmsmartplanner.repository.budget;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.budget.Emi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmiRepository
        extends JpaRepository<Emi, Long> {

    // =====================================
    // GET ALL USER EMIS
    // =====================================

    List<Emi>
    findAllByUserAndActiveTrue(

            User user
    );

    // =====================================
    // GET EMI BY ID
    // =====================================

    Optional<Emi>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );

    // =====================================
    // GET ALL ACTIVE EMIS
    // =====================================

    List<Emi>
    findAllByActiveTrue();

    // =====================================
    // EMI COUNT
    // =====================================

    long countByUserAndActiveTrue(

            User user
    );

    // =====================================
    // UPCOMING EMI
    // =====================================

    List<Emi>
    findAllByUserAndActiveTrueOrderByEmiDueDateAsc(

            User user
    );
}