package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConsultaLimitadaValidator.class)
public @interface ConsultaLimitada {
    String message() default "Paciente já possui uma consulta agendada na data cadastrada";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

