package com.reclaimit.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.reclaimit.entity.Item;
import com.reclaimit.entity.User;
import com.reclaimit.repository.ItemRepository;
import com.reclaimit.repository.UserRepository;

@RestController
@RequestMapping("/api/user/items")
public class UserItemController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public UserItemController(ItemRepository itemRepository,
                              UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    // ✅ 1. GET MY POSTED ITEMS (MyItems page)
    @GetMapping
    public List<Item> getMyItems(Principal principal) {
        return itemRepository.findByPostedByEmail(principal.getName());
    }

    // ✅ 2. GET SINGLE ITEM FOR EDIT (EditItem page)
    @GetMapping("/{id}")
    public Item getMyItemById(
            @PathVariable Long id,
            Principal principal
    ) {
        User user = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Item item = itemRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 🔒 OWNER CHECK
        if (!item.getPostedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        return item;
    }

    // ✅ 3. UPDATE MY ITEM (FULL EDIT – OWNER ONLY)
    @PutMapping("/{id}")
    public Item updateMyItem(
            @PathVariable Long id,
            @RequestBody Item updatedItem,
            Principal principal
    ) {
        User user = userRepository
                .findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Item existingItem = itemRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 🔒 OWNER CHECK
        if (!existingItem.getPostedBy().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized action");
        }

        // ✅ ALLOWED FIELDS ONLY
        existingItem.setTitle(updatedItem.getTitle());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setLocation(updatedItem.getLocation());
        existingItem.setImageUrl(updatedItem.getImageUrl());

        return itemRepository.save(existingItem);
    }
}
