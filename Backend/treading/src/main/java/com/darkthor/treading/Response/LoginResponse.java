package com.darkthor.treading.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String jwt;
    private Boolean status;
    private String message;
    private Boolean isTwoFactorEnabled;
    private String session;
}
