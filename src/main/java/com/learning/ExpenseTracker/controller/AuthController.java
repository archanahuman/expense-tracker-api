package com.learning.ExpenseTracker.controller;

import com.learning.ExpenseTracker.dto.AuthResponse;
import com.learning.ExpenseTracker.dto.LoginRequest;
import com.learning.ExpenseTracker.dto.RegisterRequest;
import com.learning.ExpenseTracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Expense Management",
        description = "APIs for managing authenticated user's expenses"
)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest request) {

        userService.register(request);

        return new ResponseEntity<>(
                "User registered successfully.",
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody @Valid LoginRequest request) {

        return ResponseEntity.ok(
                userService.login(request)
        );
    }
}