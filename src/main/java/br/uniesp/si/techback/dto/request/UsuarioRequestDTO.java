package br.uniesp.si.techback.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String nome;
    private String email;

    @Size(min = 8)
    private String senha;

    // Referencia o Endereco pelo ID
    private Long enderecoId;
}