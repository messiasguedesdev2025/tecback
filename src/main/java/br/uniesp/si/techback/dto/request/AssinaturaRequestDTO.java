package br.uniesp.si.techback.dto.request;

import br.uniesp.si.techback.model.Assinatura;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AssinaturaRequestDTO {

    @NotNull(message = "O ID do Usuário é obrigatório.")
    @Schema(example = "1", description = "ID do usuário que está assinando.")
    private Long usuarioId;

    @NotNull(message = "O ID do Plano é obrigatório.")
    @Schema(example = "1", description = "ID do plano escolhido.")
    private Long planoId;


    @NotNull(message = "A data de início é obrigatória.")
    @Schema(example = "2025-11-26T22:00:00", description = "Data de início da vigência (Ex: Hoje).")
    private LocalDateTime dataInicio;


    @NotNull(message = "A data final é obrigatória.")
    @Schema(example = "2025-12-26T22:00:00", description = "Data de término (Ex: Daqui a 1 mês).")
    private LocalDateTime dataFim;


    @NotNull(message = "O status é obrigatório.")
    @Schema(example = "ATIVA", description = "Status inicial da assinatura.")
    private Assinatura.StatusAssinatura status;

}