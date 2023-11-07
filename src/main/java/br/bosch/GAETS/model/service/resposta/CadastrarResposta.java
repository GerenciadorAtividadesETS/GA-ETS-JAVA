package br.bosch.GAETS.model.service.resposta;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.resposta.DadosCadastroResposta;
import br.bosch.GAETS.model.resposta.DadosRetornoResposta;
import br.bosch.GAETS.model.resposta.Resposta;
import br.bosch.GAETS.model.resposta.RespostaRepository;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CadastrarResposta {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private List<ValidarCadastroResposta> validadores;

    public DadosRetornoResposta cadastrar(DadosCadastroResposta dadosCadastroResposta, String edv) {
        if (!atividadeRepository.existsById(dadosCadastroResposta.idAtividade())) {
            throw new RuntimeException("Atividade não existe");
        }

        var usuario = usuarioRepository.getByEdv(edv);
        var atividade = atividadeRepository.getReferenceById(dadosCadastroResposta.idAtividade());

        // VALIDAÇÃO REGRAS DE NEGÓCIO
        validadores.forEach(v -> v.validar(dadosCadastroResposta, edv));

        var resposta = new Resposta(0, atividade, usuario, LocalDateTime.now(), dadosCadastroResposta.compartilhado(), dadosCadastroResposta.github(), dadosCadastroResposta.comentario());
        respostaRepository.save(resposta);

        return new DadosRetornoResposta(resposta);
    }
}