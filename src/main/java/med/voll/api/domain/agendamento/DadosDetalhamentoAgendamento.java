package med.voll.api.domain.agendamento;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosDetalhamentoAgendamento(Long id, String nomePaciente, String nomeMedico,
                                           LocalDateTime horarioConsulta) {
    
    public DadosDetalhamentoAgendamento(Agendamento agendamento){
        this(agendamento.getId(), agendamento.getPaciente().getNome(), agendamento.getMedico().getNome(),
                agendamento.getHorario());
    }
}
