package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.PlanejamentoReceita;

public interface PlanejamentoReceitaRepository extends JpaRepository<PlanejamentoReceita, Long> {
    List<PlanejamentoReceita> findByEmpresaId(Long empresaId);

    List<PlanejamentoReceita> findByPlanejamentoId(Long planejamentoId);

    List<PlanejamentoReceita> findByReceitaId(Long receitaId);

    List<PlanejamentoReceita> findByServicoId(Long servicoId);
}
