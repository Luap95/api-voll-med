package med.voll.api.domain.agendamento.validacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import med.voll.api.domain.agendamento.AgendamentoRepository;
import med.voll.api.domain.agendamento.DadosCadastroAgendamento;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ConsultaLimitadaValidator implements ConstraintValidator<ConsultaLimitada, DadosCadastroAgendamento> {

    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaLimitadaValidator(AgendamentoRepository agendamentoRepository, PacienteRepository pacienteRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public boolean isValid(DadosCadastroAgendamento agendamento, ConstraintValidatorContext context){
        var paciente = pacienteRepository.findById(agendamento.pacienteId());

        var consulta = agendamentoRepository.findByHorarioAndPaciente(
                LocalDateTime.parse(agendamento.horarioConsulta()), paciente);
        if(consulta == null){

        }else if(consulta.getHorario().getDayOfMonth() ==
                LocalDateTime.parse(agendamento.horarioConsulta()).getDayOfMonth()){
            return false;
        }

        return true;
    }
}
