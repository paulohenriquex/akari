package com.web.akari.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.akari.dto.FornecedorRequestDTO;
import com.web.akari.dto.FornecedorResponseDTO;
import com.web.akari.service.FornecedorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> criarFornecedor(@RequestBody FornecedorRequestDTO dto) {
        return new ResponseEntity<>(fornecedorService.criar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarPorUsuario(@RequestParam Long userId) {
        return ResponseEntity.ok(fornecedorService.listarPorUsuario(userId));
    }
}