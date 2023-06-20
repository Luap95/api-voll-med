package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PacienteAtivoValidator.class)
public @interface PacienteAtivo {
    String message() default "Cadastro do paciente está inativo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
