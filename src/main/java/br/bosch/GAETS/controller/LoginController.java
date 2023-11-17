package br.bosch.GAETS.controller;

import br.bosch.GAETS.infra.security.DadosTokenJWT;
import br.bosch.GAETS.infra.security.TokenService;
import br.bosch.GAETS.model.service.usuario.ValidarUsuarioAtivo;
import br.bosch.GAETS.model.usuario.DadosLogin;
import br.bosch.GAETS.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Login")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ValidarUsuarioAtivo validador;

    @Operation(
        summary = "Realiza o login do usuário no sistema",
        tags = { "Login" },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "EDV e Senha do usuário a ser logado", required = true),
        responses = {
                @ApiResponse(responseCode = "200", description = "Login bem sucedido, retorna o Token JWT",
                        content = @io.swagger.v3.oas.annotations.media.Content(
                                mediaType = "application/json",
                                examples = @ExampleObject(
                                        name = "Login bem sucedido",
                                        value = "{ \"tokenJWT\": \"string\" }"
                                )
                        )
                ),
                @ApiResponse(responseCode = "404", description = "Usuário inexistente e/ou credenciais inválidas",
                        content = @io.swagger.v3.oas.annotations.media.Content(
                                mediaType = "",
                                examples = @ExampleObject(
                                        name = "",
                                        value = ""
                                )
                        )
                )
        }
    )
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