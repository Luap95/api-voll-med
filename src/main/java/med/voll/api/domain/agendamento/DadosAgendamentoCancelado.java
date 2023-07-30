package med.voll.api.domain.agendamento;

import java.util.Optional;

public record DadosAgendamentoCancelado(
        Long agendamentoId,
        MotivoCancelamento motivoCancelamento
) {
    public DadosAgendamentoCancelado(Agendamento agendamento){
        this(agendamento.getId(), agendamento.getMotivoCancelamento());
    }
}

