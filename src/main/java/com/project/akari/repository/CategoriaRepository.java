package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEmpresaId(Long empresaId);
}
