package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateAppointmentRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateAppointmentRequestDTO;
import com.gmsmartplanner.dto.response.health.AppointmentResponseDTO;

import java.util.List;

public interface AppointmentService {

    // =====================================
    // CREATE
    // =====================================

    AppointmentResponseDTO createAppointment(

            String username,

            CreateAppointmentRequestDTO dto
    );

    // =====================================
    // GET ALL
    // =====================================

    List<AppointmentResponseDTO>
    getAppointments(

            String username
    );

    // =====================================
    // GET BY ID
    // =====================================

    AppointmentResponseDTO getAppointment(

            String username,

            Long appointmentId
    );

    // =====================================
    // UPDATE
    // =====================================

    AppointmentResponseDTO updateAppointment(

            String username,

            Long appointmentId,

            UpdateAppointmentRequestDTO dto
    );

    // =====================================
    // DELETE
    // =====================================

    void deleteAppointment(

            String username,

            Long appointmentId
    );

    // =====================================
// COMPLETE
// =====================================

    AppointmentResponseDTO
    completeAppointment(

            String username,

            Long appointmentId
    );
}