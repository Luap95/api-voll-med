package med.voll.api.domain.agendamento;


import med.voll.api.domain.medico.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    public Agendamento findByHorarioAndMedico(LocalDateTime horario, Medico medico);

}

