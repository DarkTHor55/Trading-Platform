package com.darkthor.treading.Services.impl;

import com.darkthor.treading.Configuration.JwtProvider;
import com.darkthor.treading.Exception.UserException;
import com.darkthor.treading.Models.TwoFactorOTP;
import com.darkthor.treading.Repository.TwoFactorRepository;
import com.darkthor.treading.Request.LoginRequest;
import com.darkthor.treading.Response.AuthResponse;
import com.darkthor.treading.Response.LoginResponse;
import com.darkthor.treading.Services.UserService;
import com.darkthor.treading.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.darkthor.treading.Models.User;
import com.darkthor.treading.Repository.UserRepository;
@Service
public class UserServeriveImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

    @Autowired
    private TwoFactorOtpServiceImpl twoFactorOtpService;

    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    private TwoFactorRepository twoFactorRepository;

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

    public LoginResponse loginUser(LoginRequest user) throws MessagingException {
        User u = userRepository.findByEmail(user.getEmail());

        if (u == null) {
            throw new UserException("User not found with email: " + user.getEmail());
        }

        if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            throw new UserException("Invalid password");
        }

        UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(user.getEmail());
        if (userDetails == null) {
            throw new UserException("Invalid username");
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        String jwt = jwtProvider.genrateToken(authentication);

        if (u.getTwoFactorAuth().isEnabled()){
            AuthResponse ar=new AuthResponse();
            ar.setIsTwoFactorEnabled(true);
            ar.setMessage("Two factor auth enabled");
            String otp= OtpUtils.genrateOtp();

            TwoFactorOTP oldTwoFactorOTP= twoFactorOtpService.findByUser(u.getId());
            if (oldTwoFactorOTP!=null){
                twoFactorOtpService.deleteOtp(oldTwoFactorOTP);
            }
            TwoFactorOTP newTwoFactorOTP= twoFactorOtpService.createTwoFactorOtp(u,otp,jwt);
            emailService.setVerificationOtpEmail(u.getEmail(),otp);
            ar.setSession(newTwoFactorOTP.getId());
        }
        return LoginResponse.builder()
                .email(user.getEmail())
                .isTwoFactorEnabled(false)
                .message("Login Successfully")
                .jwt(jwt)
                .session("")
                .status(true)
                .build();
    }

    public AuthResponse verifyOtp(String otp, String id) throws Exception {
        TwoFactorOTP factorOTP=twoFactorRepository.findById(id).orElse(null);
        if(factorOTP==null){
            throw new Exception("Invalid OTP");
        }
        if(twoFactorOtpService.verifyTwoFactorOtp(factorOTP,otp)){
            return AuthResponse.builder()
                    .message("Two factor method verifed")
                    .isTwoFactorEnabled(true)
                    .jwt(factorOTP.getJwt())
                    .build();
        }
        return null;

    }

    @Override
    public User findUserProfileByJwt(String jwt) {
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }

    @Override
    public User findUserById(Long userId) {
        return null;
    }

    @Override
    public User enableTwoFactorAuthentication(User user) {
        return null;
    }

    @Override
    public User updatePassword(User user) {
        return null;
    }
}
