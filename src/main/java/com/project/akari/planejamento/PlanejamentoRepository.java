package com.project.akari.planejamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
    List<Planejamento> findByEmpresaId(Long empresaId);

}
