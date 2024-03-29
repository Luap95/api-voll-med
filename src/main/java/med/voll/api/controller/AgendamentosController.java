package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.agendamento.*;
import med.voll.api.domain.agendamento.validacao.AgendamentoService;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Medico medico = null;
        if(dados.medicoId() == null){
            try{
                medico = service.selecionaMedicoDiponivelAleatoriamente(
                        LocalDateTime.parse(dados.horarioConsulta()));
                System.out.println(medico.getNome());
            }catch (Exception e){
                return ResponseEntity.badRequest().body("Não há médicos diponíveis para o horário selecionado");
            }

        }else{
             medico = medicoRepository.getReferenceById(dados.medicoId());
        }
        
        Paciente paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        Agendamento agendamento = new Agendamento(paciente, medico, LocalDateTime.parse(dados.horarioConsulta()));

        List<ResponseEntity> responses = service.validaAgendamento(agendamento);
        ResponseEntity response = null;

        int bad = 0;
        String mensagemErro ="";

        for(ResponseEntity responseEntity : responses){
            if(responseEntity.getStatusCode().value()==200){

            }else if(responseEntity.getStatusCode().value()==400) {
                bad++;
                mensagemErro = mensagemErro + "\n" + responseEntity.getBody();
            }
        }
        System.out.println(bad);
        if(bad==0){
            this.agendamentoRepository.save(agendamento);

            var uri = uriBuilder.path("agendamentos/{id}").buildAndExpand(agendamento.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoAgendamento(agendamento));
        }else {
            return ResponseEntity.badRequest().body(mensagemErro);
        }

    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoAgendamento dados){
        var agendamento = agendamentoRepository.getReferenceById(dados.agendamentoId());
        ResponseEntity response = service.validaHorarioAntecedenciaCancelamento(agendamento);
        if(response.getStatusCode().value()==200){
            agendamento.cancelar(dados.motivoCancelamento());
            return response;
        }else{
            return response;
        }


    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
//        String mensagemErro = "Paciente já possui uma consulta agendada para a data cadastrada";
//        return ResponseEntity.badRequest().body(mensagemErro);
//    }
}
