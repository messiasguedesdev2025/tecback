package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * Implementação do validador para a anotação @GeneroProibido.
 * Este validador verifica se o gênero fornecido está na lista de gêneros bloqueados.
 * A verificação é case-insensitive.
 */
public class GeneroProibidoValidator implements ConstraintValidator<GeneroProibido, String> {

    // Lista de gêneros que não são permitidos.
    private static final List<String> GENEROS_BLOQUEADOS = Arrays.asList(
        "Adulto", "Hentai", "Conteúdo Explícito"
    );

    @Override
    public boolean isValid(String genero, ConstraintValidatorContext context) {
        if (genero == null || genero.trim().isEmpty()) {
            // Consideramos válido se for nulo ou vazio, para ser tratado por @NotBlank
            return true;
        }

        // Verifica se o gênero fornecido, em minúsculas, corresponde a algum da lista de bloqueados
        return GENEROS_BLOQUEADOS.stream()
                .noneMatch(bloqueado -> bloqueado.equalsIgnoreCase(genero.trim()));
    }
}
