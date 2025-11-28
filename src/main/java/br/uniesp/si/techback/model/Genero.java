package br.uniesp.si.techback.model;

import br.uniesp.si.techback.validation.GeneroProibido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "generos")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @GeneroProibido

    private String nome;

    @Column(length = 200)
    private String descricao;
}