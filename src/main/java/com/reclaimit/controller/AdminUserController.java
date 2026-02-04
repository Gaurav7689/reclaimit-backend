package com.reclaimit.controller;

import com.reclaimit.entity.User;
import com.reclaimit.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserRepository repo;

    public AdminUserController(UserRepository repo) {
        this.repo = repo;
    }

    // ✅ GET ALL USERS
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // ✅ BLOCK USER
    @PutMapping("/{id}/block")
    public User blockUser(@PathVariable Long id) {
        User user = repo.findById(id).orElseThrow();
        user.setBlocked(true);
        return repo.save(user);
    }

    // ✅ UNBLOCK USER
    @PutMapping("/{id}/unblock")
    public User unblockUser(@PathVariable Long id) {
        User user = repo.findById(id).orElseThrow();
        user.setBlocked(false);
        return repo.save(user);
    }
}
