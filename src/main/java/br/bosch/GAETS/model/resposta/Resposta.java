package br.bosch.GAETS.model.resposta;

import br.bosch.GAETS.model.atividade.Atividade;
import br.bosch.GAETS.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Table(name = "Respostas")
@Entity(name = "Resposta")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atividades_id")
    private Atividade atividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    private LocalDateTime dataAlteracao;
    private String compartilhado, github, comentario;
}