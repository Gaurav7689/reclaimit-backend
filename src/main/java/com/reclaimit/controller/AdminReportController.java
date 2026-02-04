package com.reclaimit.controller;

import com.reclaimit.entity.Report;
import com.reclaimit.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private final ReportService reportService;

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // ✅ CHECK ADMIN ROLE (CORRECT WAY)
    private boolean isAdmin() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) return false;

        return authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // ✅ ADMIN – GET ALL REPORTS
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {

        if (!isAdmin()) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(reportService.getAllReports());
    }

    // ✅ ADMIN – APPROVE REPORT
    @PostMapping("/{reportId}/approve")
    public ResponseEntity<Report> approveReport(@PathVariable Long reportId) {

        if (!isAdmin()) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(reportService.approveReport(reportId));
    }

    // ✅ ADMIN – REJECT REPORT
    @PostMapping("/{reportId}/reject")
    public ResponseEntity<Report> rejectReport(@PathVariable Long reportId) {

        if (!isAdmin()) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(reportService.rejectReport(reportId));
    }
}
