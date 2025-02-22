package app.controller;

import app.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> response = authService.login(loginRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        // Default role for new registrations is USER
        user.setRole(Role.ROLE_USER);
        ApiResponse<User> response = authService.registerUser(user);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse<User>> registerAdmin(@RequestBody User user) {
        // Admin registration endpoint (should be secured in production)
        user.setRole(Role.ROLE_ADMIN);
        ApiResponse<User> response = authService.registerUser(user);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
