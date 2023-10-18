package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.usuario.DadosCadastroUsuario;
import br.bosch.GAETS.model.usuario.DadosRetornoUsuario;
import br.bosch.GAETS.model.usuario.Usuario;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Entrou no cadastrar");
        System.out.println("Entrou no cadastrar");
        System.out.println("Entrou no cadastrar");
        var usuario = new Usuario(dadosCadastroUsuario);
        repository.save(usuario);

        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosRetornoUsuario(usuario));
    }
}