package com.project.akari.planejamento;

import jakarta.persistence.GenerationType;

import java.util.List;

import com.project.akari.empresa.Empresa;
import com.project.akari.servico.Servico;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class PlanejamentoServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planejamento_id", nullable = false)
    private Planejamento planejamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @OneToMany(mappedBy = "planejamentoServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanejamentoReceita> receitas;

    @Column(nullable = false)
    private Integer quantitativo;

}
