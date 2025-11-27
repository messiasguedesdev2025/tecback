package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // NOVO: Chave Estrangeira Genérica (O ID do Filme ou da Série)
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    // NOVO: Coluna Identificadora do Tipo de Item (Ex: "FILME" ou "SERIE")
    @Column(name = "item_type", nullable = false, length = 10)
    private String itemType;
}