package br.uniesp.si.techback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritosId implements Serializable {
    private Long usuarioId;
    private Long conteudoId;
}
