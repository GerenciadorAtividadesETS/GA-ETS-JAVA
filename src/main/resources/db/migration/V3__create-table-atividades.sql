CREATE TABLE Atividades(
    id INT NOT NULL AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    materia_id INT NOT NULL,
    turma INT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao LONGTEXT,
    data_criacao DATETIME NOT NULL,
    data_entrega DATETIME NOT NULL,
    ativo TINYINT NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_atividades_usuarios_id FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_atividades_materias_id FOREIGN KEY(materia_id) REFERENCES materias(id)
)