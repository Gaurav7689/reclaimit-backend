package com.reclaimit.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reclaimit.entity.Item;
import com.reclaimit.entity.ItemStatus;
import com.reclaimit.repository.ItemRepository;

@RestController
@RequestMapping("/api/admin/items")
public class AdminItemController {

    private final ItemRepository repo;

    public AdminItemController(ItemRepository repo) {
        this.repo = repo;
    }

    // ✅ GET ALL ITEMS
    @GetMapping
    public List<Item> getAll() {
        return repo.findAll();
    }

    // ✅ UPDATE ITEM STATUS (SAFE)
    @PutMapping("/{id}/status")
    public ResponseEntity<Item> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Item item = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        try {
            ItemStatus itemStatus = ItemStatus.valueOf(status.toUpperCase());
            item.setStatus(itemStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(repo.save(item));
    }
}
