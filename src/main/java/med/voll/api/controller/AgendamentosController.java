package med.voll.api.controller;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import med.voll.api.domain.agendamento.*;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("agendamentos")
public class AgendamentosController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private AgendamentoService service;

    
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroAgendamento dados,
                                    UriComponentsBuilder uriBuilder){
        Medico medico = medicoRepository.getReferenceById(dados.medicoId());
        Paciente paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        Agendamento agendamento = new Agendamento(paciente, medico, LocalDateTime.parse(dados.horarioConsulta()));

        ResponseEntity response = service.validaAgendamento(agendamento);
        System.out.println(response);
        if(response.getStatusCode().value()==200){
            this.agendamentoRepository.save(agendamento);

            var uri = uriBuilder.path("agendamentos/{id}").buildAndExpand(agendamento.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoAgendamento(agendamento));
        }else {
            return response;
        }

    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
//        String mensagemErro = "Paciente já possui uma consulta agendada para a data cadastrada";
//        return ResponseEntity.badRequest().body(mensagemErro);
//    }
}
