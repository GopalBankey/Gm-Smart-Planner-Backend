package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;
import com.gmsmartplanner.dto.response.health.PrescriptionResponseDTO;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.entity.health.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    // =====================================
    // CREATE
    // =====================================

    public Prescription
    createPrescription(

            CreatePrescriptionRequestDTO dto

    ) {

        Prescription prescription =
                new Prescription();

        prescription.setPrescriptionDate(
                dto.getPrescriptionDate()
        );

        prescription.setRelatedDisease(
                dto.getRelatedDisease()
        );

        prescription.setActive(
                true
        );

        return prescription;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void
    updatePrescription(

            Prescription prescription,

            UpdatePrescriptionRequestDTO dto

    ) {

        if (
                dto.getPrescriptionDate()
                        != null
        ) {

            prescription.setPrescriptionDate(
                    dto.getPrescriptionDate()
            );
        }

        if (
                dto.getRelatedDisease()
                        != null
        ) {

            prescription.setRelatedDisease(
                    dto.getRelatedDisease()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public PrescriptionResponseDTO
    mapToResponse(

            Prescription prescription

    ) {

        return PrescriptionResponseDTO
                .builder()

                .id(
                        prescription.getId()
                )

                .prescriptionPhoto(
                        prescription.getPrescriptionPhoto()
                )

                .prescriptionDate(
                        prescription.getPrescriptionDate()
                )

                // =====================================
                // DOCTOR
                // =====================================

                .doctor(

                        mapDoctor(
                                prescription.getDoctor()
                        )
                )

                // =====================================
                // HOSPITAL
                // =====================================

                .hospital(

                        mapHospital(
                                prescription.getHospital()
                        )
                )

                .relatedDisease(
                        prescription.getRelatedDisease()
                )

                .active(
                        prescription.isActive()
                )

                .build();
    }

    // =====================================
    // DOCTOR
    // =====================================

    private DoctorResponseDTO
    mapDoctor(

            Doctor doctor

    ) {

        if (doctor == null) {
            return null;
        }

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

    // =====================================
    // HOSPITAL
    // =====================================

    private HospitalResponseDTO
    mapHospital(

            Hospital hospital

    ) {

        if (hospital == null) {
            return null;
        }

        return HospitalResponseDTO
                .builder()

                .id(
                        hospital.getId()
                )

                .hospitalName(
                        hospital.getHospitalName()
                )

                .countryCode(
                        hospital.getCountryCode()
                )

                .mobileNumber(
                        hospital.getMobileNumber()
                )

                .openingTime(
                        hospital.getOpeningTime()
                )

                .closingTime(
                        hospital.getClosingTime()
                )

                .address(
                        hospital.getAddress()
                )

                .build();
    }
}