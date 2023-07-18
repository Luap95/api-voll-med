package med.voll.api.domain.agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoAgendamento(
        @NotNull
        Long agendamentoId,
        @NotBlank
        String motivoCancelamento
) {

}
