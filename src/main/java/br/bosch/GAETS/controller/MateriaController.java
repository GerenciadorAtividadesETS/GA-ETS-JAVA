package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.materia.DadosCadastroMateria;
import br.bosch.GAETS.model.materia.DadosRetornoMateria;
import br.bosch.GAETS.model.materia.Materia;
import br.bosch.GAETS.model.materia.MateriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    @Autowired
    private MateriaRepository repository;


    // ENDPOINTS
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarMateria(@RequestBody @Valid DadosCadastroMateria dadosCadastroMateria,
                                           UriComponentsBuilder uriComponentsBuilder) {
        var materia = new Materia(dadosCadastroMateria);
        var uri = uriComponentsBuilder.path("/materias/{id}").buildAndExpand(materia.getId()).toUri();
        repository.save(materia);

        return ResponseEntity.created(uri).body(new DadosRetornoMateria(materia));
    }


    @GetMapping
    public ResponseEntity<Page<DadosRetornoMateria>> listarMaterias(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var page = repository.findAll(pageable).map(DadosRetornoMateria::new);
        return ResponseEntity.ok(page);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity excluirMateria(@RequestParam String nome) {
        repository.deleteByNome(nome);
        return ResponseEntity.noContent().build();
    }
}