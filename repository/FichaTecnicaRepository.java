package com.web.akari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.akari.model.FichaTecnica;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnica, Long> {

}
