package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateDoctorRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDoctorRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.DoctorMapper;
import com.gmsmartplanner.repository.health.DoctorRepository;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl
        implements DoctorService {

    private final DoctorRepository
            doctorRepository;

    private final DoctorMapper
            doctorMapper;

    private final UserHelperService
            userHelperService;

    @Override
    public DoctorResponseDTO createDoctor(

            String username,

            CreateDoctorRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        Doctor doctor =
                doctorMapper
                        .createDoctor(dto);

        doctor.setUser(user);

        Doctor savedDoctor =
                doctorRepository.save(
                        doctor
                );

        return doctorMapper
                .mapToResponse(
                        savedDoctor
                );
    }

    @Override
    public List<DoctorResponseDTO> getDoctors(
            String username
    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        return doctorRepository
                .findAllByUserAndActiveTrueOrderByDoctorNameAsc(
                        user
                )
                .stream()
                .map(doctorMapper::mapToResponse)
                .toList();
    }

    @Override
    public DoctorResponseDTO getDoctor(

            String username,

            Long doctorId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        return doctorMapper
                .mapToResponse(

                        getDoctorEntity(
                                doctorId,
                                user
                        )
                );
    }

    @Override
    public DoctorResponseDTO updateDoctor(

            String username,

            Long doctorId,

            UpdateDoctorRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        Doctor doctor =
                getDoctorEntity(
                        doctorId,
                        user
                );

        doctorMapper.updateDoctor(
                doctor,
                dto
        );

        Doctor updatedDoctor =
                doctorRepository.save(
                        doctor
                );

        return doctorMapper
                .mapToResponse(
                        updatedDoctor
                );
    }

    @Override
    public void deleteDoctor(

            String username,

            Long doctorId

    ) {

        User user =
                userHelperService
                        .getCurrentUser(username);

        Doctor doctor =
                getDoctorEntity(
                        doctorId,
                        user
                );

        doctor.setActive(false);

        doctorRepository.save(
                doctor
        );
    }

    private Doctor getDoctorEntity(

            Long doctorId,

            User user

    ) {

        return doctorRepository
                .findByIdAndUserAndActiveTrue(
                        doctorId,
                        user
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found"
                        )
                );
    }
}