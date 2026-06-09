package com.gmsmartplanner.repository.health;

import com.gmsmartplanner.entity.User;
import com.gmsmartplanner.entity.health.HealthReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository
        extends JpaRepository<HealthReport, Long> {

    List<HealthReport>
    findAllByUserAndActiveTrueOrderByReportDateDesc(

            User user
    );

    Optional<HealthReport>
    findByIdAndUserAndActiveTrue(

            Long id,

            User user
    );
}