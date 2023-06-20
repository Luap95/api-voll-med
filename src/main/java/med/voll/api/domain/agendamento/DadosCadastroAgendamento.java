package med.voll.api.domain.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.agendamento.validacao.HorarioAntecedencia;
import med.voll.api.domain.agendamento.validacao.HorarioAtendimento;

import java.time.LocalDateTime;

public record DadosCadastroAgendamento(
        @NotNull
        Long pacienteId,
        Long medicoId,
        @NotBlank @HorarioAtendimento @HorarioAntecedencia
        String horarioConsulta) {

}
