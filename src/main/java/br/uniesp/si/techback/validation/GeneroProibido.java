package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = GeneroProibidoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface GeneroProibido {
    String message() default "O gênero informado não é permitido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
