package com.web.akari.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.akari.dto.EnderecoDTO;
import com.web.akari.dto.FornecedorRequestDTO;
import com.web.akari.dto.FornecedorResponseDTO;
import com.web.akari.model.Endereco;
import com.web.akari.model.Fornecedor;
import com.web.akari.model.User;
import com.web.akari.repository.FornecedorRepository;
import com.web.akari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final UserRepository userRepository;

    @Transactional
    public FornecedorResponseDTO criar(FornecedorRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.nome());
        fornecedor.setCnpj(dto.cnpj());
        fornecedor.setTelefone(dto.telefone());
        fornecedor.setEmail(dto.email());
        fornecedor.setUser(user);

        Endereco endereco = new Endereco();
        endereco.setCep(dto.endereco().cep());
        endereco.setLogradouro(dto.endereco().logradouro());
        endereco.setNumero(dto.endereco().numero());
        endereco.setComplemento(dto.endereco().complemento());
        endereco.setBairro(dto.endereco().bairro());
        endereco.setCidade(dto.endereco().cidade());
        endereco.setEstado(dto.endereco().estado());

        fornecedor.setEndereco(endereco);

        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        return converterParaResponseDTO(salvo);
    }

    @Transactional(readOnly = true)
    public List<FornecedorResponseDTO> listarPorUsuario(Long userId) {
        return fornecedorRepository.findByUserId(userId).stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    private FornecedorResponseDTO converterParaResponseDTO(Fornecedor fornecedor) {
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                fornecedor.getEndereco().getCep(),
                fornecedor.getEndereco().getLogradouro(),
                fornecedor.getEndereco().getNumero(),
                fornecedor.getEndereco().getComplemento(),
                fornecedor.getEndereco().getBairro(),
                fornecedor.getEndereco().getCidade(),
                fornecedor.getEndereco().getEstado());

        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getCnpj(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                enderecoDTO);
    }
}