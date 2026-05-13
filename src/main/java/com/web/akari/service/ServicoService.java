package com.web.akari.service;

import com.web.akari.dto.ServicoDTO;
import com.web.akari.repository.ServicoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRepository;

    @Transactional(readOnly = true)
    public List<ServicoDTO> listarTodos() {
        return servicoRepository
            .findAll()
            .stream()
            .map(servico -> new ServicoDTO(servico.getId(), servico.getNome()))
            .toList();
    }
}
