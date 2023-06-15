package med.voll.api.domain.agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCadastroAgendamento(
        @NotNull
        Long pacienteId,
        Long medicoId,
        @NotBlank
        String horarioConsulta) {

}
