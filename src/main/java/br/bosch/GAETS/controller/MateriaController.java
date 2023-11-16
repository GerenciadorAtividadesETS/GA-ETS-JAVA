package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.materia.*;
import br.bosch.GAETS.model.service.usuario.ValidarUsuarioInstrutor;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidarUsuarioInstrutor validarUsuarioInstrutor;


    // ENDPOINTS
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarMateria(@RequestBody @Valid DadosCadastroMateria dadosCadastroMateria,
                                           UriComponentsBuilder uriComponentsBuilder,
                                           Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        var materia = new Materia(dadosCadastroMateria);
        var uri = uriComponentsBuilder.path("/materias/{id}").buildAndExpand(materia.getId()).toUri();
        repository.save(materia);

        return ResponseEntity.created(uri).body(new DadosRetornoMateria(materia));
    }


    @GetMapping
    public ResponseEntity<Page<DadosRetornoMateria>> listarMateriasTurma(@PageableDefault(size = 10) Pageable pageable,
                                                                         Authentication authentication) {
        var usuario = usuarioRepository.getByEdv(authentication.getName());
        var page = repository.findAllByTurma(pageable, usuario.getTurma());

        return ResponseEntity.ok(page);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity excluirMateria(@RequestParam(name = "nome") String nome,
                                         Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        if (repository.existsByNome(nome)) {
            repository.deleteByNome(nome);
            return ResponseEntity.noContent().build();
        }
        else {
            throw new RuntimeException("Matéria não encontrada");
        }
    }
}