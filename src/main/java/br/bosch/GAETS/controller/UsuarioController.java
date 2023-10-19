package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        var senhaCriptografada = bCryptPasswordEncoder.encode(dadosCadastroUsuario.senha());
        var usuario = new Usuario(dadosCadastroUsuario);
        usuario.setSenha(senhaCriptografada);
        repository.save(usuario);

        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosRetornoUsuario(usuario));
    }

    @GetMapping("/{edv}")
    public ResponseEntity detalhar(@PathVariable String edv) {
        var usuario = repository.getByEdv(edv);
        return ResponseEntity.ok(new DadosRetornoUsuario(usuario));
    }

    @GetMapping("/turmas")
    public ResponseEntity<Page<DadosTurma>> listarTodasTurmas(Pageable pageable) {
        var page = repository.findAllTurma(pageable).map(DadosTurma::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/turmas/{turma}")
    public ResponseEntity<Page<DadosRetornoUsuario>> listarUsuariosPorTurma(@PathVariable int turma, @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
        var page = repository.findAllByTurma(pageable, turma).map(DadosRetornoUsuario::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{edv}")
    @Transactional
    public ResponseEntity desativar(@PathVariable String edv) {
        var usuario = repository.getByEdv(edv);
        usuario.desativar();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/excluir/{edv}")
    @Transactional
    public ResponseEntity excluir(@PathVariable String edv) {
        repository.deleteByEdv(edv);
        return ResponseEntity.noContent().build();
    }
}