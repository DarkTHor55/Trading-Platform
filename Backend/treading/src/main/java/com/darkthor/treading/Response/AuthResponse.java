package com.darkthor.treading.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String email;
    private Boolean status;
    private String message;
    private Boolean isTwoFactorEnabled;
    private String session;




}
