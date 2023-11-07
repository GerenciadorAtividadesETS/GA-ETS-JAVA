package br.bosch.GAETS.model.service.atividade;

import br.bosch.GAETS.model.atividade.DadosCadastroAtividade;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarUsuarioInstrutor implements ValidarCadastroAtividade {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validar(DadosCadastroAtividade dadosCadastroAtividade, String edv) {
        var usuario = usuarioRepository.getByEdv(edv);

        if (usuario.getTurma() != 0) {
            throw new RuntimeException("Seu usuário não tem permissão de acesso");
        }
    }
}