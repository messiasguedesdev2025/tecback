package br.uniesp.si.techback.dto.response;

import br.uniesp.si.techback.model.Endereco;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;


    private Endereco endereco;
}