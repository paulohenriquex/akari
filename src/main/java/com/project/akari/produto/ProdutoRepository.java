package com.project.akari.produto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByEmpresaId(Long empresaId);
}
