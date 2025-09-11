package com.darkthor.treading.Models;

import com.darkthor.treading.Domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled;
    private  VerificationType sendTo;
    
}
