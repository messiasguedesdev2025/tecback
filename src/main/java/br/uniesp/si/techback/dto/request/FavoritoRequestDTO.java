package br.uniesp.si.techback.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoritoRequestDTO {

    @NotNull(message = "O ID do Usuário é obrigatório.")
    @Schema(example = "1", description = "ID do usuário que está adicionando o favorito.")
    private Long usuarioId; // OBRIGATÓRIO: Quem favoritou

    @NotNull(message = "O ID do Item (Filme ou Série) é obrigatório.")
    @Schema(example = "1", description = "ID da Entity referenciada (Filme ou Série).")
    private Long itemId; // CHAVE ESTRANGEIRA GENÉRICA (ID)

    @NotBlank(message = "O tipo do item é obrigatório: 'FILME' ou 'SERIE'.")
    @Schema(example = "FILME", description = "O tipo de recurso. Usado pelo Service para buscar na tabela correta.")
    private String itemType; // IDENTIFICADOR POLIMÓRFICO (TIPO)
}