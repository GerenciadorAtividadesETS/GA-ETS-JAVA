package br.bosch.GAETS.model.service.usuario;

import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarUsuarioAtivo implements ValidarUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(String edv) {
        var usuario = usuarioRepository.getByEdvAndAtivoTrue(edv);

        if (usuario == null) {
            throw new RuntimeException("Usuário desativado");
        }
    }
}