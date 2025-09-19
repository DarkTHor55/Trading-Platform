package com.darkthor.treading.Services;

import com.darkthor.treading.Models.User;

public interface UserService {
    public User findUserProfileByJwt(String jwt);
    public User findUserByEmail(String email);
    public User findUserById(Long userId);
    public User enableTwoFactorAuthentication(User user);
    public User updatePassword(User user);

}
