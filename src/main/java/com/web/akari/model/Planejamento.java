package com.web.akari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "planejamentos")
public class Planejamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_servico")
    private LocalDate data;

    @Column(name = "quantidade_pessoas")
    private Integer quantidadePessoas;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToMany
    @JoinTable(
            name = "planejamento_fichas_tecnicas",
            joinColumns = @JoinColumn(name = "planejamento_id"),
            inverseJoinColumns = @JoinColumn(name = "ficha_tecnica_id")
    )
    private Set<FichaTecnica> fichasTecnicas = new HashSet<>();
}