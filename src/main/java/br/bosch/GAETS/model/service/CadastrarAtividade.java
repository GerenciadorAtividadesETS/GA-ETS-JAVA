package br.bosch.GAETS.model.service;

import br.bosch.GAETS.model.atividade.*;
import br.bosch.GAETS.model.materia.MateriaRepository;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CadastrarAtividade {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    public DadosRetornoAtividade cadastrar(DadosCadastroAtividade dadosCadastroAtividade, String edv) {
        var usuario = usuarioRepository.getByEdv(edv);
        // TESTAR DEPOIS TROCAR O FINDBY PELO GETREFERENCEBYID
        var materia = materiaRepository.findById(dadosCadastroAtividade.idMateria()).get();
        var atividade = new Atividade(0, usuario, materia, dadosCadastroAtividade.turma(), dadosCadastroAtividade.titulo(), dadosCadastroAtividade.descricao(), LocalDateTime.now(), dadosCadastroAtividade.dataEntrega(), true);
        atividadeRepository.save(atividade);

        return new DadosRetornoAtividade(atividade);
    }
}