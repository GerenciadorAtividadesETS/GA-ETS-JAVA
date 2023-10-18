CREATE TABLE Usuarios(
    id INT NOT NULL AUTO_INCREMENT,
    turma int,
    edv VARCHAR(8) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    ativo TINYINT NOT NULL,
    instrutor TINYINT NOT NULL,

    PRIMARY KEY(id)
)