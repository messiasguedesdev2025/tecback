package br.uniesp.si.techback.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;



@Documented
@Constraint(validatedBy = TitulosBloqueadosValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TitulosBloqueados {
    String message() default "O título contém palavras não permitidas";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
