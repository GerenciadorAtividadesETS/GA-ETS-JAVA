package br.bosch.GAETS.model.atividade;

import java.time.LocalDateTime;

public record DadosRetornoAtividade(int id,
                                    int idUsuario,
                                    int idMateria,
                                    int turma,
                                    String titulo,
                                    String descricao,
                                    LocalDateTime dataCriacao,
                                    LocalDateTime dataEntrega) {

    public DadosRetornoAtividade(Atividade atividade) {
        this(
                atividade.getId(),
                atividade.getUsuario().getId(),
                atividade.getMateria().getId(),
                atividade.getTurma(),
                atividade.getTitulo(),
                atividade.getDescricao(),
                atividade.getDataCriacao(),
                atividade.getDataEntrega()
        );
    }
}