package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.atividade.DadosCadastroAtividade;
import br.bosch.GAETS.model.atividade.DadosRetornoAtividade;
import br.bosch.GAETS.model.service.atividade.CadastrarAtividade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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


    // ENDPOINTS
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarAtividade(@RequestBody @Valid DadosCadastroAtividade dadosCadastroAtividade,
                                    Authentication authentication) {
        var edv = authentication.getName();
        var atividade = cadastrarAtividade.cadastrar(dadosCadastroAtividade, edv);

        return ResponseEntity.ok(atividade);
    }


    @GetMapping("/{id}")
    public ResponseEntity detalharAtividade(@PathVariable int id) {
        var atividade = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosRetornoAtividade(atividade));
    }


    @GetMapping("/turmas/{idTurma}")
    public ResponseEntity listarAtividadesPorTurma(@PathVariable int idTurma, Pageable pageable) {
        var page = repository.findAllByTurma(pageable, idTurma).map(DadosRetornoAtividade::new);
        return ResponseEntity.ok(page);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirAtividade(@PathVariable int id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}