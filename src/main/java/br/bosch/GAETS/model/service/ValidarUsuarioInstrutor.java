package br.bosch.GAETS.model.service;

import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarUsuarioInstrutor {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // VALIDAR SE USUÁRIO LOGADO É INSTRUTOR
    public void validar(String edv) {
        var usuario = usuarioRepository.getByEdv(edv);

        if (usuario.getTurma() != 0) {
            throw new RuntimeException("Seu usuário não tem permissão de acesso");
        }
    }
}