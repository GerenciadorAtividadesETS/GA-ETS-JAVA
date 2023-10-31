package br.bosch.GAETS.model.resposta;

public record DadosRetornoRespostaId(int idResposta,
                                     int idAtividade,
                                     int idUsuario) {

    public DadosRetornoRespostaId(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getAtividade().getId(),
                resposta.getUsuario().getId()
        );
    }
}