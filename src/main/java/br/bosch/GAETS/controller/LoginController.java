package br.bosch.GAETS.controller;

import br.bosch.GAETS.infra.security.DadosTokenJWT;
import br.bosch.GAETS.infra.security.TokenService;
import br.bosch.GAETS.model.usuario.DadosLogin;
import br.bosch.GAETS.model.usuario.DadosUsuario;
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

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosLogin dadosLogin) {
        var token = new UsernamePasswordAuthenticationToken(dadosLogin.edv(), dadosLogin.senha());
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}