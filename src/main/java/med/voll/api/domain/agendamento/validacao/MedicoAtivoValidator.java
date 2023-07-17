package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoAtivoValidator implements ConstraintValidator<MedicoAtivo, Long> {
    @Autowired
    private MedicoRepository repository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context){
        if(id == null){
            return true;
        }
        var medico = repository.findById(id);
        if (medico.get().getAtivo()){
            return true;
        }else {
            return false;
        }
    }
}
