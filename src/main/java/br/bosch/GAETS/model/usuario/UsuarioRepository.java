package br.bosch.GAETS.model.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByEdv(String edv);
    Usuario getByEdv(String edv);
    Usuario getByEdvAndAtivoTrue(String edv);

    @Query(
        """
            SELECT u FROM Usuario u
            WHERE
            u.turma = :turma AND
            u.ativo = true
        """
    )
    Page<Usuario> findAllByTurma(Pageable pageable, int turma);

    @Query(
        """
            SELECT DISTINCT(u.turma) FROM Usuario u
            WHERE
            u.turma != 0 AND
            u.ativo = true
        """
    )
    Page<Integer> findAllTurma(Pageable pageable);
}