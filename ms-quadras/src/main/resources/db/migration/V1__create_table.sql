CREATE TABLE quadras (
    id BIGINT NOT NULL AUTO_INCREMENT,
    logradouro VARCHAR(150) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    nota_risco INT NOT NULL,
    nivel_risco VARCHAR(10) NOT NULL,
    status_alagamento VARCHAR(10) NOT NULL,
    data_atualizacao DATETIME NOT NULL,
    CONSTRAINT pk_quadras PRIMARY KEY (id)
);
