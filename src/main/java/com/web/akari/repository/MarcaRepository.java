package com.web.akari.repository;

import com.web.akari.model.Marca;
import com.web.akari.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    List<Marca> findByUserId(Long userId);

    Optional<Marca> findByNomeIgnoreCaseAndUser(String nome, User user);
}
