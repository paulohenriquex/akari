package com.web.akari.repository;

import com.web.akari.model.Produto;
import com.web.akari.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByUserId(Long userId);

    Optional<Produto> findByNomeIgnoreCaseAndUser(String nome, User user);
}
