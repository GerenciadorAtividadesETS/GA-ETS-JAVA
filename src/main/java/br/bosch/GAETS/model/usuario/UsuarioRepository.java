package br.bosch.GAETS.model.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    UserDetails findByEdv(String edv);
    Usuario getByEdv(String edv);
    void deleteByEdv(String edv);

    @Query(
        """
            SELECT u FROM Usuario u
            WHERE
            u.turma = :turma
        """
    )
    Page<Usuario> findAllByTurma(Pageable pageable, int turma);

    @Query(
        """
            SELECT DISTINCT(u.turma) FROM Usuario u
        """
    )
    Page<Integer> findAllTurma(Pageable pageable);
}