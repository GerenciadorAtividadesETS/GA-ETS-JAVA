package br.bosch.GAETS.model.usuario;

import br.bosch.GAETS.model.turma.DadosTurma;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record DadosUsuario(@Valid DadosTurma turma,
                           @NotBlank @Pattern(regexp = "\\d{8}") String edv,
                           @NotBlank String nome,
                           @NotBlank String senha,
                           @NotNull boolean instrutor) {
}