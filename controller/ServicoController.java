package com.web.akari.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.akari.dto.ServicoDTO;
import com.web.akari.service.ServicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        return ResponseEntity.ok(servicoService.listarTodos());
    }
}