package com.web.akari.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.akari.dto.MarcaRequestDTO;
import com.web.akari.dto.MarcaResponseDTO;
import com.web.akari.service.MarcaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @PostMapping
    public ResponseEntity<MarcaResponseDTO> criar(@RequestBody MarcaRequestDTO dto) {
        return new ResponseEntity<>(marcaService.criar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> listarPorUsuario(@RequestParam Long userId) {
        return ResponseEntity.ok(marcaService.listarPorUsuario(userId));
    }
}
