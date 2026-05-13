package com.web.akari.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FichaTecnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(
        mappedBy = "fichaTecnica",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Ingrediente> ingredientes;

    @Column(columnDefinition = "TEXT")
    private String modoDePreparo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
