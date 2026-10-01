package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdatePrescriptionRequestDTO;
import com.gmsmartplanner.dto.response.health.PrescriptionResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.entity.health.Prescription;
import com.gmsmartplanner.exception.InvalidRequestException;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.PrescriptionMapper;
import com.gmsmartplanner.repository.health.DoctorRepository;
import com.gmsmartplanner.repository.health.HospitalRepository;
import com.gmsmartplanner.repository.health.PrescriptionRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class PrescriptionServiceImpl
        implements PrescriptionService {

    private final PrescriptionRepository
            prescriptionRepository;

    private final DoctorRepository
            doctorRepository;

    private final HospitalRepository
            hospitalRepository;

    private final PrescriptionMapper
            prescriptionMapper;

    private final UserHelperService
            userHelperService;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public PrescriptionResponseDTO
    createPrescription(

            String username,

            CreatePrescriptionRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Prescription prescription =
                prescriptionMapper
                        .createPrescription(
                                dto
                        );

        prescription.setUser(
                user
        );

        // =====================================
        // DOCTOR
        // =====================================

        Doctor doctor =

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
                        );

        prescription.setDoctor(
                doctor
        );

        // =====================================
        // HOSPITAL
        // =====================================

        Hospital hospital =

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
                        );

        prescription.setHospital(
                hospital
        );

        // =====================================
        // PHOTO
        // =====================================

        uploadPrescriptionPhoto(
                prescription,
                dto.getPrescriptionPhoto()
        );

        // =====================================
        // SAVE
        // =====================================

        Prescription saved =
                prescriptionRepository
                        .save(
                                prescription
                        );

        return prescriptionMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public java.util.List<PrescriptionResponseDTO>
    getPrescriptions(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return prescriptionRepository
                .findAllByUserAndActiveTrueOrderByPrescriptionDateDesc(
                        user
                )

                .stream()

                .map(
                        prescriptionMapper
                                ::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponseDTO
    getPrescription(

            String username,

            Long prescriptionId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Prescription prescription =
                getPrescriptionEntity(
                        user,
                        prescriptionId
                );

        return prescriptionMapper
                .mapToResponse(
                        prescription
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public PrescriptionResponseDTO
    updatePrescription(

            String username,

            Long prescriptionId,

            UpdatePrescriptionRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Prescription prescription =
                getPrescriptionEntity(
                        user,
                        prescriptionId
                );

        // =====================================
        // BASIC DETAILS
        // =====================================

        prescriptionMapper
                .updatePrescription(
                        prescription,
                        dto
                );

        // =====================================
        // DOCTOR
        // =====================================

        if (
                dto.getDoctorId() != null
        ) {

            Doctor doctor =

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
                            );

            prescription.setDoctor(
                    doctor
            );
        }

        // =====================================
        // HOSPITAL
        // =====================================

        if (
                dto.getHospitalId() != null
        ) {

            Hospital hospital =

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
                            );

            prescription.setHospital(
                    hospital
            );
        }

        // =====================================
        // PHOTO
        // =====================================

        if (
                dto.getPrescriptionPhoto() != null
                        &&
                        !dto.getPrescriptionPhoto().isEmpty()
        ) {

            prescription.setPrescriptionPhoto(

                    fileUploadService
                            .uploadImage(

                                    dto.getPrescriptionPhoto(),

                                    "prescriptions"
                            )
            );
        }

        // =====================================
        // SAVE
        // =====================================

        Prescription updated =
                prescriptionRepository
                        .save(
                                prescription
                        );

        return prescriptionMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deletePrescription(

            String username,

            Long prescriptionId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Prescription prescription =
                getPrescriptionEntity(
                        user,
                        prescriptionId
                );

        prescription.setActive(
                false
        );

        prescriptionRepository
                .save(
                        prescription
                );
    }

    // =====================================
    // GET ENTITY
    // =====================================

    private Prescription
    getPrescriptionEntity(

            User user,

            Long prescriptionId

    ) {

        return prescriptionRepository
                .findByIdAndUserAndActiveTrue(

                        prescriptionId,

                        user
                )

                .orElseThrow(

                        () ->
                                new ResourceNotFoundException(
                                        "Prescription not found"
                                )
                );
    }
// =====================================
// UPLOAD PHOTO
// =====================================

    private void uploadPrescriptionPhoto(

            Prescription prescription,

            MultipartFile file

    ) {

        if (
                file == null
                        ||
                        file.isEmpty()
        ) {

            throw new InvalidRequestException(
                    "Prescription photo is required"
            );
        }

        prescription.setPrescriptionPhoto(

                fileUploadService
                        .uploadImage(

                                file,

                                "prescriptions"
                        )
        );
    }
}