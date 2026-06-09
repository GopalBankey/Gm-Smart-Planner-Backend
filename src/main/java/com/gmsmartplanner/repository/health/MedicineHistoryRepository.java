package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineHistory;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicineHistoryRepository
        extends JpaRepository<
        MedicineHistory,
        Long
        > {

    // =====================================
    // GET ALL
    // =====================================

    List<MedicineHistory>
    findAllByUserAndActiveTrueOrderByDateDescTimeDesc(

            User user
    );

    // =====================================
    // GET MEDICINE HISTORY
    // =====================================

    List<MedicineHistory>
    findAllByMedicineAndActiveTrueOrderByDateDesc(

            Medicine medicine
    );

    // =====================================
    // TODAY
    // =====================================

    List<MedicineHistory>
    findAllByUserAndDateAndActiveTrue(

            User user,

            LocalDate date
    );

    // =====================================
    // CHECK TODAY
    // =====================================

    Optional<MedicineHistory>
    findByScheduleAndDateAndActiveTrue(

            MedicineSchedule schedule,

            LocalDate date
    );

    // =====================================
// CHECK SCHEDULE USED
// =====================================

    boolean
    existsByScheduleAndActiveTrue(

            MedicineSchedule schedule
    );

    // =====================================
    // FILTER STATUS
    // =====================================

    List<MedicineHistory>
    findAllByUserAndStatusAndActiveTrue(

            User user,

            MedicineHistoryStatus status
    );

    @Modifying
    @Query("""
UPDATE MedicineHistory h
SET h.active = false
WHERE h.schedule.id = :scheduleId
""")
    void deactivateBySchedule(
            Long scheduleId
    );


    boolean existsByScheduleAndDate(

            MedicineSchedule schedule,

            LocalDate date

    );

    boolean existsByScheduleAndDateAndStatusIn(

            MedicineSchedule schedule,

            LocalDate date,

            List<MedicineHistoryStatus> status

    );

}

