package com.gmsmartplanner.dto.request.health;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateAppointmentRequestDTO {

    @NotNull
    private Long doctorId;

    @NotNull
    private Long hospitalId;

    @NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    private String visitNote;

    private boolean followUpReminder;
}