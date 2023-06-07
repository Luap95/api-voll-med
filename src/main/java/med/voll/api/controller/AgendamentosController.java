package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.agendamento.Agendamento;
import med.voll.api.domain.agendamento.DadosCadastroAgendamento;
import med.voll.api.domain.agendamento.AgendamentoRepository;
import med.voll.api.domain.agendamento.DadosDetalhamentoAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("agendamentos")
public class AgendamentosController {

    @Autowired
    private AgendamentoRepository repository;
    
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroAgendamento dados,
                                    UriComponentsBuilder uriBuilder){

        Agendamento agendamento = new Agendamento(dados);
        this.repository.save(agendamento);
        
        var uri = uriBuilder.path("agendamentos/{id}").buildAndExpand(agendamento.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new DadosDetalhamentoAgendamento(agendamento));
    }
}
