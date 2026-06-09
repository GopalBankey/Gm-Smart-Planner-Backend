package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MedicineDashboardResponseDTO {

    private List<MissedMedicineResponseDTO>
            missedMedicines;

    private List<RefillSoonMedicineResponseDTO>
            refillSoonMedicines;

    private List<SessionMedicineResponseDTO>
            sessions;
}