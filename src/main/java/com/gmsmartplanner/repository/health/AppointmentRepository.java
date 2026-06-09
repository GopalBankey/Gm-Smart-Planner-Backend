package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // =====================================
    // GET ALL
    // =====================================

    List<Appointment>
    findAllByUserAndActiveTrueOrderByAppointmentDateDescAppointmentTimeDesc(

            User user
    );

    // =====================================
    // GET BY ID
    // =====================================

    Optional<Appointment>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );

    // =====================================
    // GET BY DATE
    // =====================================

    List<Appointment>
    findAllByUserAndAppointmentDateAndActiveTrueOrderByAppointmentTimeAsc(

            User user,

            LocalDate appointmentDate
    );

    List<Appointment>
    findAllByActiveTrue();
}