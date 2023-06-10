package med.voll.api.domain.agendamento;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Service
public class AgendamentoService {
    public void validaAgendamento(Agendamento agendamento){
        validaHorarioDeAtendimento(agendamento);
    }

    private void validaHorarioDeAtendimento(Agendamento agendamento){
        //verifica se a data agendada está dentro do dia da semana de atendimento
        if(agendamento.getHorario().getDayOfWeek() != DayOfWeek.SUNDAY){
            //Verifica se o horário está dentro do funcionamento
            if(agendamento.getHorario().getHour()>6 && agendamento.getHorario().getHour()<23){
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
}
