package com.web.akari.repository;

import com.web.akari.model.Categoria;
import com.web.akari.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByUserId(Long userId);

    Optional<Categoria> findByNomeIgnoreCaseAndUser(String nome, User user);
}