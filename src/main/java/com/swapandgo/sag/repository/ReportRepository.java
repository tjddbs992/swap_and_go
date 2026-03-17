package com.swapandgo.sag.repository;

import com.swapandgo.sag.domain.report.Report;
import com.swapandgo.sag.domain.report.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStatusOrderByCreatedAtDesc(ReportStatus status);
}
