package com.project.akari.receita;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByEmpresaId(Long empresaId);
}
