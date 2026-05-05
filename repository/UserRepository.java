package com.web.akari.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.akari.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
