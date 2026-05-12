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

import com.web.akari.dto.FichaTecnicaRequestDTO;
import com.web.akari.dto.FichaTecnicaResponseDTO;
import com.web.akari.service.FichaTecnicaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fichas-tecnicas")
@RequiredArgsConstructor
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    @PostMapping
    public ResponseEntity<FichaTecnicaResponseDTO> criar(@RequestBody FichaTecnicaRequestDTO dto) {
        return new ResponseEntity<>(fichaTecnicaService.criar(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FichaTecnicaResponseDTO>> listarPorUsuario(@RequestParam Long userId) {
        return ResponseEntity.ok(fichaTecnicaService.listarPorUsuario(userId));
    }
}
