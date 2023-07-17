package med.voll.api.domain.agendamento;


import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Agendamento findByHorarioAndMedico(LocalDateTime horario, Medico medico);
    List<Agendamento> findByHorarioBetweenAndPaciente(LocalDateTime horarioInicial,
            LocalDateTime horarioFinal, Optional<Paciente> paciente);

    Agendamento findByMedico(Medico medico);

    List<Agendamento> findByMedicoAndHorarioBetween(Medico medico, LocalDateTime horario, LocalDateTime localDateTime);
}

