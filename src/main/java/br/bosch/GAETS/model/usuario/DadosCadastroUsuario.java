package br.bosch.GAETS.model.usuario;

import jakarta.validation.constraints.*;

public record DadosCadastroUsuario(int turma,
                                   @NotBlank @Pattern(regexp = "\\d{8}") String edv,
                                   @NotBlank String nome,
                                   @NotBlank String senha,
                                   @NotNull boolean instrutor) {
}