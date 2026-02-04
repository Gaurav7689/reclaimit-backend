package com.reclaimit.controller;

import com.reclaimit.entity.Report;
import com.reclaimit.service.ReportService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<Report> reportItem(
            @PathVariable Long itemId,
            @RequestBody Report request
    ) {
        // ✅ GET LOGGED-IN USER EMAIL
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName(); // ← THIS IS JWT SUBJECT

        Report report = reportService.createReport(
                itemId,
                request.getMessage(),
                email
        );

        return ResponseEntity.ok(report);
    }



    // ✅ ADMIN – VIEW ALL REPORTS
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }
}
