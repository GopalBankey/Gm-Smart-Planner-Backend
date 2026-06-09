package com.gmsmartplanner.service.impl.health;

import com.gmsmartplanner.dto.request.health.CreateReportRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateReportRequestDTO;
import com.gmsmartplanner.dto.response.health.ReportResponseDTO;
import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.Doctor;
import com.gmsmartplanner.entity.health.HealthReport;
import com.gmsmartplanner.exception.ResourceNotFoundException;
import com.gmsmartplanner.mapper.health.ReportMapper;
import com.gmsmartplanner.repository.health.DoctorRepository;
import com.gmsmartplanner.repository.health.ReportRepository;
import com.gmsmartplanner.service.FileUploadService;
import com.gmsmartplanner.service.UserHelperService;
import com.gmsmartplanner.service.health.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl
        implements ReportService {

    private final ReportRepository
            reportRepository;

    private final DoctorRepository
            doctorRepository;

    private final UserHelperService
            userHelperService;

    private final FileUploadService
            fileUploadService;

    private final ReportMapper
            reportMapper;

    // =====================================
    // CREATE
    // =====================================

    @Override
    public ReportResponseDTO createReport(

            String username,

            CreateReportRequestDTO dto

    ) {

        User user =
                userHelperService
                        .getCurrentUser(
                                username
                        );

        HealthReport report =

                reportMapper
                        .createReport(
                                dto
                        );

        report.setUser(
                user
        );

        if (

                dto.getDoctorId()

                        != null

        ) {

            Doctor doctor =

                    doctorRepository

                            .findByIdAndUserAndActiveTrue(

                                    dto.getDoctorId(),

                                    user
                            )

                            .orElseThrow(

                                    () ->

                                            new ResourceNotFoundException(
                                                    "Doctor not found"
                                            )
                            );

            report.setDoctor(
                    doctor
            );
        }

        if (

                dto.getFile()

                        != null

                        &&

                        !dto.getFile()
                                .isEmpty()

        ) {

            report.setReportFile(

                    fileUploadService
                            .uploadImage(

                                    dto.getFile(),

                                    "reports"
                            )
            );
        }

        return reportMapper
                .mapToResponse(

                        reportRepository
                                .save(
                                        report
                                )
                );
    }

    // =====================================
    // GET ALL
    // =====================================

    @Override
    public List<ReportResponseDTO>
    getReports(

            String username

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        return reportRepository

                .findAllByUserAndActiveTrueOrderByReportDateDesc(

                        user
                )

                .stream()

                .map(
                        reportMapper
                                ::mapToResponse
                )

                .toList();
    }

    // =====================================
    // GET BY ID
    // =====================================

    @Override
    public ReportResponseDTO getReport(

            String username,

            Long reportId

    ) {

        return reportMapper
                .mapToResponse(

                        getReportEntity(

                                reportId,

                                userHelperService
                                        .getCurrentUser(
                                                username
                                        )
                        )
                );
    }

    // =====================================
    // UPDATE
    // =====================================

    @Override
    public ReportResponseDTO updateReport(

            String username,

            Long reportId,

            UpdateReportRequestDTO dto

    ) {

        User user =

                userHelperService
                        .getCurrentUser(
                                username
                        );

        HealthReport report =

                getReportEntity(

                        reportId,

                        user
                );

        reportMapper
                .updateReport(

                        report,

                        dto
                );

        if (

                dto.getDoctorId()

                        != null

        ) {

            Doctor doctor =

                    doctorRepository

                            .findByIdAndUserAndActiveTrue(

                                    dto.getDoctorId(),

                                    user
                            )

                            .orElseThrow(

                                    () ->

                                            new ResourceNotFoundException(
                                                    "Doctor not found"
                                            )
                            );

            report.setDoctor(
                    doctor
            );
        }

        if (

                dto.getFile()

                        != null

                        &&

                        !dto.getFile()
                                .isEmpty()

        ) {

            report.setReportFile(

                    fileUploadService
                            .uploadImage(

                                    dto.getFile(),

                                    "reports"
                            )
            );
        }

        return reportMapper
                .mapToResponse(

                        reportRepository
                                .save(
                                        report
                                )
                );
    }

    // =====================================
    // DELETE
    // =====================================

    @Override
    public void deleteReport(

            String username,

            Long reportId

    ) {

        HealthReport report =

                getReportEntity(

                        reportId,

                        userHelperService
                                .getCurrentUser(
                                        username
                                )
                );

        report.setActive(
                false
        );

        reportRepository
                .save(
                        report
                );
    }

    // =====================================
    // ENTITY
    // =====================================

    private HealthReport
    getReportEntity(

            Long reportId,

            User user

    ) {

        return reportRepository

                .findByIdAndUserAndActiveTrue(

                        reportId,

                        user
                )

                .orElseThrow(

                        () ->

                                new ResourceNotFoundException(

                                        "Report not found"
                                )
                );
    }
}