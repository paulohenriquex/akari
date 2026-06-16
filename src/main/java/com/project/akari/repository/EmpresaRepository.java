package com.project.akari.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.akari.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
