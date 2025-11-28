package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;



public class TitulosBloqueadosValidator implements ConstraintValidator<TitulosBloqueados, String> {

    // Lista de palavras que não são permitidas nos títulos de filmes
    // Esta lista pode ser facilmente estendida conforme necessário
    private static final List<String> PALAVRAS_BLOQUEADAS = Arrays.asList(
        "sexo", "drogas", "violência", "pornô", "xxx", "nazista", "racista"
    );

    @Override
    public boolean isValid(String titulo, ConstraintValidatorContext context) {
        // Se o título for nulo ou vazio, consideramos válido
        // A validação de @NotBlank deve ser feita separadamente
        if (titulo == null || titulo.isEmpty()) {
            return true;
        }

        // Converte o título para minúsculas para fazer uma comparação case-insensitive
        String tituloLowerCase = titulo.toLowerCase();
        
        // Verifica se NENHUMA das palavras bloqueadas está contida no título
        return PALAVRAS_BLOQUEADAS.stream()
                .noneMatch(palavra -> tituloLowerCase.contains(palavra.toLowerCase()));
    }
}
