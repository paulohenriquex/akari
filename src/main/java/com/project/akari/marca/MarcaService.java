package com.project.akari.marca;

import org.springframework.stereotype.Service;

import com.project.akari.empresa.Empresa;
import com.project.akari.empresa.EmpresaRepository;

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
