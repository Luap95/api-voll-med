package med.voll.api.domain.agendamento;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosDetalhamentoAgendamento(Long id, Paciente paciente, Medico medico, 
                                           LocalDateTime horarioConsulta) {
    
    public DadosDetalhamentoAgendamento(Agendamento agendamento){
        this(agendamento.getId(), agendamento.getPaciente(), agendamento.getMedico(),
                agendamento.getHorarioConsulta());
    }
}
