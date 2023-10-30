package br.bosch.GAETS.controller;

import br.bosch.GAETS.model.resposta.DadosCadastroResposta;
import br.bosch.GAETS.model.resposta.RespostaRepository;
import br.bosch.GAETS.model.service.CadastrarResposta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas")
public class RespostaController {
    @Autowired
    private RespostaRepository repository;

    @Autowired
    private CadastrarResposta cadastrarResposta;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarResposta(@RequestBody @Valid DadosCadastroResposta dadosCadastroResposta,
                                            Authentication authentication) {
        var edv = authentication.getName();
        var resposta = cadastrarResposta.cadastrar(dadosCadastroResposta, edv);

        return ResponseEntity.ok(resposta);
    }
}