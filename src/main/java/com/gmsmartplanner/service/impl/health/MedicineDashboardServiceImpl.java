package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.response.health.*;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.entity.health.MedicineHistory;
import com.gmsmartplanner.entity.health.MedicineSchedule;
import com.gmsmartplanner.enums.health.MedicineHistoryStatus;
import com.gmsmartplanner.enums.health.MedicineSlot;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.MedicineDashboardMapper;
import com.gmsmartplanner.repository.health.MedicineHistoryRepository;
import com.gmsmartplanner.repository.health.MedicineRepository;
import com.gmsmartplanner.repository.health.MedicineScheduleRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.MedicineDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MedicineDashboardServiceImpl
        implements MedicineDashboardService {

    private final MedicineRepository
            medicineRepository;

    private final MedicineScheduleRepository
            scheduleRepository;

    private final MedicineHistoryRepository
            historyRepository;

    private final UserHelperService
            userHelperService;

    private final MedicineDashboardMapper
            mapper;

    @Override
    public MedicineDashboardResponseDTO
    getDashboard(

            String username

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        List<Medicine>
                medicines =

                medicineRepository
                        .findAllByUserAndActiveTrue(
                                user
                        );

        List<MedicineHistory>
                todayHistory =

                historyRepository
                        .findAllByUserAndDateAndActiveTrue(

                                user,

                                LocalDate.now()
                        );

        return mapper.toDashboard(

                buildMissed(
                        medicines,
                        todayHistory
                ),

                buildRefill(
                        medicines
                ),

                buildSessions(
                        medicines,
                        todayHistory
                )
        );
    }

// =====================================
// MISSED
// =====================================

    private List<
            MissedMedicineResponseDTO
            >
    buildMissed(

            List<Medicine> medicines,

            List<MedicineHistory> histories

    ) {

        List<
                MissedMedicineResponseDTO
                >
                result =

                new ArrayList<>();

        LocalTime now =

                LocalTime.now();

        for (

                Medicine medicine

                :

                medicines

        ) {

            // =====================================
            // MEDICINE SCHEDULES
            // =====================================

            List<MedicineSchedule>
                    schedules =

                    scheduleRepository
                            .findAllByMedicine(
                                    medicine
                            );

            for (

                    MedicineSchedule schedule

                    :

                    schedules

            ) {

                // =====================================
                // CHECK WHETHER THIS SCHEDULE
                // WAS ALREADY TAKEN
                // =====================================

                boolean taken =

                        histories
                                .stream()
                                .anyMatch(

                                        h ->

                                                h.getSchedule()
                                                        != null

                                                        &&

                                                        h.getSchedule()
                                                                .getId()
                                                                .equals(
                                                                        schedule.getId()
                                                                )

                                                        &&

                                                        h.getStatus()
                                                                ==
                                                                MedicineHistoryStatus.TAKEN
                                );

                // =====================================
                // CHECK WHETHER THIS SCHEDULE
                // WAS ALREADY SKIPPED
                // =====================================

                boolean skipped =

                        histories
                                .stream()
                                .anyMatch(

                                        h ->

                                                h.getSchedule()
                                                        != null

                                                        &&

                                                        h.getSchedule()
                                                                .getId()
                                                                .equals(
                                                                        schedule.getId()
                                                                )

                                                        &&

                                                        h.getStatus()
                                                                ==
                                                                MedicineHistoryStatus.SKIPPED
                                );

                // =====================================
                // CHECK WHETHER SCHEDULE TIME PASSED
                // =====================================

                boolean timePassed =

                        schedule
                                .getTime()
                                .isBefore(
                                        now
                                );

                // =====================================
                // MEDICINE IS MISSED
                // =====================================

                boolean missed =

                        timePassed

                                &&

                                !taken

                                &&

                                !skipped;

                // =====================================
                // ADD TO MISSED LIST
                // =====================================

                if (missed) {

                    result.add(

                            mapper
                                    .toMissed(

                                            medicine,

                                            schedule
                                    )
                    );
                }
            }
        }

        return result;
    }

    // =====================================
    // REFILL
    // =====================================

    private List<RefillSoonMedicineResponseDTO>
    buildRefill(

            List<Medicine> medicines

    ) {

        return medicines

                .stream()

                .filter(

                        medicine ->

                                medicine
                                        .isRefillReminder()

                                        &&

                                        medicine
                                                .getCurrentStock()

                                                != null

                                        &&

                                        medicine
                                                .getCurrentStock()

                                                <= 5
                )

                .map(

                        mapper
                                ::toRefill
                )

                .toList();
    }

    // =====================================
    // SESSION
    // =====================================

    private List<SessionMedicineResponseDTO>
    buildSessions(

            List<Medicine> medicines,

            List<MedicineHistory> histories

    ) {

        Map<
                MedicineSlot,

                List<
                        MedicineCardResponseDTO
                        >

                >

                grouped =

                new LinkedHashMap<>();

        for (

                Medicine medicine

                :

                medicines

        ) {

            List<MedicineSchedule>
                    schedules =

                    scheduleRepository
                            .findAllByMedicine(
                                    medicine
                            );

            for (

                    MedicineSchedule schedule

                    :

                    schedules

            ) {

                MedicineHistoryStatus status =

                        histories
                                .stream()

                                .filter(

                                        h ->

                                                h.getSchedule()

                                                        != null

                                                        &&

                                                        schedule
                                                                .getId()

                                                                .equals(

                                                                        h
                                                                                .getSchedule()
                                                                                .getId()
                                                                )
                                )

                                .map(
                                        MedicineHistory::getStatus
                                )

                                .filter(
                                        Objects::nonNull
                                )

                                .findFirst()

                                .orElse(

                                        schedule
                                                .getTime()

                                                .isBefore(
                                                        LocalTime.now()
                                                )

                                                ?

                                                MedicineHistoryStatus.MISSED

                                                :

                                                MedicineHistoryStatus.PENDING
                                );

                grouped

                        .computeIfAbsent(

                                schedule
                                        .getSlotName(),

                                s ->

                                        new ArrayList<>()
                        )

                        .add(

                                mapper
                                        .toCard(

                                                medicine,

                                                schedule,

                                                status
                                        )
                        );
            }
        }

        List<SessionMedicineResponseDTO>
                result =

                new ArrayList<>();

        grouped.forEach(

                (

                        session,

                        cards

                ) -> {

                    int upcoming =

                            (int)

                                    cards
                                            .stream()

                                            .filter(

                                                    c ->

                                                            c.getStatus()

                                                                    ==

                                                                    MedicineHistoryStatus.PENDING
                                            )

                                            .count();

                    result.add(

                            mapper
                                    .toSession(

                                            session.name(),

                                            upcoming,

                                            cards
                                    )
                    );
                }
        );

        return result;
    }

    // =====================================
// GET MISSED MEDICINE DETAILS
// =====================================



    @Override
    public MedicineCardResponseDTO
    getMissedMedicineDetails(

            String username,

            Long medicineId,

            Long scheduleId

    ) {

        // =====================================
        // CURRENT USER
        // =====================================

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        // =====================================
        // MEDICINE
        // =====================================

        Medicine medicine =

                medicineRepository
                        .findById(
                                medicineId
                        )

                        .filter(
                                Medicine::isActive
                        )

                        .filter(
                                m ->
                                        m.getUser()
                                                .getId()
                                                .equals(
                                                        user.getId()
                                                )
                        )

                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Medicine not found"
                                        )
                        );

        // =====================================
        // MEDICINE SCHEDULE
        // =====================================

        MedicineSchedule schedule =

                scheduleRepository
                        .findById(
                                scheduleId
                        )

                        .filter(
                                s ->
                                        Boolean.TRUE.equals(
                                                s.getActive()
                                        )
                        )

                        .filter(
                                s ->
                                        s.getMedicine()
                                                .getId()
                                                .equals(
                                                        medicine.getId()
                                                )
                        )

                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Medicine schedule not found"
                                        )
                        );

        // =====================================
        // CURRENT TIME
        // =====================================

        LocalTime now =

                LocalTime.now();

        // =====================================
        // VERIFY SCHEDULE TIME HAS PASSED
        // =====================================

        if (
                !schedule
                        .getTime()
                        .isBefore(
                                now
                        )
        ) {

            throw new InvalidRequestException(
                    "Medicine is not missed"
            );
        }

        // =====================================
        // TODAY'S MEDICINE HISTORY
        // =====================================

        List<MedicineHistory>
                todayHistory =

                historyRepository
                        .findAllByUserAndDateAndActiveTrue(

                                user,

                                LocalDate.now()
                        );

        // =====================================
        // CHECK WHETHER THIS SCHEDULE
        // WAS ALREADY TAKEN
        // =====================================

        boolean taken =

                todayHistory
                        .stream()
                        .anyMatch(

                                h ->

                                        h.getSchedule()
                                                != null

                                                &&

                                                h.getSchedule()
                                                        .getId()
                                                        .equals(
                                                                schedule.getId()
                                                        )

                                                &&

                                                h.getStatus()
                                                        ==
                                                        MedicineHistoryStatus.TAKEN
                        );

        // =====================================
        // CHECK WHETHER THIS SCHEDULE
        // WAS ALREADY SKIPPED
        // =====================================

        boolean skipped =

                todayHistory
                        .stream()
                        .anyMatch(

                                h ->

                                        h.getSchedule()
                                                != null

                                                &&

                                                h.getSchedule()
                                                        .getId()
                                                        .equals(
                                                                schedule.getId()
                                                        )

                                                &&

                                                h.getStatus()
                                                        ==
                                                        MedicineHistoryStatus.SKIPPED
                        );

        // =====================================
        // ALREADY PROCESSED
        // =====================================

        if (
                taken
                        ||
                        skipped
        ) {

            throw new InvalidRequestException(
                    "Medicine has already been processed"
            );
        }

        // =====================================
        // RETURN MISSED MEDICINE DETAILS
        // =====================================

        return mapper.toCard(

                medicine,

                schedule,

                MedicineHistoryStatus.MISSED
        );
    }
}