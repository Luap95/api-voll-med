package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PacienteAtivoValidator implements ConstraintValidator<PacienteAtivo, Long> {

    @Autowired
    private PacienteRepository repository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context){
        var paciente = repository.findById(id);
        if(paciente.get().isAtivo()){
            return true;
        }
        else {
            return false;
        }
    }

}
