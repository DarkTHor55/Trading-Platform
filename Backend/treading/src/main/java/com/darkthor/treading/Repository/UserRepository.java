package com.darkthor.treading.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.darkthor.treading.Models.User;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);


    User findByEmail(String username);
}
