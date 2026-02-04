package com.reclaimit.controller;

import com.reclaimit.dto.LoginRequest;
import com.reclaimit.dto.LoginResponse;
import com.reclaimit.dto.RegisterRequest;
import com.reclaimit.entity.User;
import com.reclaimit.security.JwtUtil;
import com.reclaimit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {

        userService.register(
            request.getName(),
            request.getEmail(),
            request.getPhone(),
            request.getPassword()
        );

        return ResponseEntity.ok("User registered successfully");
    }


    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        User user = userService.login(request.getEmail(), request.getPassword());

        String token = JwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return ResponseEntity.ok(
                new LoginResponse(token, user.getEmail(), user.getRole().name())
        );
    }
}
