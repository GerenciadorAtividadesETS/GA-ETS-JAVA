package br.bosch.GAETS.model.turma;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Turma {
    private String turma;

    public Turma(DadosTurma dadosTurma) {
        this.turma = dadosTurma.turma();
    }
}