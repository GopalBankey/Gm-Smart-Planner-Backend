package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineRefillReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
MedicineRefillReminderRepository

        extends JpaRepository<
        MedicineRefillReminder,
        Long> {

    // =====================================
    // GET BY MEDICINE
    // =====================================

    Optional<MedicineRefillReminder>
    findByMedicine(

            Medicine medicine
    );
}