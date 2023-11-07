package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.materia.MateriaRepository;
import br.bosch.GAETS.model.resposta.DadosCadastroResposta;
import br.bosch.GAETS.model.resposta.DadosRetornoResposta;
import br.bosch.GAETS.model.resposta.DadosRetornoRespostaId;
import br.bosch.GAETS.model.resposta.RespostaRepository;
import br.bosch.GAETS.model.service.ValidarUsuarioInstrutor;
import br.bosch.GAETS.model.service.resposta.CadastrarResposta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas")
public class RespostaController {
    // VERIFICAR DEPOIS SE É POSSÍVEL CRIAR MAIS FUNÇÕES DENTRO DE SERVICE PARA TIRAR OS REPOSITÓRIOS DAQUI

    @Autowired
    private RespostaRepository repository;

    @Autowired
    private CadastrarResposta cadastrarResposta;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ValidarUsuarioInstrutor validarUsuarioInstrutor;


    // ENDPOINTS
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarResposta(@RequestBody @Valid DadosCadastroResposta dadosCadastroResposta,
                                            Authentication authentication) {
        var resposta = cadastrarResposta.cadastrar(dadosCadastroResposta, authentication.getName());

        return ResponseEntity.ok(resposta);
    }


    @GetMapping
    public ResponseEntity detalharResposta(@RequestParam(name = "atividade") int idAtividade,
                                           Authentication authentication) {
        String edv = authentication.getName();
        var atividade = atividadeRepository.getReferenceById(idAtividade);
        var resposta = repository.findResposta(edv, atividade);

        return ResponseEntity.ok(new DadosRetornoResposta(resposta));
    }


    // QUANDO O USUÁRIO TEM MAIS DE UMA RESPOSTA CADASTRADA PARA A MESMA ATIVIDADE, DÁ ERRO. PRECISA FAZER UM TRATAMENTO DE ERRO.
    @GetMapping("/materias/{idMateria}")
    public ResponseEntity listarRespostasPorMateria(@PathVariable int idMateria,
                                                    Pageable pageable) {
        var materia = materiaRepository.getReferenceById(idMateria);
        var page = repository.findAllByMateria(pageable, materia).map(DadosRetornoRespostaId::new);

        return ResponseEntity.ok(page);
    }


    @GetMapping("/atividades/{idAtividade}/turmas/{idTurma}")
    public ResponseEntity listarRespostasPorTurma(@PathVariable int idAtividade,
                                                  @PathVariable int idTurma,
                                                  Pageable pageable,
                                                  Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        var atividade = atividadeRepository.getReferenceById(idAtividade);
        var page = repository.findAllByTurma(pageable, atividade, idTurma).map(DadosRetornoResposta::new);

        return ResponseEntity.ok(page);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirResposta(@PathVariable int id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}