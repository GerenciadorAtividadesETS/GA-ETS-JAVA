package br.bosch.GAETS.model.resposta;

import java.time.LocalDateTime;

public record DadosRetornoResposta(int id,
                                   int idAtividade,
                                   int idUsuario,
                                   LocalDateTime dataAlteracao,
                                   String compartilhado,
                                   String github,
                                   String comentario) {

    public DadosRetornoResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getAtividade().getId(),
                resposta.getUsuario().getId(),
                resposta.getDataAlteracao(),
                resposta.getCompartilhado(),
                resposta.getGithub(),
                resposta.getComentario()
        );
    }
}