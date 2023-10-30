package br.bosch.GAETS.model.resposta;

import br.bosch.GAETS.model.atividade.Atividade;
import br.bosch.GAETS.model.materia.Materia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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


    // FAZER ESSA QUERY FUNCIONAR NO WORKBENCH, PARECE QUE ELA ESTÁ RETORNANDO UM MONTE DE REGISTROS
    @Query(
        """
            SELECT DISTINCT(r.id), a.id FROM Resposta r
            INNER JOIN r.atividade a
            WHERE
            a.materia = :materia
        """
    )
    Page<Resposta> findAllByMateria(Pageable pageable, Materia materia);
}