package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "favorito")
@IdClass(FavoritosId.class)
public class Favoritos {

    @Id
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Id
    @Column(name = "conteudo_id", nullable = false)
    private Long conteudoId;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime criadoEm;
}
