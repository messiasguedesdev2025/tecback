package br.uniesp.si.techback.model;

import br.uniesp.si.techback.validation.TitulosBloqueados;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "filmes")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @TitulosBloqueados
    private String titulo;

    @Column(length = 500)
    private String descricao;

    private int duracaoMinutos;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

    @Column(nullable = false)
    private int anoLancamento;

    @Column(nullable = false)
    private int classificacaoIndicativa;

    @Column(nullable = false)
    private boolean disponivel;

    @Column(nullable = false)
    private int estoque;

}
