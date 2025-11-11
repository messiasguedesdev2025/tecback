package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genero")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    @PalavrasBloqueadas
    private String nome;

    @Column(length = 255)
    private String descricao;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false, columnDefinition = "TIMESTAMP(3)")
    private OffsetDateTime atualizadoEm;
}
