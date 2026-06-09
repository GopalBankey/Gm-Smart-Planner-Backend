package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.response.health.MedicineHistoryResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineHistory;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.MedicineHistoryMapper;
import com.gmsmartplanner.repository.health.MedicineHistoryRepository;
import com.gmsmartplanner.repository.health.MedicineRepository;
import com.gmsmartplanner.repository.health.MedicineScheduleRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.MedicineHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineHistoryServiceImpl
        implements MedicineHistoryService {

    private final MedicineRepository
            medicineRepository;

    private final MedicineHistoryRepository
            historyRepository;

    private final MedicineScheduleRepository
            scheduleRepository;

    private final UserHelperService
            userHelperService;

    private final MedicineHistoryMapper
            mapper;

    // =====================================
    // TAKE
    // =====================================

    @Override
    public MedicineHistoryResponseDTO
    takeMedicine(

            String username,

            Long medicineId,

            Long scheduleId

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        Medicine medicine =

                getMedicine(
                        medicineId,
                        user
                );

        MedicineSchedule schedule =

                getSchedule(
                        scheduleId,
                        medicine
                );

        validateAlreadyProcessed(
                schedule
        );

        Integer previousStock =
                medicine.getCurrentStock();

        Integer dose =
                schedule.getDoseCount();

        Integer currentStock =

                Math.max(

                        0,

                        previousStock
                                -
                                dose
                );

        MedicineHistory history =

                mapper
                        .takeMedicine(

                                user,

                                medicine,

                                schedule,

                                previousStock,

                                currentStock,

                                dose
                        );

        medicine.setCurrentStock(
                currentStock
        );

        medicine.setTotalConsumed(

                medicine.getTotalConsumed()

                        +

                        dose
        );

        medicineRepository
                .save(
                        medicine
                );

        return mapper.toResponse(

                historyRepository
                        .save(
                                history
                        )
        );
    }

    // =====================================
    // SKIP
    // =====================================

    @Override
    public MedicineHistoryResponseDTO
    skipMedicine(

            String username,

            Long medicineId,

            Long scheduleId

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        Medicine medicine =

                getMedicine(
                        medicineId,
                        user
                );

        MedicineSchedule schedule =

                getSchedule(
                        scheduleId,
                        medicine
                );

        validateAlreadyProcessed(
                schedule
        );

        MedicineHistory history =

                mapper
                        .skipMedicine(

                                user,

                                medicine,

                                schedule,

                                medicine.getCurrentStock()
                        );

        return mapper.toResponse(

                historyRepository
                        .save(
                                history
                        )
        );
    }

    // =====================================
    // GET ALL HISTORY
    // =====================================

    @Override
    public List<MedicineHistoryResponseDTO>
    getHistory(

            String username

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        return historyRepository

                .findAllByUserAndActiveTrueOrderByDateDescTimeDesc(
                        user
                )

                .stream()

                .map(
                        mapper::toResponse
                )

                .toList();
    }

    // =====================================
    // GET MEDICINE HISTORY
    // =====================================

    @Override
    public List<MedicineHistoryResponseDTO>
    getMedicineHistory(

            String username,

            Long medicineId

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        Medicine medicine =

                getMedicine(
                        medicineId,
                        user
                );

        return historyRepository

                .findAllByMedicineAndActiveTrueOrderByDateDesc(
                        medicine
                )

                .stream()

                .map(
                        mapper::toResponse
                )

                .toList();
    }

    // =====================================
    // VALIDATE
    // =====================================

    private void validateAlreadyProcessed(

            MedicineSchedule schedule

    ) {

        boolean processed =

                historyRepository

                        .existsByScheduleAndDateAndStatusIn(

                                schedule,

                                LocalDate.now(),

                                List.of(

                                        MedicineHistoryStatus.TAKEN,

                                        MedicineHistoryStatus.SKIPPED
                                )
                        );

        if (

                processed

        ) {

            throw new InvalidRequestException(

                    "Medicine already taken or skipped"
            );
        }
    }

    // =====================================
    // GET MEDICINE
    // =====================================

    private Medicine
    getMedicine(

            Long medicineId,

            User user

    ) {

        return medicineRepository

                .findByIdAndUserAndActiveTrue(

                        medicineId,

                        user
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "Medicine not found"
                                )
                );
    }

    // =====================================
    // GET SCHEDULE
    // =====================================

    private MedicineSchedule
    getSchedule(

            Long scheduleId,

            Medicine medicine

    ) {

        return scheduleRepository

                .findById(

                        scheduleId
                )

                .filter(

                        schedule ->

                                schedule
                                        .getMedicine()

                                        .getId()

                                        .equals(
                                                medicine.getId()
                                        )
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "Schedule not found"
                                )
                );
    }
}