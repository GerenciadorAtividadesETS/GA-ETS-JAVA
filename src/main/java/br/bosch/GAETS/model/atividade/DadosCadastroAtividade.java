package br.bosch.GAETS.model.atividade;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record DadosCadastroAtividade(@NotNull int idUsuario,
                                     @NotNull int idMateria,
                                     @NotNull int turma,
                                     @NotBlank String titulo,
                                     String descricao,
                                     @NotNull @Future LocalDateTime dataEntrega) {
}