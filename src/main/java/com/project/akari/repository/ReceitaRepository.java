package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByEmpresaId(Long empresaId);
}
