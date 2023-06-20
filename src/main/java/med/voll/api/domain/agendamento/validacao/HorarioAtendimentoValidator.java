package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class HorarioAtendimentoValidator implements ConstraintValidator<HorarioAtendimento, String> {
    //private static final LocalDateTime HORARIO_ABERTURA = LocalDateTime.of(null, LocalTime.of(7,0)); // Horário de abertura do estabelecimento
    //private static final LocalDateTime HORARIO_FECHAMENTO = LocalDateTime.of(null, LocalTime.of(19,0)); // Horário de fechamento do estabelecimento

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            LocalDateTime valor = LocalDateTime.parse(value);
            LocalDateTime HORARIO_ABERTURA = LocalDateTime.of(valor.getYear(), valor.getMonth(), valor.getDayOfMonth(), 7, 0);
            LocalDateTime HORARIO_FECHAMENTO = LocalDateTime.of(valor.getYear(), valor.getMonth(), valor.getDayOfMonth(), 19, 0);
            return valor.isAfter(HORARIO_ABERTURA) && valor.isBefore(HORARIO_FECHAMENTO)
                    && (valor.getDayOfWeek() != DayOfWeek.SUNDAY);
        }catch (DateTimeException e){
            return false;
        }

    }
}

