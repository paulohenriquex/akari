package com.project.akari.servico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByEmpresaId(Long empresaId);

}
