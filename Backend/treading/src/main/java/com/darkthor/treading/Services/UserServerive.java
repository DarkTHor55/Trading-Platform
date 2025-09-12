package com.darkthor.treading.Services;

import com.darkthor.treading.Configuration.JwtProvider;
import com.darkthor.treading.Exception.UserException;
import com.darkthor.treading.Request.LoginRequest;
import com.darkthor.treading.Response.AuthResponse;
import com.darkthor.treading.Response.LoginResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darkthor.treading.Models.User;
import com.darkthor.treading.Repository.UserRepository;
@Service
public class UserServerive {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public AuthResponse createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException("User already exists with email: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return AuthResponse.builder()
                .email(user.getEmail())
                .isTwoFactorEnabled(false)
                .message("Register Successfully")
                .session("")
                .status(true)
                .build();
    }

    public LoginResponse loginUser(LoginRequest user) {
        User u = userRepository.findByEmail(user.getEmail());

        if (u == null) {
            throw new UserException("User not found with email: " + user.getEmail());
        }

        if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            throw new UserException("Invalid password");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
        if (userDetails == null) {
            throw new UserException("Invalid username");
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        String jwt = jwtProvider.genrateToken(authentication);

        return LoginResponse.builder()
                .email(user.getEmail())
                .isTwoFactorEnabled(false)
                .message("Login Successfully")
                .jwt(jwt)
                .session("")
                .status(true)
                .build();
    }
}
