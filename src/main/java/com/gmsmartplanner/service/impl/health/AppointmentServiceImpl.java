package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateAppointmentRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAppointmentRequestDTO;
import com.gmsmartplanner.dto.response.health.AppointmentResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Appointment;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.enums.health.AppointmentStatus;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.AppointmentMapper;
import com.gmsmartplanner.repository.health.AppointmentRepository;
import com.gmsmartplanner.repository.health.DoctorRepository;
import com.gmsmartplanner.repository.health.HospitalRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.gmsmartplanner.entity.Reminder;
import com.gmsmartplanner.enums.NotificationReferenceType;
import com.gmsmartplanner.repository.ReminderRepository;

import java.time.LocalDateTime;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl
        implements AppointmentService {

    private final AppointmentRepository
            appointmentRepository;

    private final DoctorRepository
            doctorRepository;

    private final HospitalRepository
            hospitalRepository;

    private final AppointmentMapper
            appointmentMapper;

    private final UserHelperService
            userHelperService;

    private final ReminderRepository
            reminderRepository;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public AppointmentResponseDTO createAppointment(

            String username,

            CreateAppointmentRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Doctor doctor =
                doctorRepository
                        .findByIdAndUserAndActiveTrue(

                                dto.getDoctorId(),

                                user
                        )

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Doctor not found"
                                )
                        );

        Hospital hospital =
                hospitalRepository
                        .findByIdAndUserAndActiveTrue(

                                dto.getHospitalId(),

                                user
                        )

                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Hospital not found"
                                )
                        );

        Appointment appointment =
                appointmentMapper
                        .createAppointment(
                                dto
                        );

        appointment.setUser(
                user
        );

        appointment.setDoctor(
                doctor
        );

        appointment.setHospital(
                hospital
        );

        appointment.setStatus(
                AppointmentStatus
                        .UPCOMING
        );

        Appointment saved =
                appointmentRepository
                        .save(
                                appointment
                        );

        createAppointmentReminder(
                saved
        );
        return appointmentMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<AppointmentResponseDTO>
    getAppointments(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return appointmentRepository

                .findAllByUserAndActiveTrueOrderByAppointmentDateDescAppointmentTimeDesc(
                        user
                )

                .stream()

                .map(
                        appointmentMapper
                                ::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public AppointmentResponseDTO getAppointment(

            String username,

            Long appointmentId

    ) {

        return appointmentMapper
                .mapToResponse(

                        getAppointmentEntity(

                                appointmentId,

                                userHelperService
                                        .getCurrentUser(
                                                username
                                        )
                        )
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public AppointmentResponseDTO updateAppointment(

            String username,

            Long appointmentId,

            UpdateAppointmentRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Appointment appointment =
                getAppointmentEntity(

                        appointmentId,

                        user
                );

        if (dto.getDoctorId() != null) {

            appointment.setDoctor(

                    doctorRepository
                            .findByIdAndUserAndActiveTrue(

                                    dto.getDoctorId(),

                                    user
                            )

                            .orElseThrow(() ->

                                    new ResourceNotFoundException(
                                            "Doctor not found"
                                    )
                            )
            );
        }

        if (dto.getHospitalId() != null) {

            appointment.setHospital(

                    hospitalRepository
                            .findByIdAndUserAndActiveTrue(

                                    dto.getHospitalId(),

                                    user
                            )

                            .orElseThrow(() ->

                                    new ResourceNotFoundException(
                                            "Hospital not found"
                                    )
                            )
            );
        }

        appointmentMapper
                .updateAppointment(

                        appointment,

                        dto
                );

        if (

                appointment.getStatus()

                        !=

                        AppointmentStatus
                                .COMPLETED

        ) {

            appointment.setStatus(

                    AppointmentStatus
                            .UPCOMING
            );
        }
        Appointment updated =
                appointmentRepository
                        .save(
                                appointment
                        );

        deleteAppointmentReminder(
                updated.getId()
        );

        createAppointmentReminder(
                updated
        );

        return appointmentMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // COMPLETE
    // =====================================

    @Override
    public AppointmentResponseDTO completeAppointment(

            String username,

            Long appointmentId

    ) {

        Appointment appointment =

                getAppointmentEntity(

                        appointmentId,

                        userHelperService
                                .getCurrentUser(
                                        username
                                )
                );

        appointment.setStatus(

                AppointmentStatus
                        .COMPLETED
        );

        Appointment updated =
                appointmentRepository
                        .save(
                                appointment
                        );

        return appointmentMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteAppointment(

            String username,

            Long appointmentId

    ) {

        Appointment appointment =

                getAppointmentEntity(

                        appointmentId,

                        userHelperService
                                .getCurrentUser(
                                        username
                                )
                );

        appointment.setActive(
                false
        );

        deleteAppointmentReminder(
                appointmentId
        );

        appointmentRepository
                .save(
                        appointment
                );
    }

    // =====================================
    // ENTITY
    // =====================================

    private Appointment
    getAppointmentEntity(

            Long id,

            User user

    ) {

        return appointmentRepository

                .findByIdAndUserAndActiveTrue(

                        id,

                        user
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Appointment not found"
                        )
                );
    }

    private void createAppointmentReminder(

            Appointment appointment

    ) {

        LocalDateTime reminderTime =

                LocalDateTime.of(

                        appointment.getAppointmentDate(),

                        appointment.getAppointmentTime()
                );

        if (

                reminderTime.isBefore(

                        LocalDateTime.now()
                )

        ) {

            return;
        }

        Reminder reminder =
                new Reminder();

        reminder.setUser(
                appointment.getUser()
        );

        reminder.setReferenceId(
                appointment.getId()
        );

        reminder.setReferenceType(

                NotificationReferenceType
                        .APPOINTMENT
        );

        reminder.setReminderTime(
                reminderTime
        );

        reminder.setRecurring(
                false
        );

        reminder.setSent(
                false
        );

        reminder.setActive(
                true
        );

        reminderRepository
                .save(
                        reminder
                );
    }

    private void deleteAppointmentReminder(

            Long appointmentId

    ) {

        reminderRepository

                .deleteAllByReferenceIdAndReferenceType(

                        appointmentId,

                        NotificationReferenceType
                                .APPOINTMENT
                );
    }
}