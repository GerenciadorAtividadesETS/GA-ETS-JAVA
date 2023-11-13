package br.bosch.GAETS.model.materia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
    void deleteByNome(String nome);

    @Query(
        """
            SELECT DISTINCT(a.materia) FROM Atividade a
            WHERE
            a.turma = :turma
        """
    )
    Page<DadosRetornoMateria> findAllByTurma(Pageable pageable, int turma);
}