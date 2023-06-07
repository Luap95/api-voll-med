package med.voll.api.domain.agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosCadastroAgendamento(
        @NotNull
        Paciente paciente,
        @NotNull
        Medico medico,
        @NotBlank
        LocalDateTime horarioConsulta) {

}
