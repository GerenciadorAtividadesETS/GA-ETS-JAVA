package br.bosch.GAETS.model.materia;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "Materias")
@Entity(name = "Materia")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome, cor;

    public Materia(DadosCadastroMateria dadosCadastroMateria) {
        this.nome = dadosCadastroMateria.nome();
        this.cor = dadosCadastroMateria.cor();
    }
}