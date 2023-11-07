package br.bosch.GAETS.model.service.resposta;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.resposta.DadosCadastroResposta;
import br.bosch.GAETS.model.resposta.RespostaRepository;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarRespostaDuplicada implements ValidarCadastroResposta {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;


    // VERIFICAR SE USUÁRIO JÁ CADASTROU RESPOSTA PARA ATIVIDADE
    public void validar(DadosCadastroResposta dadosCadastroResposta, String edv) {
        var usuario = usuarioRepository.getByEdv(edv);
        var atividade = atividadeRepository.getReferenceById(dadosCadastroResposta.idAtividade());
        var resposta = respostaRepository.findResposta(usuario.getEdv(), atividade);

        if (resposta != null) {
            throw new RuntimeException("Este usuário já tem uma resposta cadastrada para esta atividade");
        }
    }
}