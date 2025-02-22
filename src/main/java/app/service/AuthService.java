package app.service;

import app.dto.ApiResponse;
import app.dto.LoginRequest;
import app.dto.LoginResponse;
import app.model.User;
import app.repository.UserRepository;
import app.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                      JwtTokenProvider jwtTokenProvider,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        LoginResponse loginResponse = new LoginResponse(jwt);
        
        return ApiResponse.success("Login successful", loginResponse);
    }

    public ApiResponse<User> registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return ApiResponse.error("Username is already taken!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ApiResponse.error("Email is already in use!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ApiResponse.success("User registered successfully", savedUser, HttpStatus.CREATED);
    }
}
