package com.reclaimit.controller;

import com.reclaimit.entity.Item;
import com.reclaimit.entity.ItemType;
import com.reclaimit.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // ✅ POST LOST or FOUND ITEM (USER / ADMIN)
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        // email/username extracted from JWT
        String email = authentication.getName();

        Item savedItem = itemService.createItem(item, email);
        return ResponseEntity.ok(savedItem);
    }

    // ✅ GET ALL ITEMS
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // ✅ GET ONLY LOST ITEMS
    @GetMapping("/lost")
    public ResponseEntity<List<Item>> getLostItems() {
        return ResponseEntity.ok(itemService.getItemsByType(ItemType.LOST));
    }

    // ✅ GET ONLY FOUND ITEMS
    @GetMapping("/found")
    public ResponseEntity<List<Item>> getFoundItems() {
        return ResponseEntity.ok(itemService.getItemsByType(ItemType.FOUND));
    }
}
