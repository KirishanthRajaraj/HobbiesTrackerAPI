package com.kiri.hobby_tracker.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiri.hobby_tracker.Model.User;
import com.kiri.hobby_tracker.Service.JwtUtilHelper;
import com.kiri.hobby_tracker.Service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtilHelper jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserService userService, JwtUtilHelper jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public static class RegisterRequest {

        public String username;
        public String email;
        public String password;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User newUser = userService.register(request.username, request.email, request.password);
            return ResponseEntity.ok("User registered with ID: " + newUser.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    public static class LoginRequest {

        public String username;
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        Optional<User> userOptional = userService.findByUsername(request.username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            boolean passwordMatches = passwordEncoder.matches(request.password, user.getPassword());

            if (passwordMatches) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());

                // create HttpOnly cookie
                Cookie cookie = new Cookie("jwt", token);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);

                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(401).body("User not found");
        }
    }
}
