package com.web.akari.repository;

import com.web.akari.model.Planejamento;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanejamentoRepository
    extends JpaRepository<Planejamento, Long>
{
    List<Planejamento> findByDataBetween(LocalDate inicio, LocalDate fim);

    List<Planejamento> findByUsuarioId(Long userId);
}