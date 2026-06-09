package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SessionMedicineResponseDTO {

    private String
            session;

    private Integer
            upcomingCount;

    private List<
            MedicineCardResponseDTO
            >
            medicines;
}