package br.bosch.GAETS.model.atividade;

import br.bosch.GAETS.model.materia.Materia;
import br.bosch.GAETS.model.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Table(name = "Atividades")
@Entity(name = "Atividade")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private Materia materia;

    private int turma;
    private String titulo, descricao;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataEntrega;
    private boolean ativo = true;
}