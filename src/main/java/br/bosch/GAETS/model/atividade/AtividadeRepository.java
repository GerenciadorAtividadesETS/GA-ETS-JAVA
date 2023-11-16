package br.bosch.GAETS.model.atividade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
    boolean existsById(int id);

    @Query(
        """
            SELECT a FROM Atividade a
            WHERE
            a.turma = :turma
        """
    )
    Page<Atividade> findAllByTurma(Pageable pageable, int turma);
}