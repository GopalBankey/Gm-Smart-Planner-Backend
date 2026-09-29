package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateOperationRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;
import com.gmsmartplanner.dto.response.health.OperationResponseDTO;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.entity.health.Operation;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper {

    // =====================================
    // CREATE
    // =====================================

    public Operation
    createOperation(
            CreateOperationRequestDTO dto
    ) {

        Operation operation =
                new Operation();

        operation.setOperationName(
                dto.getOperationName()
        );

        operation.setDate(
                dto.getDate()
        );

        operation.setReason(
                dto.getReason()
        );

        operation.setActive(
                true
        );

        return operation;
    }

    // =====================================
    // RESPONSE
    // =====================================

    public OperationResponseDTO
    mapToResponse(
            Operation operation
    ) {

        return OperationResponseDTO
                .builder()

                .id(
                        operation.getId()
                )

                .operationName(
                        operation.getOperationName()
                )

                .date(
                        operation.getDate()
                )

                // =====================================
                // DOCTOR
                // =====================================

                .doctor(
                        mapDoctor(
                                operation.getDoctor()
                        )
                )

                // =====================================
                // HOSPITAL
                // =====================================

                .hospital(
                        mapHospital(
                                operation.getHospital()
                        )
                )

                .reason(
                        operation.getReason()
                )

                .relatedDocuments(
                        operation.getRelatedDocuments()
                )

                .active(
                        operation.isActive()
                )

                .build();
    }

    // =====================================
    // DOCTOR RESPONSE
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
    // HOSPITAL RESPONSE
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