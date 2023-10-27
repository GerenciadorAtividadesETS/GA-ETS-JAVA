CREATE TABLE Respostas(
    id INT NOT NULL AUTO_INCREMENT,
    atividades_id INT NOT NULL,
    usuarios_id INT NOT NULL,
    data_entrega DATETIME NOT NULL,
    data_alteracao DATETIME NOT NULL,
    compartilhado VARCHAR(255) NOT NULL,
    github VARCHAR(255),
    comentario LONGTEXT,

    PRIMARY KEY(id),
    CONSTRAINT fk_respostas_atividades_id FOREIGN KEY(atividades_id) REFERENCES atividades(id),
    CONSTRAINT fk_respostas_usuarios_id FOREIGN KEY(usuarios_id) REFERENCES usuarios(id)
)