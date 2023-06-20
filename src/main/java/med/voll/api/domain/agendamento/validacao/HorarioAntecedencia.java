package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HorarioAntecedenciaValidator.class)
public @interface HorarioAntecedencia {
    String message() default "Antecedência do horário de agendamento deve ser no máximo de 30 minutos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}