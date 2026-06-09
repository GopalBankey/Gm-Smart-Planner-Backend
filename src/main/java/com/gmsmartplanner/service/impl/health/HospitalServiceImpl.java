package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateHospitalRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateHospitalRequestDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Hospital;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.HospitalMapper;
import com.gmsmartplanner.repository.health.HospitalRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.HospitalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HospitalServiceImpl
        implements HospitalService {

    private final HospitalRepository
            hospitalRepository;

    private final HospitalMapper
            hospitalMapper;

    private final UserHelperService
            userHelperService;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public HospitalResponseDTO createHospital(

            String username,

            CreateHospitalRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Hospital hospital =
                hospitalMapper
                        .createHospital(
                                dto
                        );

        hospital.setUser(
                user
        );

        Hospital savedHospital =
                hospitalRepository
                        .save(
                                hospital
                        );

        log.info(
                "Hospital created : {}",
                savedHospital.getId()
        );

        return hospitalMapper
                .mapToResponse(
                        savedHospital
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<HospitalResponseDTO>
    getHospitals(

            String username

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return hospitalRepository
                .findAllByUserAndActiveTrueOrderByHospitalNameAsc(
                        user
                )
                .stream()
                .map(
                        hospitalMapper
                                ::mapToResponse
                )
                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public HospitalResponseDTO getHospital(

            String username,

            Long hospitalId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        return hospitalMapper
                .mapToResponse(

                        getHospitalEntity(

                                hospitalId,

                                user
                        )
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public HospitalResponseDTO updateHospital(

            String username,

            Long hospitalId,

            UpdateHospitalRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Hospital hospital =
                getHospitalEntity(

                        hospitalId,

                        user
                );

        hospitalMapper
                .updateHospital(

                        hospital,

                        dto
                );

        Hospital updated =
                hospitalRepository
                        .save(
                                hospital
                        );

        return hospitalMapper
                .mapToResponse(
                        updated
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteHospital(

            String username,

            Long hospitalId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        Hospital hospital =
                getHospitalEntity(

                        hospitalId,

                        user
                );

        hospital.setActive(
                false
        );

        hospitalRepository
                .save(
                        hospital
                );

        log.info(
                "Hospital deleted : {}",
                hospitalId
        );
    }

    // =====================================
    // GET ENTITY
    // =====================================

    private Hospital getHospitalEntity(

            Long hospitalId,

            User user

    ) {

        return hospitalRepository
                .findByIdAndUserAndActiveTrue(

                        hospitalId,

                        user
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(

                                "Hospital not found"
                        )
                );
    }
}