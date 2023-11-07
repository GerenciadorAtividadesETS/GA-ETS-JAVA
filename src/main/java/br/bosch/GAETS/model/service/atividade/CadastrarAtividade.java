package br.bosch.GAETS.model.service.atividade;

import br.bosch.GAETS.model.atividade.*;
import br.bosch.GAETS.model.materia.MateriaRepository;
import br.bosch.GAETS.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CadastrarAtividade {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private List<ValidarCadastroAtividade> validadores;

    public DadosRetornoAtividade cadastrar(DadosCadastroAtividade dadosCadastroAtividade, String edv) {
        validadores.forEach(v -> v.validar(dadosCadastroAtividade, edv));

        var usuario = usuarioRepository.getByEdv(edv);
        var materia = materiaRepository.getReferenceById(dadosCadastroAtividade.idMateria());

        var atividade = new Atividade(0, usuario, materia, dadosCadastroAtividade.turma(), dadosCadastroAtividade.titulo(), dadosCadastroAtividade.descricao(), LocalDateTime.now(), dadosCadastroAtividade.dataEntrega(), true);
        atividadeRepository.save(atividade);

        return new DadosRetornoAtividade(atividade);
    }
}