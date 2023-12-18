CREATE TABLE Respostas(
    id INT NOT NULL AUTO_INCREMENT,
    atividade_id INT NOT NULL,
    usuario_id INT NOT NULL,
    data_alteracao DATETIME NOT NULL,
    compartilhado VARCHAR(255) NOT NULL,
    github VARCHAR(255),
    comentario LONGTEXT,

    PRIMARY KEY(id),
    CONSTRAINT fk_respostas_atividades_id FOREIGN KEY(atividade_id) REFERENCES atividades(id),
    CONSTRAINT fk_respostas_usuarios_id FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
)