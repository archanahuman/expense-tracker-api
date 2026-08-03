package com.learning.ExpenseTracker.service;

import com.learning.ExpenseTracker.dto.AuthResponse;
import com.learning.ExpenseTracker.dto.LoginRequest;
import com.learning.ExpenseTracker.dto.RegisterRequest;
import com.learning.ExpenseTracker.entity.User;
import com.learning.ExpenseTracker.exception.InvalidCredentialsException;
import com.learning.ExpenseTracker.exception.UserAlreadyExistsException;
import com.learning.ExpenseTracker.repository.UserRepository;
import com.learning.ExpenseTracker.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;
@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Register User
    public void register(RegisterRequest request) {

        logger.info("Registering user: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {

            logger.warn("Registration failed. Username '{}' already exists.",
                    request.getUsername());

            throw new UserAlreadyExistsException(
                    "Username already exists.");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");

        userRepository.save(user);

        logger.info("User '{}' registered successfully.",
                request.getUsername());
    }

    // Login User
    public AuthResponse login(LoginRequest request) {

        logger.info("Login attempt for user: {}",
                request.getUsername());

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {

            logger.warn("Invalid login attempt for user '{}'",
                    request.getUsername());

            throw new InvalidCredentialsException(
                    "Invalid username or password.");
        }

        logger.info("User '{}' authenticated successfully.",
                request.getUsername());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(
                new CustomUserDetails(user)
        );

        logger.info("JWT generated successfully for user '{}'.",
                request.getUsername());

        return new AuthResponse(token);
    }
}