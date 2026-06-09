package com.gmsmartplanner.dto.request.health;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakeMedicineRequestDTO {

    private Integer consumedDose;

    private String note;
}