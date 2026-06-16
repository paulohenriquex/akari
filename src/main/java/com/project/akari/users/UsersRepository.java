package com.project.akari.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByEmpresaId(Long empresaId);

    Optional<Users> findByEmail(String email);
}
