package com.gmsmartplanner.scheduler;

import com.gmsmartplanner.entity.health.Appointment;
import com.gmsmartplanner.enums.health.AppointmentStatus;
import com.gmsmartplanner.repository.health.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppointmentScheduler {

    private final AppointmentRepository
            appointmentRepository;

    // =====================================
    // AUTO MARK MISSED
    // =====================================

    @Scheduled(
            cron =
                    "0 * * * * *"
    )
    @Transactional
    public void updateMissedAppointments() {

        List<Appointment>
                appointments =

                appointmentRepository
                        .findAllByActiveTrue();

        List<Appointment>
                updatedAppointments =
                new ArrayList<>();

        LocalDateTime now =
                LocalDateTime.now();

        for (

                Appointment appointment

                :

                appointments

        ) {

            if (

                    appointment
                            .getStatus()

                            ==

                            AppointmentStatus
                                    .COMPLETED

            ) {

                continue;
            }

            if (

                    appointment
                            .getStatus()

                            ==

                            AppointmentStatus
                                    .MISSED

            ) {

                continue;
            }

            LocalDateTime
                    appointmentDateTime =

                    LocalDateTime.of(

                            appointment
                                    .getAppointmentDate(),

                            appointment
                                    .getAppointmentTime()
                    );

            if (

                    appointmentDateTime
                            .isBefore(
                                    now
                            )

            ) {

                appointment.setStatus(

                        AppointmentStatus
                                .MISSED
                );

                updatedAppointments
                        .add(
                                appointment
                        );
            }
        }

        if (

                !updatedAppointments
                        .isEmpty()

        ) {

            appointmentRepository
                    .saveAll(
                            updatedAppointments
                    );

            log.info(

                    "Marked {} appointments as MISSED",

                    updatedAppointments.size()
            );
        }
    }
}