package com.web.akari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.akari.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
