package com.gmsmartplanner.dto.response.health;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class MedicineScheduleResponseDTO {

    private Long id;

    private String slotName;

    private LocalTime time;

    private Integer doseCount;

    private boolean enabled;
}