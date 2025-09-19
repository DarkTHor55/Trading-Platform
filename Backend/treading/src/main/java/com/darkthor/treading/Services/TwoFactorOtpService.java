package com.darkthor.treading.Services;

import com.darkthor.treading.Models.TwoFactorOTP;
import com.darkthor.treading.Models.User;

public interface TwoFactorOtpService {

    public TwoFactorOTP createTwoFactorOtp(User user, String otp , String jwt);
    public TwoFactorOTP findByUser(Long userid);
    public TwoFactorOTP findById(String id);
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP,String otp);
    public void deleteOtp(TwoFactorOTP twoFactorOTP);

}
