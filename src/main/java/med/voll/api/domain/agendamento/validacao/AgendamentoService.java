package med.voll.api.domain.agendamento.validacao;

import med.voll.api.domain.agendamento.Agendamento;
import med.voll.api.domain.agendamento.AgendamentoRepository;
import med.voll.api.domain.agendamento.DadosAgendamentoCancelado;
import med.voll.api.domain.agendamento.DadosDetalhamentoAgendamento;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AgendamentoService {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<ResponseEntity> validaAgendamento(Agendamento agendamento){
        List<ResponseEntity> responseEntityList = new ArrayList<>();
        responseEntityList.add(validaNumeroConsultaPaciente(agendamento));
        responseEntityList.add(validaConsultaMedico(agendamento));
        return responseEntityList;
    }


    public ResponseEntity validaConsultaMedico(Agendamento agendamento){
        Medico medico = agendamento.getMedico();
        ResponseEntity response = null;
        List<Agendamento> consulta = agendamentoRepository.findByMedicoAndHorarioBetween(agendamento.getMedico(),
                agendamento.getHorario().minusMinutes(59), agendamento.getHorario().plusMinutes(59));
        if(consulta.isEmpty()){
            response = ResponseEntity.ok().build();
        }else {
            String mensagemErro = "O médico já possui uma consulta em andamento no horário";
            response = ResponseEntity.badRequest().body(mensagemErro);
        }
        return response;
    }
    private ResponseEntity validaNumeroConsultaPaciente(Agendamento agendamento){
        var paciente = pacienteRepository.findById(agendamento.getPaciente().getId());
        ResponseEntity response = null;
        List<Agendamento> consulta = agendamentoRepository.findByHorarioBetweenAndPaciente(
                getHorarioInicial(agendamento), getHorarioFinal(agendamento),paciente);
        if(consulta.isEmpty()){
            response = ResponseEntity.ok().build();
        }else {
            String mensagemErro = "Paciente já possui uma consulta agendada para a data cadastrada";
            response = ResponseEntity.badRequest().body(mensagemErro);
        }

        return response;
    }

    private static LocalDateTime getHorarioFinal(Agendamento agendamento) {
        return LocalDateTime.of(
                agendamento.getHorario().getYear(),
                agendamento.getHorario().getMonth(),
                agendamento.getHorario().getDayOfMonth(),
                23, 59, 59);
    }

    private static LocalDateTime getHorarioInicial(Agendamento agendamento) {
        return LocalDateTime.of(agendamento.getHorario().getYear(),
                agendamento.getHorario().getMonth(),
                agendamento.getHorario().getDayOfMonth(),
                0, 0, 0);
    }

    public Medico selecionaMedicoDiponivelAleatoriamente(LocalDateTime horario) {
        List<Medico> medicos = medicoRepository.findMedicosSemConsultaAgendada(horario);
        int i = new Random().nextInt(medicos.size());
        return medicos.get(i);
    }

    public ResponseEntity validaHorarioAntecedenciaCancelamento(Agendamento agendamento){
        if(LocalDateTime.now().isBefore(agendamento.getHorario().minusHours(24))){
            return ResponseEntity.ok().body(new DadosAgendamentoCancelado(agendamento));
        }
        return ResponseEntity.badRequest().body("Consulta só pode ser cancelada com 24 horas " +
                "de antecedência");
    }
}
