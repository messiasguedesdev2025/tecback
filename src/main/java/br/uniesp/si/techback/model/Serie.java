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
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @TitulosBloqueados
    private String titulo;

    @Column(length = 500)
    private String descricao;

    private int totalEpisodios;

    private int temporadas;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

}
