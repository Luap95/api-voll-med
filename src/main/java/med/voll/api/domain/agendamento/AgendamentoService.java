package med.voll.api.domain.agendamento;

import jakarta.annotation.PostConstruct;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    public ResponseEntity validaAgendamento(Agendamento agendamento){
        return validaNumeroConsulta(agendamento);
    }

    private void validaHorarioDeAtendimento(Agendamento agendamento){
        //verifica se a data agendada está dentro do dia da semana de atendimento
        if(agendamento.getHorario().getDayOfWeek() != DayOfWeek.SUNDAY){
            //Verifica se o horário está dentro do funcionamento
            if(agendamento.getHorario().getHour()>6 && agendamento.getHorario().getHour()<19){
                //Verifica se o horário de agendamento está sendo feito com 30 minutos de antecedência
                if(LocalDateTime.now().isBefore(agendamento.getHorario().minusMinutes(30))){
                    //Verifica se o horario de atendimento não possui minutos
                    if(agendamento.getHorario().getMinute() == 0){

                    }else {
                        throw new RuntimeException("Só é possível fazer agendamentos no ínicio de horário");
                    }
                }
                else {
                    throw new RuntimeException("Agendamentos só podem ser feitos com no minimo " +
                            "30 minutos de antecedência");
                }

            }
            else {
                throw new RuntimeException("Data de agendamento fora do horário de funcionamento " +
                        "de segunda a sábado, das 07:00 às 19:00");
            }

        }else{
            throw new RuntimeException("Data de agendamento fora do horário de funcionamento " +
                    "de segunda a sábado, das 07:00 às 19:00");
        }
    }


    public void validaMedico(Agendamento agendamento){

        //verifica se o médico tem cadastro  ativo
        if(agendamento.getMedico().getAtivo()){

            Agendamento agenda = agendamentoRepository.findByHorarioAndMedico(agendamento.getHorario(),
                    agendamento.getMedico());
            //verifica se o médico já possui uma consulta na data
            if(agenda == null){
            }
            else {
                throw new RuntimeException("Médico já possui consulta agendada no horário definido");
            }

        }else {
            throw new RuntimeException("Não é possível fazer o agendamento de consulta com um médico inativo");
        }
    }
    private ResponseEntity validaNumeroConsulta(Agendamento agendamento){
        var paciente = pacienteRepository.findById(agendamento.getPaciente().getId());
        ResponseEntity response = null;
        var consulta = agendamentoRepository.findByHorarioAndPaciente(agendamento.getHorario(), paciente);
        if(consulta == null){
            response = ResponseEntity.ok().build();
        }else {
            String mensagemErro = "Paciente já possui uma consulta agendada para a data cadastrada";
            response = ResponseEntity.badRequest().body(mensagemErro);
        }

        return response;
    }
}
