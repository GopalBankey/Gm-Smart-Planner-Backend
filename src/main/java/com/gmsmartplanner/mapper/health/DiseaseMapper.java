package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateDiseaseRequestDTO;
import com.gmsmartplanner.dto.response.health.DiseaseResponseDTO;
import com.gmsmartplanner.dto.response.health.MedicineResponseDTO;
import com.gmsmartplanner.entity.health.Disease;
import org.springframework.stereotype.Component;

@Component
public class DiseaseMapper {

    private final DoctorMapper
            doctorMapper;

    private final HospitalMapper
            hospitalMapper;

    public DiseaseMapper(

            DoctorMapper doctorMapper,

            HospitalMapper hospitalMapper

    ) {

        this.doctorMapper =
                doctorMapper;

        this.hospitalMapper =
                hospitalMapper;
    }

    // =====================================
    // CREATE
    // =====================================

    public Disease
    createDisease(

            CreateDiseaseRequestDTO dto

    ) {

        Disease disease =
                new Disease();

        disease.setDiseaseName(
                dto.getDiseaseName()
        );

        disease.setDiseaseSince(
                dto.getDiseaseSince()
        );

        disease.setReferencePerson(
                dto.getReferencePerson()
        );

        disease.setActive(
                true
        );

        return disease;
    }

    // =====================================
    // RESPONSE
    // =====================================

    public DiseaseResponseDTO
    mapToResponse(

            Disease disease

    ) {

        return DiseaseResponseDTO
                .builder()

                .id(
                        disease.getId()
                )

                .diseaseName(
                        disease.getDiseaseName()
                )

                .diseaseSince(
                        disease.getDiseaseSince()
                )

                .medicines(

                        disease.getMedicines()
                                .stream()

                                .filter(
                                        medicine ->
                                                medicine.isActive()
                                )

                                .map(
                                        medicine ->
                                                MedicineResponseDTO
                                                        .builder()

                                                        .id(
                                                                medicine.getId()
                                                        )

                                                        .medicineName(
                                                                medicine.getMedicineName()
                                                        )

                                                        .dosage(
                                                                medicine.getDosage()
                                                        )

                                                        .form(
                                                                medicine.getForm()
                                                        )

                                                        .purpose(
                                                                medicine.getPurpose()
                                                        )

                                                        .mealType(
                                                                medicine.getMealType()
                                                        )

                                                        .pillColor(
                                                                medicine.getPillColor()
                                                        )

                                                        .pillPhoto(
                                                                medicine.getPillPhoto()
                                                        )

                                                        .companyName(
                                                                medicine.getCompanyName()
                                                        )

                                                        .currentStock(
                                                                medicine.getCurrentStock()
                                                        )

                                                        .expiryDate(
                                                                medicine.getExpiryDate()
                                                        )

                                                        .build()
                                )

                                .toList()
                )

                // =====================================
                // DOCTOR
                // =====================================

                .doctor(

                        disease.getDoctor() != null
                                ?
                                doctorMapper
                                        .mapToResponse(
                                                disease.getDoctor()
                                        )
                                :
                                null
                )

                // =====================================
                // HOSPITAL
                // =====================================

                .hospital(

                        disease.getHospital() != null
                                ?
                                hospitalMapper
                                        .mapToResponse(
                                                disease.getHospital()
                                        )
                                :
                                null
                )

                .referencePerson(
                        disease.getReferencePerson()
                )

                .active(
                        disease.isActive()
                )

                .build();
    }
}