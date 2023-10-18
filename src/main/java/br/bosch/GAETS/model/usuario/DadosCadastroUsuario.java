package br.bosch.GAETS.model.usuario;

import jakarta.validation.constraints.*;

public record DadosCadastroUsuario(@NotNull int turma,
                                   @NotBlank @Pattern(regexp = "\\d{8}") String edv,
                                   @NotBlank String nome,
                                   @NotBlank String senha,
                                   @NotNull String cor) {
}