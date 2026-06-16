package com.project.akari.ingrediente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.produto.Produto;
import com.project.akari.receita.Receita;

public interface IngredienteReceitaRepository extends JpaRepository<IngredienteReceita, Long> {
    List<IngredienteReceita> findByEmpresaId(Long empresaId);

    List<Receita> findByReceitaId(Long receitaId);

    List<Produto> findByProdutoId(Long produtoId);

}
