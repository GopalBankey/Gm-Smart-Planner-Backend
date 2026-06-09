package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class MedicineScheduleRequestDTO {

    private String slotName;

    private LocalTime time;

    private Integer doseCount;

    private boolean enabled;
}