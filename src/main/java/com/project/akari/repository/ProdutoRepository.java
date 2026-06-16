package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByEmpresaId(Long empresaId);
}
