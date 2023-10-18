package br.bosch.GAETS.model.usuario;

public record DadosRetornoUsuario(int id,
                                  int turma,
                                  String edv,
                                  String nome,
                                  String cor) {

    public DadosRetornoUsuario(Usuario usuario) {
        this(usuario.getId(),
                usuario.getTurma(),
                usuario.getEdv(),
                usuario.getNome(),
                usuario.getCor());
    }
}