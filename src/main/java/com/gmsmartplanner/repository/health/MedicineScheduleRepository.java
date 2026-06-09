package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MedicineScheduleRepository
        extends JpaRepository<MedicineSchedule, Long> {

    List<MedicineSchedule>
    findAllByMedicine(

            Medicine medicine
    );


    @Modifying
    @Transactional
    void deleteAllByMedicine(
            Medicine medicine
    );

//    List<MedicineSchedule>
//    findAllByMedicineAndEnabledTrue(
//
//            Medicine medicine
//    );
}