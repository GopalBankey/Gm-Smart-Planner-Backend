package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.response.health.MedicineDashboardResponseDTO;

public interface MedicineDashboardService {

    MedicineDashboardResponseDTO
    getDashboard(

            String username
    );
}