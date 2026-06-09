package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateDoctorRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDoctorRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;
import com.gmsmartplanner.entity.health.Doctor;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    // =====================================
    // CREATE
    // =====================================

    public Doctor createDoctor(
            CreateDoctorRequestDTO dto
    ) {

        Doctor doctor =
                new Doctor();

        doctor.setDoctorName(
                dto.getDoctorName()
        );

        doctor.setMobileNumber(
                dto.getMobileNumber()
        );

        doctor.setSpecialization(
                dto.getSpecialization()
        );

        doctor.setCountryCode(

                dto.getCountryCode() == null
                        || dto.getCountryCode().isBlank()

                        ? "+91"

                        : dto.getCountryCode()
        );

        doctor.setActive(true);

        return doctor;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateDoctor(

            Doctor doctor,

            UpdateDoctorRequestDTO dto

    ) {

        if (dto.getDoctorName() != null) {

            doctor.setDoctorName(
                    dto.getDoctorName()
            );
        }

        if (dto.getMobileNumber() != null) {

            doctor.setMobileNumber(
                    dto.getMobileNumber()
            );
        }

        if (dto.getSpecialization() != null) {

            doctor.setSpecialization(
                    dto.getSpecialization()
            );
        }

        if (dto.getCountryCode() != null
                && !dto.getCountryCode().isBlank()) {

            doctor.setCountryCode(
                    dto.getCountryCode()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public DoctorResponseDTO mapToResponse(
            Doctor doctor
    ) {

        return DoctorResponseDTO
                .builder()

                .id(
                        doctor.getId()
                )

                .doctorName(
                        doctor.getDoctorName()
                )

                .countryCode(
                        doctor.getCountryCode()
                )

                .mobileNumber(
                        doctor.getMobileNumber()
                )

                .specialization(
                        doctor.getSpecialization()
                )

                .build();
    }
}