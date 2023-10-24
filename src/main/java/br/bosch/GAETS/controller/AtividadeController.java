package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.atividade.DadosCadastroAtividade;
import br.bosch.GAETS.model.service.CadastrarAtividade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    @Autowired
    private AtividadeRepository repository;

    @Autowired
    private CadastrarAtividade cadastrarAtividade;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroAtividade dadosCadastroAtividade, Authentication authentication) {
        var edv = authentication.getName();
        var atividade = cadastrarAtividade.cadastrar(dadosCadastroAtividade, edv);

        return ResponseEntity.ok(atividade);
    }
}