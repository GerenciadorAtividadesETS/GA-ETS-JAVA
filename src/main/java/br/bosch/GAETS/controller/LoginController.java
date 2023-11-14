package br.bosch.GAETS.controller;

import br.bosch.GAETS.infra.security.DadosTokenJWT;
import br.bosch.GAETS.infra.security.TokenService;
import br.bosch.GAETS.model.service.usuario.ValidarUsuarioAtivo;
import br.bosch.GAETS.model.usuario.DadosLogin;
import br.bosch.GAETS.model.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ValidarUsuarioAtivo validador;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosLogin dadosLogin) {
        try {
            var token = new UsernamePasswordAuthenticationToken(dadosLogin.edv(), dadosLogin.senha());
            validador.validar(dadosLogin.edv());
            var authentication = manager.authenticate(token);
            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        }

        catch(RuntimeException e) {
            throw new RuntimeException("Usuário inexistente e/ou credenciais inválidas");
        }
    }
}