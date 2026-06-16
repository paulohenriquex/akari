package com.project.akari.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmpresaId(Long empresaId);

    Optional<Users> findByEmail(String email);
}
