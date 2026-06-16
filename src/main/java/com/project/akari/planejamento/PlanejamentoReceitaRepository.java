package com.project.akari.planejamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanejamentoReceitaRepository extends JpaRepository<PlanejamentoReceita, Long> {
    List<PlanejamentoReceita> findByEmpresaId(Long empresaId);

    List<PlanejamentoReceita> findByPlanejamentoId(Long planejamentoId);

    List<PlanejamentoReceita> findByReceitaId(Long receitaId);

    List<PlanejamentoReceita> findByServicoId(Long servicoId);
}
