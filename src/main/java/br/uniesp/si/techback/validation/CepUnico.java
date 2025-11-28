package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Anotação para validar se um CEP já existe no banco de dados.
 * Garante que cada endereço tenha um CEP único.
 */
@Documented
@Constraint(validatedBy = CepUnicoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CepUnico {
    String message() default "Este CEP já está cadastrado";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
