package br.bosch.GAETS.model.resposta;

import br.bosch.GAETS.model.atividade.Atividade;
import br.bosch.GAETS.model.materia.Materia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Integer> {
    @Query(
        """
            SELECT r FROM Resposta r
            INNER JOIN r.usuario u
            WHERE
            r.atividade = :atividade AND
            u.edv = :edv
        """
    )
    Resposta findResposta(String edv, Atividade atividade);

    @Query(
        """
            SELECT r FROM Resposta r
            INNER JOIN r.atividade a
            WHERE
            a.materia = :materia
        """
    )
    Page<Resposta> findAllByMateria(Pageable pageable, Materia materia);

    @Query(
        """
            SELECT r FROM Resposta r
            INNER JOIN r.atividade a
            WHERE r.atividade = :atividade AND
            a.turma = :idTurma
        """
    )
    Page<Resposta> findAllByTurma(Pageable pageable, Atividade atividade, int idTurma);
}