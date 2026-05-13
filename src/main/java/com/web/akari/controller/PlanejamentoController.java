package com.web.akari.controller;

import com.web.akari.dto.PlanejamentoRequestDTO;
import com.web.akari.dto.PlanejamentoResponseDTO;
import com.web.akari.service.PlanejamentoService;
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
@RequestMapping("/api/planejamentos")
@RequiredArgsConstructor
public class PlanejamentoController {

    private final PlanejamentoService planejamentoService;

    @PostMapping
    public ResponseEntity<PlanejamentoResponseDTO> criar(
        @RequestBody PlanejamentoRequestDTO dto
    ) {
        return new ResponseEntity<>(
            planejamentoService.criar(dto),
            HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<PlanejamentoResponseDTO>> listarPorUsuario(
        @RequestParam Long userId
    ) {
        return ResponseEntity.ok(planejamentoService.listarPorUsuario(userId));
    }

}
