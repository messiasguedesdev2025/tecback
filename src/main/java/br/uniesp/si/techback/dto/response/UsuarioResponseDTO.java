package br.uniesp.si.techback.dto.response;

import br.uniesp.si.techback.model.Endereco;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    // Senha (senha) é omitida!

    // O Endereco pode ser simplificado (EnderecoDTO) ou retornado aqui
    private Endereco endereco;
}