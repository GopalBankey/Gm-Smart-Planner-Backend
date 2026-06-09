package com.gmsmartplanner.service.health;

import com.gmsmartplanner.dto.request.health.CreateReportRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateReportRequestDTO;
import com.gmsmartplanner.dto.response.health.ReportResponseDTO;

import java.util.List;

public interface ReportService {

    ReportResponseDTO createReport(

            String username,

            CreateReportRequestDTO dto
    );

    List<ReportResponseDTO> getReports(
            String username
    );

    ReportResponseDTO getReport(

            String username,

            Long reportId
    );

    ReportResponseDTO updateReport(

            String username,

            Long reportId,

            UpdateReportRequestDTO dto
    );

    void deleteReport(

            String username,

            Long reportId
    );
}