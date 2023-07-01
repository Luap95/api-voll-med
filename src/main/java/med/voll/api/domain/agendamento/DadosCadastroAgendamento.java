package med.voll.api.domain.agendamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.agendamento.validacao.*;
import org.springframework.validation.annotation.Validated;

@Validated
@ConsultaLimitada
public record DadosCadastroAgendamento(
        @NotNull @PacienteAtivo
        Long pacienteId,
        @MedicoAtivo
        Long medicoId,
        @NotBlank @HorarioAtendimento @HorarioAntecedencia
        String horarioConsulta) {

}
