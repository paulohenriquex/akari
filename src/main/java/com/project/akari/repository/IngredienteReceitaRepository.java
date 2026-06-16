package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.IngredienteReceita;
import com.project.akari.model.Produto;
import com.project.akari.model.Receita;

public interface IngredienteReceitaRepository extends JpaRepository<IngredienteReceita, Long> {
    List<IngredienteReceita> findByEmpresaId(Long empresaId);

    List<Receita> findByReceitaId(Long receitaId);

    List<Produto> findByProdutoId(Long produtoId);

}
