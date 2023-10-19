package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.atividade.Atividade;
import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.atividade.DadosCadastroAtividade;
import br.bosch.GAETS.model.atividade.DadosRetornoAtividade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    @Autowired
    private AtividadeRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroAtividade dadosCadastroAtividade, UriComponentsBuilder uriComponentsBuilder) {
        var atividade = new Atividade(dadosCadastroAtividade);
        repository.save(atividade);

        var uri = uriComponentsBuilder.path("/atividades/{id}").buildAndExpand(atividade.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosRetornoAtividade(atividade));
    }
}