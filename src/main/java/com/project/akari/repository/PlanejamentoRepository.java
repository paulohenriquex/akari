package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Planejamento;

public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
    List<Planejamento> findByEmpresaId(Long empresaId);

}
