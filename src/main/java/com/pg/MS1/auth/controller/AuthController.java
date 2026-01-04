package com.pg.MS1.auth.controller;

import com.pg.MS1.auth.dto.LoginRequest;
import com.pg.MS1.auth.dto.LoginResponse;
import com.pg.MS1.auth.service.AuthService;
import com.pg.MS1.common.enums.Role;
import com.pg.MS1.user.dto.CreateUserRequest;
import com.pg.MS1.user.entity.User;
import com.pg.MS1.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService authService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/bootstrap/admin")
    public ResponseEntity<?> bootStrapAdmin (@RequestBody CreateUserRequest request) {
        if (userRepository.existsByRole(Role.ADMIN)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Admin already exists");
        }

        User admin = new User();

        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setName(request.getName());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        return ResponseEntity.ok("Admin was created");
    }
}
