package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateHospitalRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateHospitalRequestDTO;
import com.gmsmartplanner.dto.response.health.HospitalResponseDTO;

import java.util.List;

public interface HospitalService {

    // =====================================
    // CREATE
    // =====================================

    HospitalResponseDTO createHospital(

            String username,

            CreateHospitalRequestDTO dto
    );

    // =====================================
    // GET ALL
    // =====================================

    List<HospitalResponseDTO>
    getHospitals(

            String username
    );

    // =====================================
    // GET BY ID
    // =====================================

    HospitalResponseDTO getHospital(

            String username,

            Long hospitalId
    );

    // =====================================
    // UPDATE
    // =====================================

    HospitalResponseDTO updateHospital(

            String username,

            Long hospitalId,

            UpdateHospitalRequestDTO dto
    );

    // =====================================
    // DELETE
    // =====================================

    void deleteHospital(

            String username,

            Long hospitalId
    );
}