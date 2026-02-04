package com.reclaimit.service;

import com.reclaimit.entity.Item;
import com.reclaimit.entity.Report;
import com.reclaimit.entity.ReportStatus;
import com.reclaimit.entity.User;
import com.reclaimit.repository.ItemRepository;
import com.reclaimit.repository.ReportRepository;
import com.reclaimit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ReportService(
            ReportRepository reportRepository,
            ItemRepository itemRepository,
            UserRepository userRepository
    ) {
        this.reportRepository = reportRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    // ✅ USER – CREATE REPORT
    public Report createReport(Long itemId, String message, String reporterEmail) {

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        User reporter = userRepository.findByEmail(reporterEmail)
                .orElseThrow(() -> new RuntimeException("Reporter not found"));

        // 🚫 PREVENT OWNER FROM REPORTING OWN ITEM
        if (item.getPostedBy().getId().equals(reporter.getId())) {
            throw new RuntimeException("You cannot report your own item");
        }

        Report report = new Report();
        report.setItem(item);
        report.setReporter(reporter);
        report.setMessage(message);
        report.setStatus(ReportStatus.PENDING);

        return reportRepository.save(report);
    }

    // ✅ ADMIN – GET ALL REPORTS
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // ✅ ADMIN – APPROVE REPORT
    public Report approveReport(Long reportId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));

        report.setStatus(ReportStatus.APPROVED);
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }

    // ✅ ADMIN – REJECT REPORT
    public Report rejectReport(Long reportId) {

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new RuntimeException("Report not found"));

        report.setStatus(ReportStatus.REJECTED);
        report.setUpdatedAt(LocalDateTime.now());

        return reportRepository.save(report);
    }
}
