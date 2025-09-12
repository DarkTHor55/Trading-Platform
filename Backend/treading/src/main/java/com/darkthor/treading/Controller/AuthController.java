package com.darkthor.treading.Controller;

import com.darkthor.treading.Models.User;
import com.darkthor.treading.Request.LoginRequest;
import com.darkthor.treading.Response.AuthResponse;
import com.darkthor.treading.Response.LoginResponse;
import com.darkthor.treading.Services.UserServerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {

    @Autowired
    private UserServerive userServerive;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            AuthResponse response = userServerive.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AuthResponse.builder()
                            .email(null)
                            .status(false)
                            .message(e.getMessage())
                            .isTwoFactorEnabled(false)
                            .session("")
                            .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest user) {
        try {
            LoginResponse response = userServerive.loginUser(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(LoginResponse.builder()
                            .email(null)
                            .jwt(null)
                            .status(false)
                            .message(e.getMessage())
                            .isTwoFactorEnabled(false)
                            .session("")
                            .build());
        }
    }
}
