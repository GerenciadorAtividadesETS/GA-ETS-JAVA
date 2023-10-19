package br.bosch.GAETS.model.materia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
    Materia getByNome(String Nome);
    void deleteByNome(String nome);
}