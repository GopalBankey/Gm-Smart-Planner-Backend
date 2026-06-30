package com.gmsmartplanner.service.budget;

import com.gmsmartplanner.dto.request.budget.CreateEmiRequestDTO;
import com.gmsmartplanner.dto.request.budget.UpdateEmiRequestDTO;
import com.gmsmartplanner.dto.response.budget.EmiResponseDTO;

import java.util.List;

public interface EmiService {

    // =====================================
    // CREATE EMI
    // =====================================

    EmiResponseDTO createEmi(

            String username,

            CreateEmiRequestDTO dto
    );

    // =====================================
    // GET ALL EMIS
    // =====================================

    List<EmiResponseDTO> getEmis(

            String username
    );

    // =====================================
    // GET EMI
    // =====================================

    EmiResponseDTO getEmi(

            String username,

            Long emiId
    );

    // =====================================
    // UPDATE EMI
    // =====================================

    EmiResponseDTO updateEmi(

            String username,

            Long emiId,

            UpdateEmiRequestDTO dto
    );

    // =====================================
    // DELETE EMI
    // =====================================

    void deleteEmi(

            String username,

            Long emiId
    );
}