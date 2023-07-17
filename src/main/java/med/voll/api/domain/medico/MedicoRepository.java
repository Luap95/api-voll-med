package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryRewriter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable pageable);

    @Query(value="SELECT m.* FROM medicos m LEFT JOIN agendamentos a ON m.id = a.medico_id " +
            "AND a.horario = :horarioDesejado WHERE a.id IS NULL AND m.ativo = true", nativeQuery = true)
    List<Medico> findMedicosSemConsultaAgendada(@Param("horarioDesejado") LocalDateTime horarioDesejado);

}
