package com.gmsmartplanner.dto.response.health;

import com.gmsmartplanner.enums.health.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class AppointmentResponseDTO {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private Long hospitalId;

    private String hospitalName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String visitNote;

    private boolean followUpReminder;
    private AppointmentStatus
            status;
}