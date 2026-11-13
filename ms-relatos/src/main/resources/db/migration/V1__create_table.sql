CREATE TABLE relatos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    quadra_id BIGINT NOT NULL,
    nome_usuario VARCHAR(100) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    foto_url VARCHAR(255),
    data_hora DATETIME NOT NULL,
    status VARCHAR(15) NOT NULL,
    CONSTRAINT pk_relatos PRIMARY KEY (id)
);
