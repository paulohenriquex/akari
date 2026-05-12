package com.web.akari.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(columnNames = { "nome", "user_id" }))
public class Categoria {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   
   private String nome;

   @OneToMany(mappedBy = "categoria")
   private List<Produto> produto;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

}
