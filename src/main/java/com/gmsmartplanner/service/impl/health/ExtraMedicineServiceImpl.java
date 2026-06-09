package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateExtraMedicineRequestDTO;
import com.gmsmartplanner.dto.response.health.ExtraMedicineResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.ExtraMedicine;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.ExtraMedicineMapper;
import com.gmsmartplanner.repository.health.ExtraMedicineRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.ExtraMedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExtraMedicineServiceImpl
        implements ExtraMedicineService {

    private final ExtraMedicineRepository
            extraMedicineRepository;

    private final ExtraMedicineMapper
            extraMedicineMapper;

    private final UserHelperService
            userHelperService;

    private final FileUploadService
            fileUploadService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public ExtraMedicineResponseDTO createMedicine(

            String username,

            CreateExtraMedicineRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        ExtraMedicine medicine =
                extraMedicineMapper
                        .createExtraMedicine(
                                dto
                        );

        medicine.setUser(
                user
        );

        if (dto.getPillPhoto() != null
                && !dto.getPillPhoto().isEmpty()) {

            medicine.setPillPhoto(

                    fileUploadService
                            .uploadImage(

                                    dto.getPillPhoto(),

                                    "extra-medicine"
                            )
            );
        }

        ExtraMedicine saved =
                extraMedicineRepository
                        .save(
                                medicine
                        );

        return extraMedicineMapper
                .mapToResponse(
                        saved
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<ExtraMedicineResponseDTO>
    getMedicines(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return extraMedicineRepository

                .findAllByUserAndActiveTrueOrderByMedicineNameAsc(
                        user
                )

                .stream()

                .map(
                        extraMedicineMapper
                                ::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public ExtraMedicineResponseDTO getMedicine(

            String username,

            Long medicineId

    ) {

        return extraMedicineMapper
                .mapToResponse(

                        getMedicineEntity(

                                medicineId,

                                username
                        )
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public ExtraMedicineResponseDTO updateMedicine(

            String username,

            Long medicineId,

            UpdateExtraMedicineRequestDTO dto

    ) {

        ExtraMedicine medicine =
                getMedicineEntity(

                        medicineId,

                        username
                );

        extraMedicineMapper
                .updateExtraMedicine(

                        medicine,

                        dto
                );

        if (dto.getPillPhoto() != null
                && !dto.getPillPhoto().isEmpty()) {

            medicine.setPillPhoto(

                    fileUploadService
                            .uploadImage(

                                    dto.getPillPhoto(),

                                    "extra-medicine"
                            )
            );
        }

        ExtraMedicine updated =
                extraMedicineRepository
                        .save(
                                medicine
                        );

        return extraMedicineMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteMedicine(

            String username,

            Long medicineId

    ) {

        ExtraMedicine medicine =
                getMedicineEntity(

                        medicineId,

                        username
                );

        medicine.setActive(
                false
        );

        extraMedicineRepository
                .save(
                        medicine
                );
    }

    // =====================================
    // ENTITY
    // =====================================

    private ExtraMedicine getMedicineEntity(

            Long medicineId,

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return extraMedicineRepository

                .findByIdAndUserAndActiveTrue(

                        medicineId,

                        user
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Medicine not found"
                        )
                );
    }
}