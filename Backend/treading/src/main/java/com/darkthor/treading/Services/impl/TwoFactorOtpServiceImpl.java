package com.darkthor.treading.Services.impl;

import com.darkthor.treading.Models.TwoFactorOTP;
import com.darkthor.treading.Models.User;
import com.darkthor.treading.Repository.TwoFactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {
    @Autowired
    TwoFactorRepository twoFactorRepository;
    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();

        TwoFactorOTP factorOTP=new TwoFactorOTP();
        factorOTP.setId(id);
        factorOTP.setOtp(otp);
        factorOTP.setJwt(jwt);
        factorOTP.setUser(user);
        return twoFactorRepository.save(factorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userid) {
        return twoFactorRepository.findByUserId(userid);
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP>otp=twoFactorRepository.findById(id);
        return otp.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorRepository.delete(twoFactorOTP
        );
    }
}
