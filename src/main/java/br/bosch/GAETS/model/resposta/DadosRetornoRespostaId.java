package br.bosch.GAETS.model.resposta;

public record DadosRetornoRespostaId(int idResposta,
                                     int idAtividade) {

    public DadosRetornoRespostaId(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getAtividade().getId()
        );
    }
}
