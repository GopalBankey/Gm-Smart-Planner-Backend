package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateAppointmentRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAppointmentRequestDTO;
import com.gmsmartplanner.dto.response.health.AppointmentResponseDTO;
import com.gmsmartplanner.entity.health.Appointment;
import com.gmsmartplanner.enums.health.AppointmentStatus;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    // =====================================
    // CREATE
    // =====================================

    public Appointment createAppointment(

            CreateAppointmentRequestDTO dto

    ) {

        Appointment appointment =
                new Appointment();

        appointment.setAppointmentDate(
                dto.getAppointmentDate()
        );

        appointment.setAppointmentTime(
                dto.getAppointmentTime()
        );

        appointment.setVisitNote(
                dto.getVisitNote()
        );

        appointment.setFollowUpReminder(
                dto.isFollowUpReminder()
        );

        appointment.setActive(
                true
        );

        // DEFAULT STATUS

        appointment.setStatus(
                AppointmentStatus.UPCOMING
        );

        return appointment;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateAppointment(

            Appointment appointment,

            UpdateAppointmentRequestDTO dto

    ) {

        if (dto.getAppointmentDate() != null) {

            appointment.setAppointmentDate(
                    dto.getAppointmentDate()
            );
        }

        if (dto.getAppointmentTime() != null) {

            appointment.setAppointmentTime(
                    dto.getAppointmentTime()
            );
        }

        if (dto.getVisitNote() != null) {

            appointment.setVisitNote(
                    dto.getVisitNote()
            );
        }

        if (dto.getFollowUpReminder() != null) {

            appointment.setFollowUpReminder(
                    dto.getFollowUpReminder()
            );
        }

        // DO NOT UPDATE STATUS HERE
    }

    // =====================================
    // RESPONSE
    // =====================================

    public AppointmentResponseDTO mapToResponse(

            Appointment appointment

    ) {

        return AppointmentResponseDTO
                .builder()

                .id(
                        appointment.getId()
                )

                .doctorId(
                        appointment
                                .getDoctor()
                                .getId()
                )

                .doctorName(
                        appointment
                                .getDoctor()
                                .getDoctorName()
                )

                .hospitalId(
                        appointment
                                .getHospital()
                                .getId()
                )

                .hospitalName(
                        appointment
                                .getHospital()
                                .getHospitalName()
                )

                .appointmentDate(
                        appointment
                                .getAppointmentDate()
                )

                .appointmentTime(
                        appointment
                                .getAppointmentTime()
                )

                .visitNote(
                        appointment
                                .getVisitNote()
                )

                .followUpReminder(
                        appointment
                                .isFollowUpReminder()
                )

                .status(
                        appointment
                                .getStatus()
                )

                .build();
    }
}