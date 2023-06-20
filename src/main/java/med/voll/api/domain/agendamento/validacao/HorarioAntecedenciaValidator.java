package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class HorarioAntecedenciaValidator implements ConstraintValidator<HorarioAntecedencia, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Permite valores nulos, se necessário
        }
        try {
            LocalDateTime valor = LocalDateTime.parse(value);

            return LocalDateTime.now().isBefore(valor.minusMinutes(30));
        }catch (DateTimeException e){
            return false;
        }

    }
}
