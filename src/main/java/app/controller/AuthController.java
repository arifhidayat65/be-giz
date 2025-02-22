package app.controller;

import app.dto.LoginRequest;
import app.dto.LoginResponse;
import app.model.Role;
import app.model.User;
import app.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Default role for new registrations is USER
        user.setRole(Role.ROLE_USER);
        User result = authService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        // Admin registration endpoint (should be secured in production)
        user.setRole(Role.ROLE_ADMIN);
        User result = authService.registerUser(user);
        return ResponseEntity.ok("Admin registered successfully");
    }
}
