package br.uniesp.si.techback.validation;

import br.uniesp.si.techback.repository.EnderecoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class CepUnicoValidator implements ConstraintValidator<CepUnico, String> {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public boolean isValid(String cep, ConstraintValidatorContext context) {
        // Se o CEP for nulo ou vazio, outra anotação (@NotBlank) deve cuidar disso.
        if (cep == null || cep.trim().isEmpty()) {
            return true;
        }
        // A validação é bem-sucedida se o CEP *não* existir no repositório.
        return !enderecoRepository.existsByCep(cep);
    }
}
