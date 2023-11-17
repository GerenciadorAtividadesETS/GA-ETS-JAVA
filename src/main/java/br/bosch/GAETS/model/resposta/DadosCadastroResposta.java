package br.bosch.GAETS.model.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(@NotNull int idAtividade,
                                    @NotNull int idUsuario,
                                    @NotBlank String compartilhado,
                                    String github,
                                    String comentario) {
}