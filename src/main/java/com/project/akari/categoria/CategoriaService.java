package com.project.akari.categoria;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.project.akari.empresa.Empresa;
import com.project.akari.empresa.EmpresaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final EmpresaRepository empresaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, EmpresaRepository empresaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public CategoriaResponse criar(CategoriaRequest dto) {
        Empresa empresa = empresaRepository.findById(dto.empresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada. ID: " + dto.empresaId()));

        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(dto.nome());
        novaCategoria.setEmpresa(empresa);

        Categoria salva = categoriaRepository.save(novaCategoria);
        return new CategoriaResponse(salva.getId(), salva.getNome(), salva.getEmpresa().getId());
    }

    public List<CategoriaResponse> listarPorEmpresa(Long empresaId) {
        List<Categoria> categorias = categoriaRepository.findByEmpresaId(empresaId);
        List<CategoriaResponse> lista = new ArrayList<>();

        for (Categoria c : categorias) {
            CategoriaResponse cr = new CategoriaResponse(c.getId(), c.getNome(), c.getEmpresa().getId());
            lista.add(cr);
        }
        return lista;
    }

    public CategoriaResponse buscarPorId(@NonNull Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada. ID: " + id));
        return new CategoriaResponse(categoria.getId(), categoria.getNome(),
                categoria.getEmpresa().getId());
    }
}
