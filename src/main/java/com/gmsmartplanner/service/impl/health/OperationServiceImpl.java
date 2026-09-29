package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateOperationRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateOperationRequestDTO;
import com.gmsmartplanner.dto.response.health.OperationResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.entity.health.Operation;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.OperationMapper;
import com.gmsmartplanner.repository.health.DoctorRepository;
import com.gmsmartplanner.repository.health.HospitalRepository;
import com.gmsmartplanner.repository.health.OperationRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationServiceImpl
        implements OperationService {

    private final OperationRepository
            operationRepository;

    private final DoctorRepository
            doctorRepository;

    private final HospitalRepository
            hospitalRepository;

    private final OperationMapper
            operationMapper;

    private final UserHelperService
            userHelperService;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public OperationResponseDTO
    createOperation(

            String username,

            CreateOperationRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Operation operation =
                operationMapper
                        .createOperation(
                                dto
                        );

        operation.setUser(
                user
        );

        // =====================================
        // HOSPITAL
        // =====================================

        operation.setHospital(

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

        // =====================================
        // DOCTOR
        // =====================================

        operation.setDoctor(

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

        // =====================================
        // DOCUMENTS
        // =====================================

        uploadDocuments(
                operation,
                dto.getFiles()
        );

        // =====================================
        // SAVE
        // =====================================

        Operation saved =
                operationRepository
                        .save(
                                operation
                        );

        return operationMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public List<OperationResponseDTO>
    getOperations(
            String username
    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return operationRepository
                .findAllByUserAndActiveTrueOrderByDateDesc(
                        user
                )

                .stream()

                .map(
                        operationMapper
                                ::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    @Transactional(readOnly = true)
    public OperationResponseDTO
    getOperation(

            String username,

            Long operationId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Operation operation =
                getOperationEntity(
                        user,
                        operationId
                );

        return operationMapper
                .mapToResponse(
                        operation
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public OperationResponseDTO
    updateOperation(

            String username,

            Long operationId,

            UpdateOperationRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Operation operation =
                getOperationEntity(
                        user,
                        operationId
                );

        // =====================================
        // BASIC DETAILS
        // =====================================

        if (dto.getOperationName() != null) {

            operation.setOperationName(
                    dto.getOperationName()
            );
        }

        if (dto.getDate() != null) {

            operation.setDate(
                    dto.getDate()
            );
        }

        if (dto.getReason() != null) {

            operation.setReason(
                    dto.getReason()
            );
        }

        // =====================================
        // HOSPITAL
        // =====================================

        if (dto.getHospitalId() != null) {

            operation.setHospital(

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
        // DOCTOR
        // =====================================

        if (dto.getDoctorId() != null) {

            operation.setDoctor(

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
        // ADD NEW DOCUMENTS
        // =====================================

        uploadDocuments(
                operation,
                dto.getFiles()
        );

        // =====================================
        // SAVE
        // =====================================

        Operation updated =
                operationRepository
                        .save(
                                operation
                        );

        return operationMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteOperation(

            String username,

            Long operationId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Operation operation =
                getOperationEntity(
                        user,
                        operationId
                );

        operation.setActive(
                false
        );

        operationRepository
                .save(
                        operation
                );
    }

    // =====================================
    // GET ENTITY
    // =====================================

    private Operation
    getOperationEntity(

            User user,

            Long operationId

    ) {

        return operationRepository
                .findByIdAndUserAndActiveTrue(

                        operationId,

                        user
                )

                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Operation not found"
                                )
                );
    }

    // =====================================
    // UPLOAD DOCUMENTS
    // =====================================

    private void uploadDocuments(

            Operation operation,

            List<MultipartFile> files

    ) {

        if (
                files == null
                        ||
                        files.isEmpty()
        ) {

            return;
        }

        if (
                operation.getRelatedDocuments()
                        == null
        ) {

            operation.setRelatedDocuments(
                    new ArrayList<>()
            );
        }

        for (
                MultipartFile file
                :
                files
        ) {

            if (
                    file == null
                            ||
                            file.isEmpty()
            ) {

                continue;
            }

            String fileUrl =
                    fileUploadService
                            .uploadImage(
                                    file,
                                    "operations"
                            );

            operation
                    .getRelatedDocuments()
                    .add(
                            fileUrl
                    );
        }
    }
}