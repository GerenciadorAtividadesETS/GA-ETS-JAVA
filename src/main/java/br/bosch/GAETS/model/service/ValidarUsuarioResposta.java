package br.bosch.GAETS.model.service;

import br.bosch.GAETS.model.atividade.AtividadeRepository;
import br.bosch.GAETS.model.resposta.DadosCadastroResposta;
import br.bosch.GAETS.model.resposta.RespostaRepository;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarUsuarioResposta implements ValidarCadastroResposta {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    public void validar(DadosCadastroResposta dadosCadastroResposta, String edv) {
        var usuario = usuarioRepository.getReferenceById(dadosCadastroResposta.idUsuario());
        var atividade = atividadeRepository.getReferenceById(dadosCadastroResposta.idAtividade());

        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI");
        System.out.println("AQUI ID USUÁRIO");
//        O ID DO RECORD VEM VAZIO PORQUE NÃO É PASSADO O ID NA REQUISIÇÃO, MAS SIM DENTRO DO BACKEND TEM O LINK DO EDV DO TOKEN COM O USUÁRIO
//        PORTANTO PARA ENCONTRAR O USUÁRIO VOU PRECISAR PASSAR O EDV AQUI PARA O VALIDAR

        System.out.println(dadosCadastroResposta.idUsuario());


        var resposta = respostaRepository.findResposta(usuario.getEdv(), atividade);


        System.out.println(resposta);
    }
}