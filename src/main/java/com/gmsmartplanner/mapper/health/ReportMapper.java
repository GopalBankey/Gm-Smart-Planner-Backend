package com.gmsmartplanner.mapper.health;

import com.gmsmartplanner.dto.request.health.CreateReportRequestDTO;
import com.gmsmartplanner.dto.request.health.UpdateReportRequestDTO;
import com.gmsmartplanner.dto.response.health.ReportResponseDTO;
import com.gmsmartplanner.entity.health.HealthReport;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    // =====================================
    // CREATE
    // =====================================

    public HealthReport createReport(

            CreateReportRequestDTO dto

    ) {

        HealthReport report =
                new HealthReport();

        report.setReportName(
                dto.getReportName()
        );

        report.setLabName(
                dto.getLabName()
        );

        report.setCountryCode(

                dto.getCountryCode() == null

                        ?

                        "+91"

                        :

                        dto.getCountryCode()
        );

        report.setLabMobileNumber(
                dto.getLabMobileNumber()
        );

        report.setLabAddress(
                dto.getLabAddress()
        );

        report.setReportDate(
                dto.getReportDate()
        );

        report.setReportTime(
                dto.getReportTime()
        );

        report.setNotes(
                dto.getNotes()
        );

        report.setActive(
                true
        );

        return report;
    }

    // =====================================
    // UPDATE
    // =====================================

    public void updateReport(

            HealthReport report,

            UpdateReportRequestDTO dto

    ) {

        if (
                dto.getReportName()
                        != null
        ) {

            report.setReportName(
                    dto.getReportName()
            );
        }

        if (
                dto.getLabName()
                        != null
        ) {

            report.setLabName(
                    dto.getLabName()
            );
        }

        if (
                dto.getCountryCode()
                        != null
        ) {

            report.setCountryCode(
                    dto.getCountryCode()
            );
        }

        if (
                dto.getLabMobileNumber()
                        != null
        ) {

            report.setLabMobileNumber(
                    dto.getLabMobileNumber()
            );
        }

        if (
                dto.getLabAddress()
                        != null
        ) {

            report.setLabAddress(
                    dto.getLabAddress()
            );
        }

        if (
                dto.getReportDate()
                        != null
        ) {

            report.setReportDate(
                    dto.getReportDate()
            );
        }

        if (
                dto.getReportTime()
                        != null
        ) {

            report.setReportTime(
                    dto.getReportTime()
            );
        }

        if (
                dto.getNotes()
                        != null
        ) {

            report.setNotes(
                    dto.getNotes()
            );
        }
    }

    // =====================================
    // RESPONSE
    // =====================================

    public ReportResponseDTO mapToResponse(

            HealthReport report

    ) {

        return ReportResponseDTO
                .builder()

                .id(
                        report.getId()
                )

                .reportName(
                        report.getReportName()
                )

                .doctorId(

                        report.getDoctor() != null

                                ?

                                report.getDoctor()
                                        .getId()

                                :

                                null
                )

                .doctorName(

                        report.getDoctor() != null

                                ?

                                report.getDoctor()
                                        .getDoctorName()

                                :

                                null
                )

                .labName(
                        report.getLabName()
                )

                .countryCode(
                        report.getCountryCode()
                )

                .labMobileNumber(
                        report.getLabMobileNumber()
                )

                .labAddress(
                        report.getLabAddress()
                )

                .reportDate(
                        report.getReportDate()
                )

                .reportTime(
                        report.getReportTime()
                )

                .notes(
                        report.getNotes()
                )

                .reportFile(
                        report.getReportFile()
                )

                .build();
    }
}