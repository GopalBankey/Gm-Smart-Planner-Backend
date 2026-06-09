package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateDoctorRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateDoctorRequestDTO;
import com.gmsmartplanner.dto.response.health.DoctorResponseDTO;

import java.util.List;

public interface DoctorService {

    DoctorResponseDTO createDoctor(

            String username,

            CreateDoctorRequestDTO dto
    );

    List<DoctorResponseDTO> getDoctors(
            String username
    );

    DoctorResponseDTO getDoctor(

            String username,

            Long doctorId
    );

    DoctorResponseDTO updateDoctor(

            String username,

            Long doctorId,

            UpdateDoctorRequestDTO dto
    );

    void deleteDoctor(

            String username,

            Long doctorId
    );
}