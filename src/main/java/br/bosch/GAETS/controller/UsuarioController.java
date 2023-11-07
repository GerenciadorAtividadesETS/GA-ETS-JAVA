package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.service.ValidarUsuarioInstrutor;
import br.bosch.GAETS.model.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ValidarUsuarioInstrutor validarUsuarioInstrutor;


    // ENDPOINTS
    @RequestMapping(value = "/usuarios", method=RequestMethod.POST)
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {
        var senhaCriptografada = bCryptPasswordEncoder.encode(dadosCadastroUsuario.senha());
        var usuario = new Usuario(dadosCadastroUsuario);
        var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        usuario.setSenha(senhaCriptografada);
        repository.save(usuario);

        return ResponseEntity.created(uri).body(new DadosRetornoUsuario(usuario));
    }


    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ResponseEntity detalharUsuario(Authentication authentication) {
        var usuario = repository.getByEdv(authentication.getName());
        return ResponseEntity.ok(new DadosRetornoUsuario(usuario));
    }


    @RequestMapping(value = "/turmas", method=RequestMethod.GET)
    public ResponseEntity<Page<DadosRetornoTurma>> listarTodasTurmas(Pageable pageable,
                                                                     Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        var page = repository.findAllTurma(pageable).map(DadosRetornoTurma::new);
        return ResponseEntity.ok(page);
    }


    @RequestMapping(value = "/turmas/{idTurma}", method=RequestMethod.GET)
    public ResponseEntity<Page<DadosRetornoUsuario>> listarUsuariosPorTurma(@PathVariable int idTurma,
                                                                            @PageableDefault(sort = {"nome"}) Pageable pageable,
                                                                            Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        var page = repository.findAllByTurma(pageable, idTurma).map(DadosRetornoUsuario::new);
        return ResponseEntity.ok(page);
    }


    @RequestMapping(value = "/usuarios", method=RequestMethod.DELETE)
    @Transactional
    public ResponseEntity desativarUsuario(@RequestParam("edv") String edv,
                                           Authentication authentication) {
        validarUsuarioInstrutor.validar(authentication.getName());

        var usuario = repository.getByEdv(edv);
        usuario.desativar();

        return ResponseEntity.noContent().build();
    }
}