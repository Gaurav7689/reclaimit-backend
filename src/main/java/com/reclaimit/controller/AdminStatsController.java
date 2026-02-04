package com.reclaimit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reclaimit.repository.ItemRepository;
import com.reclaimit.repository.ReportRepository;
import com.reclaimit.repository.UserRepository;

@RestController
@RequestMapping("/api/admin/stats")
public class AdminStatsController {

    private final ItemRepository itemRepo;
    private final ReportRepository reportRepo;
    private final UserRepository userRepo;

    public AdminStatsController(
            ItemRepository itemRepo,
            ReportRepository reportRepo,
            UserRepository userRepo
    ) {
        this.itemRepo = itemRepo;
        this.reportRepo = reportRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("items", itemRepo.count());
        stats.put("reports", reportRepo.count());
        stats.put("users", userRepo.count());

        return stats;
    }
}
