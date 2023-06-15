package med.voll.api.domain.agendamento;

import jakarta.annotation.PostConstruct;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoService {

    public void validaAgendamento(Agendamento agendamento, AgendamentoRepository repository){
        //validaHorarioDeAtendimento(agendamento);

        validaMedico(agendamento, repository);
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


    public void validaMedico(Agendamento agendamento, AgendamentoRepository repository){

        //verifica se o médico tem cadastro  ativo
        if(agendamento.getMedico().getAtivo()){

            Agendamento agenda = repository.findByHorarioAndMedico(agendamento.getHorario(),
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
}
