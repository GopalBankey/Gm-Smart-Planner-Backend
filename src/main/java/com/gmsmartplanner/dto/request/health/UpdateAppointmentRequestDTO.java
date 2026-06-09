package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class UpdateAppointmentRequestDTO {

    private Long doctorId;

    private Long hospitalId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String visitNote;

    private Boolean followUpReminder;
}