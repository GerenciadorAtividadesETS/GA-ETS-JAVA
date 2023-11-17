package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.materia.MateriaRepository;
import br.bosch.GAETS.model.resposta.*;
import br.bosch.GAETS.model.service.usuario.ValidarUsuarioInstrutor;
import br.bosch.GAETS.model.service.resposta.CadastrarResposta;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository repository;

    @Autowired
    private CadastrarResposta cadastrarResposta;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        try {
            var atividade = atividadeRepository.getReferenceById(idAtividade);
            var resposta = repository.findResposta(authentication.getName(), atividade);
            return ResponseEntity.ok(new DadosRetornoResposta(resposta));
        }

        catch(RuntimeException e) {
            throw new RuntimeException("Resposta não encontrada");
        }
    }


    @GetMapping("/materias/{idMateria}")
    public ResponseEntity listarRespostasPorMateria(@PathVariable int idMateria,
                                                    Pageable pageable,
                                                    Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        try {
            var materia = materiaRepository.getReferenceById(idMateria);
            var page = repository.findAllByMateria(pageable, materia).map(DadosRetornoRespostaId::new);
            return ResponseEntity.ok(page);
        }
        catch(RuntimeException e) {
            throw new RuntimeException("ID Matéria não encontrado");
        }
    }


    @GetMapping("/atividades/{idAtividade}/turmas/{idTurma}")
    public ResponseEntity listarRespostasPorTurma(@PathVariable int idAtividade,
                                                  @PathVariable int idTurma,
                                                  Pageable pageable,
                                                  Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        try {
            var atividade = atividadeRepository.getReferenceById(idAtividade);
            var page = repository.findAllByTurma(pageable, atividade, idTurma).map(DadosRetornoResposta::new);
            return ResponseEntity.ok(page);
        }
        catch(RuntimeException e) {
            throw new RuntimeException("ID Atividade não encontrado");
        }
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirResposta(@PathVariable int id,
                                          Authentication authentication) {

        if (repository.existsById(id)) {
            var resposta = repository.getReferenceById(id);
            var edv = authentication.getName();
            if ((resposta.getUsuario().getEdv()).equals(edv)) {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            throw new RuntimeException("Usuário sem permissão");
        }
        throw new RuntimeException("Resposta não encontrada");
    }
}