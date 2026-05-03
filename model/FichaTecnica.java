package com.web.akari.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FichaTecnica {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "fichaTecnica")
  private List<Ingrediente> ingredientes;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "receita_id")
  private Receita receita;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}
