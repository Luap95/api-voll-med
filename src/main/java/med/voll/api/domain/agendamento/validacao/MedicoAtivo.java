package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MedicoAtivoValidator.class)
public @interface MedicoAtivo {
    String message() default "Cadastro do médico está inativo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
