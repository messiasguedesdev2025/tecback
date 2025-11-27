package br.uniesp.si.techback.dto.response;

import lombok.Data;

@Data
public class FavoritoResponseDTO {
    private Long id;
    private Long itemId;
    private String itemType;

    // Simplifica a referência do usuário
    private Long usuarioId;
    private String usuarioEmail; // Opcional: Para saber quem é o dono
}