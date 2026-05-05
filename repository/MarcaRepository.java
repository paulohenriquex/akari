package com.web.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.akari.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    List<Marca> findByUserId(Long userId);
}
