package com.darkthor.treading.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.darkthor.treading.Models.User;
import com.darkthor.treading.Services.UserServerive;
@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
    @Autowired
    private UserServerive userServerive;

    @PostMapping("/create")
    public ResponseEntity<User>createUser(@RequestBody User user) throws Exception{
        User u=null;
        try {
            u=userServerive.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating user", e);
        }

        return new ResponseEntity<>(u,HttpStatus.CREATED);

    }
    
}
