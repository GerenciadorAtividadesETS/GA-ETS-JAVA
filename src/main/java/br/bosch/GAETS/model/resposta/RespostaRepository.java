package br.bosch.GAETS.model.resposta;

import br.bosch.GAETS.model.atividade.Atividade;
import br.bosch.GAETS.model.materia.Materia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Integer> {
    boolean existsById(int id);
    @Query(
        """
            SELECT r FROM Resposta r
            WHERE
            r.atividade = :atividade AND
            r.usuario.edv = :edv
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
            WHERE
            r.atividade = :atividade
        """
    )
    Page<Resposta> findAllByAtividade(Pageable pageable, Atividade atividade);
}