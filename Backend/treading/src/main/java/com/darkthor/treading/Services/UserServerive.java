package com.darkthor.treading.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.darkthor.treading.Models.User;
import com.darkthor.treading.Repository.UserRepository;

@Service
public class UserServerive {
    @Autowired
    private UserRepository userRepository;
    public User createUser(User user) throws Exception{
        if(userRepository.existsByEmail(user.getEmail())){
            throw new Exception("User already exists with this email :"+user.getEmail());
        }
        userRepository.save(user);

        return user;
    }
}
