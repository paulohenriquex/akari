package com.project.akari.service;

import org.springframework.stereotype.Service;

import com.project.akari.dto.request.MarcaRequest;
import com.project.akari.dto.response.MarcaResponse;
import com.project.akari.model.Empresa;
import com.project.akari.model.Marca;
import com.project.akari.repository.EmpresaRepository;
import com.project.akari.repository.MarcaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;
    private final EmpresaRepository empresaRepository;

    public MarcaService(MarcaRepository marcaRepository, EmpresaRepository empresaRepository) {
        this.marcaRepository = marcaRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public MarcaResponse criar(MarcaRequest dto) {
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada. ID: " + dto.empresaId()));
        Marca marca = new Marca();
        marca.setNome(dto.nome());
        marca.setEmpresa(empresa);
        Marca salva = marcaRepository.save(marca);
        return new MarcaResponse(salva.getId(), salva.getNome(), marca.getEmpresa().getId());
    }
}
