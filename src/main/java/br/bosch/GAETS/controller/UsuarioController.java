package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.service.usuario.ValidarUsuarioInstrutor;
import br.bosch.GAETS.model.usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.List;

@Schema(hidden = true)
@Tag(name = "Usuários", description = "Endpoints da entidade Usuários")
@RestController
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private List<ValidarUsuarioInstrutor> validadores;


    // FUNCIONANDO
    @Operation(
            summary = "Cadastra um novo usuário no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do usuário a ser cadastrado",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Usuário Válido",
                                    value = "{ \"turma\": \"1\"," +
                                            "\"edv\": \"12345678\"," +
                                            "\"nome\": \"João\"," +
                                            "\"senha\": \"minhaSenha\"," +
                                            "\"cor\": \"FFFFFF\"}"
                            )
                    )),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Usuário Válido",
                                            value = "{ \"turma\": \"1\"," +
                                                    "\"edv\": \"12345678\"," +
                                                    "\"nome\": \"João\"," +
                                                    "\"senha\": \"minhaSenha\"," +
                                                    "\"cor\": \"FFFFFF\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
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
    @RequestMapping(value = "/usuarios", method=RequestMethod.POST)
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dadosCadastroUsuario,
                                           UriComponentsBuilder uriComponentsBuilder) {
        if (dadosCadastroUsuario.turma() > 0) {
            var senhaCriptografada = bCryptPasswordEncoder.encode(dadosCadastroUsuario.senha());
            var usuario = new Usuario(dadosCadastroUsuario);
            var uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
            usuario.setSenha(senhaCriptografada);
            repository.save(usuario);

            return ResponseEntity.created(uri).body(new DadosRetornoUsuario(usuario));
        }
        else {
            throw new RuntimeException("Turma inválida");
        }
    }

    // FUNCIONANDO
    @Operation(
            summary = "Retorna os dados de um usuário cadastrado no sistema",
            parameters = {
                    @Parameter(name = "id", description = "ID do usuário a ser retornado", in = ParameterIn.PATH)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Usuário Válido",
                                            value = "{ \"turma\": \"1\"," +
                                                    "\"edv\": \"12345678\"," +
                                                    "\"nome\": \"João\"," +
                                                    "\"senha\": \"minhaSenha\"," +
                                                    "\"cor\": \"FFFFFF\"}"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos",
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
    @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    public ResponseEntity detalharUsuario(Authentication authentication) {
        var usuario = repository.getByEdv(authentication.getName());
        return ResponseEntity.ok(new DadosRetornoUsuario(usuario));
    }
    @RequestMapping(value = "/usuarios/{edv}", method = RequestMethod.GET)
    public ResponseEntity detalharUsuario(@PathVariable String edv) {
        var usuario = repository.getByEdv(edv);
        return ResponseEntity.ok(new DadosRetornoUsuario(usuario));
    }

    // FUNCIONANDO
    @RequestMapping(value = "/turmas", method=RequestMethod.GET)
    public ResponseEntity<Page<DadosRetornoTurma>> listarTodasTurmas(Pageable pageable,
                                                                     Authentication authentication) {
        validadores.forEach(v -> v.validar(authentication.getName()));

        var page = repository.findAllTurma(pageable).map(DadosRetornoTurma::new);
        return ResponseEntity.ok(page);
    }


    // FUNCIONANDO
    @RequestMapping(value = "/turmas/{idTurma}", method=RequestMethod.GET)
    public ResponseEntity<Page<DadosRetornoUsuario>> listarUsuariosPorTurma(@PathVariable int idTurma,
                                                                            @PageableDefault(sort = {"nome"}) Pageable pageable,
                                                                            Authentication authentication) {
        validadores.forEach(v -> v.validar(authentication.getName()));

        var page = repository.findAllByTurma(pageable, idTurma).map(DadosRetornoUsuario::new);
        return ResponseEntity.ok(page);
    }


    // FUNCIONANDO
    @RequestMapping(value = "/usuarios/{edv}", method=RequestMethod.DELETE)
    @Transactional
    public ResponseEntity desativarUsuario(@PathVariable String edv,
                                           Authentication authentication) {
        validadores.forEach(v -> v.validar(authentication.getName()));

        try {
            var usuario = repository.getByEdv(edv);
            usuario.desativar();
            return ResponseEntity.noContent().build();
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}