package br.bosch.GAETS.model.service.resposta;

import br.bosch.GAETS.model.resposta.DadosCadastroResposta;

public interface ValidarCadastroResposta {
    void validar(DadosCadastroResposta dadosCadastroResposta, String edv);
}