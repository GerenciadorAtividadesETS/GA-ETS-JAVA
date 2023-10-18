package br.bosch.GAETS.model.turma;

import jakarta.validation.constraints.NotBlank;

public record DadosTurma(@NotBlank String turma) {
}