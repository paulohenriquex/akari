package com.web.akari.repository;

import com.web.akari.model.FichaTecnica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaTecnicaRepository
    extends JpaRepository<FichaTecnica, Long>
{
    List<FichaTecnica> findByUserId(Long userId);
}
