package com.web.akari.controller;

import com.web.akari.dto.CategoriaRequestDTO;
import com.web.akari.dto.CategoriaResponseDTO;
import com.web.akari.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(
        @RequestBody CategoriaRequestDTO dto
    ) {
        return new ResponseEntity<>(
            categoriaService.criar(dto),
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarPorUsuario(
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok(categoriaService.listarPorUsuario(userId));
    }
}
