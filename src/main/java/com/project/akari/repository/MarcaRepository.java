package com.project.akari.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
        List<Marca> findByEmpresaId(Long empresaId);
}
