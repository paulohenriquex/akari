package com.project.akari.planejamento;

import com.project.akari.empresa.Empresa;
import com.project.akari.receita.Receita;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PlanejamentoReceita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planejamento_servico_id", nullable = false)
    private PlanejamentoServico planejamentoServico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id", nullable = false)
    private Receita receita;
}
