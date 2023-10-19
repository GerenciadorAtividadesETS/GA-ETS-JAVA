package br.bosch.GAETS.model.materia;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMateria(@NotBlank String nome,
                                   @NotBlank String cor) {
}