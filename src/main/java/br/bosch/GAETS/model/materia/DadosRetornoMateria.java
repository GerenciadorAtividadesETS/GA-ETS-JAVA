package br.bosch.GAETS.model.materia;

public record DadosRetornoMateria(int id,
                                  String nome,
                                  String cor) {

    public DadosRetornoMateria(Materia materia) {
        this(
                materia.getId(),
                materia.getNome(),
                materia.getCor()
        );
    }
}