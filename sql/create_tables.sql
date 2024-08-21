CREATE TABLE tb_filial (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    atvo BOOLEAN NOT NULL,
    dt_registro DATE NOT NULL,
    dh_atualizacao TIMESTAMP NOT NULL
);

CREATE TABLE tb_vendedor (
    id SERIAL PRIMARY KEY,
    matricula VARCHAR(255) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    dt_nascimento DATE,
    documento VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    tipo_contrato VARCHAR(255) NOT NULL,
    branch_id BIGINT NOT NULL,
    CONSTRAINT fk_branch
        FOREIGN KEY (branch_id)
        REFERENCES tb_filial(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_vendedor_documento ON tb_vendedor(documento);