package com.darkthor.treading.Repository;

import com.darkthor.treading.Models.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorRepository  extends JpaRepository<TwoFactorOTP,String> {
    TwoFactorOTP findByUserId(Long id);

}
