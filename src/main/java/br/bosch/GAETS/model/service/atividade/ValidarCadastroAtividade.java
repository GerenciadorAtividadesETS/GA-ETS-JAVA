package br.bosch.GAETS.model.service.atividade;

import br.bosch.GAETS.model.atividade.DadosCadastroAtividade;

public interface ValidarCadastroAtividade {
    void validar(DadosCadastroAtividade dadosCadastroAtividade, String edv);
}