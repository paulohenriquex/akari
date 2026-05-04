package com.web.akari.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.akari.model.Planejamento;

public interface PlanejamentoRepository extends JpaRepository<Planejamento, Long> {
    List<Planejamento> findByDataBetween(LocalDate inicio, LocalDate fim);
}
