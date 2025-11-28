package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação personalizada para validar se um gênero de filme ou série é permitido.
 * Esta anotação pode ser usada em campos de String para garantir que não correspondam
 * a gêneros em uma lista de bloqueio.
 */
@Documented
@Constraint(validatedBy = GeneroProibidoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneroProibido {
    String message() default "O gênero informado não é permitido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
