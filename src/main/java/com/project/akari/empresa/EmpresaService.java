package com.project.akari.empresa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public EmpresaResponse criar(EmpresaRequest dto) {
        Empresa empresa = new Empresa();
        empresa.setNome(dto.nome());
        Empresa salva = empresaRepository.save(empresa);
        return new EmpresaResponse(salva.getId(), salva.getNome());
    }

    public List<EmpresaResponse> listarTodas() {
        List<Empresa> empresas = empresaRepository.findAll();
        List<EmpresaResponse> lista = new ArrayList<>();

        for (Empresa e : empresas) {
            EmpresaResponse dto = new EmpresaResponse(e.getId(), e.getNome());
            lista.add(dto);
        }
        return lista;
    }

    public EmpresaResponse buscarPorId(@NonNull Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada. ID: " + id));
        return new EmpresaResponse(empresa.getId(), empresa.getNome());
    }
}
