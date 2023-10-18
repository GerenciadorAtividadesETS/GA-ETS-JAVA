package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.usuario.DadosCadastroUsuario;
import br.bosch.GAETS.model.usuario.DadosRetornoUsuario;
import br.bosch.GAETS.model.usuario.Usuario;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
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

    @GetMapping("/turmas/{turma}")
    public ResponseEntity<Page<DadosRetornoUsuario>> listarPorTurma(@PathVariable int turma, @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable) {
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
}