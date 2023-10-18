package br.bosch.GAETS.model.usuario;

import br.bosch.GAETS.model.turma.Turma;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private Turma turma;

    private String edv, nome, senha;
    private boolean ativo, instrutor;

    public Usuario(DadosUsuario dadosUsuario) {
        this.turma = new Turma(dadosUsuario.turma());
        this.edv = dadosUsuario.edv();
        this.nome = dadosUsuario.nome();
        this.senha = dadosUsuario.senha();
        this.ativo = true;
        this.instrutor = dadosUsuario.instrutor();
    }
}