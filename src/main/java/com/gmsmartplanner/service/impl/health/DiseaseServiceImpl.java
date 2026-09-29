package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateDiseaseRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDiseaseRequestDTO;
import com.gmsmartplanner.dto.response.health.DiseaseResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Disease;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.entity.health.Medicine;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.DiseaseMapper;
import com.gmsmartplanner.repository.health.DiseaseRepository;
import com.gmsmartplanner.repository.health.DoctorRepository;
import com.gmsmartplanner.repository.health.HospitalRepository;
import com.gmsmartplanner.repository.health.MedicineRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiseaseServiceImpl
        implements DiseaseService {

    private final DiseaseRepository
            diseaseRepository;

    private final MedicineRepository
            medicineRepository;

    private final DoctorRepository
            doctorRepository;

    private final HospitalRepository
            hospitalRepository;

    private final DiseaseMapper
            diseaseMapper;

    private final UserHelperService
            userHelperService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public DiseaseResponseDTO
    createDisease(

            String username,

            CreateDiseaseRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Disease disease =
                diseaseMapper
                        .createDisease(
                                dto
                        );

        disease.setUser(
                user
        );

        // =====================================
        // MEDICINES
        // =====================================

        disease.setMedicines(
                getMedicines(
                        dto.getMedicineIds(),
                        user
                )
        );

        // =====================================
        // DOCTOR
        // =====================================

        if (dto.getDoctorId() != null) {

            disease.setDoctor(

                    doctorRepository
                            .findByIdAndUserAndActiveTrue(

                                    dto.getDoctorId(),

                                    user
                            )

                            .orElseThrow(
                                    () ->
                                            new ResourceNotFoundException(
                                                    "Doctor not found"
                                            )
                            )
            );
        }

        // =====================================
        // HOSPITAL
        // =====================================

        if (dto.getHospitalId() != null) {

            disease.setHospital(

                    hospitalRepository
                            .findByIdAndUserAndActiveTrue(

                                    dto.getHospitalId(),

                                    user
                            )

                            .orElseThrow(
                                    () ->
                                            new ResourceNotFoundException(
                                                    "Hospital not found"
                                            )
                            )
            );
        }

        // =====================================
        // SAVE
        // =====================================

        Disease saved =
                diseaseRepository
                        .save(
                                disease
                        );

        return diseaseMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<DiseaseResponseDTO>
    getDiseases(
            String username
    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return diseaseRepository
                .findAllByUserAndActiveTrueOrderByCreatedAtDesc(
                        user
                )
                .stream()
                .map(
                        diseaseMapper
                                ::mapToResponse
                )
                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public DiseaseResponseDTO
    getDisease(

            String username,

            Long diseaseId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Disease disease =
                getDiseaseEntity(
                        user,
                        diseaseId
                );

        return diseaseMapper
                .mapToResponse(
                        disease
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public DiseaseResponseDTO
    updateDisease(

            String username,

            Long diseaseId,

            UpdateDiseaseRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Disease disease =
                getDiseaseEntity(
                        user,
                        diseaseId
                );

        // =====================================
        // BASIC DETAILS
        // =====================================

        if (dto.getDiseaseName() != null) {

            disease.setDiseaseName(
                    dto.getDiseaseName()
            );
        }

        if (dto.getDiseaseSince() != null) {

            disease.setDiseaseSince(
                    dto.getDiseaseSince()
            );
        }

        if (dto.getReferencePerson() != null) {

            disease.setReferencePerson(
                    dto.getReferencePerson()
            );
        }

        // =====================================
        // MEDICINES
        // =====================================

        if (dto.getMedicineIds() != null) {

            disease.setMedicines(
                    getMedicines(
                            dto.getMedicineIds(),
                            user
                    )
            );
        }

        // =====================================
        // DOCTOR
        // =====================================

        if (dto.getDoctorId() != null) {

            disease.setDoctor(

                    doctorRepository
                            .findByIdAndUserAndActiveTrue(

                                    dto.getDoctorId(),

                                    user
                            )

                            .orElseThrow(
                                    () ->
                                            new ResourceNotFoundException(
                                                    "Doctor not found"
                                            )
                            )
            );
        }

        // =====================================
        // HOSPITAL
        // =====================================

// =====================================
// HOSPITAL
// =====================================

        if (dto.getHospitalId() != null) {

            disease.setHospital(

                    hospitalRepository
                            .findByIdAndUserAndActiveTrue(

                                    dto.getHospitalId(),

                                    user
                            )

                            .orElseThrow(
                                    () ->
                                            new ResourceNotFoundException(
                                                    "Hospital not found"
                                            )
                            )
            );
        }
        Disease updated =
                diseaseRepository
                        .save(
                                disease
                        );

        return diseaseMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteDisease(

            String username,

            Long diseaseId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Disease disease =
                getDiseaseEntity(
                        user,
                        diseaseId
                );

        disease.setActive(
                false
        );

        diseaseRepository
                .save(
                        disease
                );
    }

    // =====================================
    // HELPERS
    // =====================================

    private Disease
    getDiseaseEntity(

            User user,

            Long diseaseId

    ) {

        return diseaseRepository
                .findByIdAndUserAndActiveTrue(

                        diseaseId,

                        user
                )

                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Disease not found"
                                )
                );
    }

    // =====================================
    // GET MEDICINES
    // =====================================

    private List<Medicine>
    getMedicines(

            List<Long> medicineIds,

            User user

    ) {

        List<Medicine>
                medicines =

                new ArrayList<>();

        if (
                medicineIds == null
                        ||
                        medicineIds.isEmpty()
        ) {

            return medicines;
        }

        for (
                Long medicineId
                :
                medicineIds
        ) {

            Medicine medicine =

                    medicineRepository
                            .findByIdAndUserAndActiveTrue(

                                    medicineId,

                                    user
                            )

                            .orElseThrow(
                                    () ->
                                            new ResourceNotFoundException(
                                                    "Medicine not found: "
                                                            + medicineId
                                            )
                            );

            medicines.add(
                    medicine
            );
        }

        return medicines;
    }
}