package com.reclaimit.repository;

import com.reclaimit.entity.Report;
import com.reclaimit.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByStatus(ReportStatus status);
}
