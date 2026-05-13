package com.web.akari.controller;

import com.web.akari.dto.ProdutoRequestDTO;
import com.web.akari.dto.ProdutoResponseDTO;
import com.web.akari.service.ProdutoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping("/criar")
    public ResponseEntity<ProdutoResponseDTO> criar(
        @RequestBody ProdutoRequestDTO dto
    ) {
        return new ResponseEntity<>(
            produtoService.criar(dto),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProdutoResponseDTO>> listarPorUsuario(
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok(produtoService.listarPorUsuario(userId));
    }
}
