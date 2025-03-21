CREATE TABLE usuario (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE perfil (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) UNIQUE NOT NULL,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE permissao (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) UNIQUE NOT NULL,
    descricao VARCHAR(100) NOT NULL
);

CREATE TABLE usuario_perfil (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id)
);

CREATE TABLE perfil_permissao (
    perfil_id BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL,
    PRIMARY KEY (perfil_id, permissao_id)
);

CREATE TABLE refresh_tokens (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) NOT NULL,
    data_expiracao TIMESTAMP NOT NULL,
    usuario_id BIGINT
);

ALTER TABLE refresh_tokens
ADD CONSTRAINT FK_refresh_token_usuario
FOREIGN KEY (usuario_id) REFERENCES usuario(id);

ALTER TABLE usuario_perfil
ADD CONSTRAINT fk_usuario_perfil_usuario
FOREIGN KEY (usuario_id) REFERENCES usuario(id);

ALTER TABLE usuario_perfil
ADD CONSTRAINT fk_usuario_perfil_perfil
FOREIGN KEY (perfil_id) REFERENCES perfil(id);

ALTER TABLE perfil_permissao
ADD CONSTRAINT fk_perfil_permissao_perfil
FOREIGN KEY (perfil_id) REFERENCES perfil(id);

ALTER TABLE perfil_permissao
ADD CONSTRAINT fk_perfil_permissao_permissao
FOREIGN KEY (permissao_id) REFERENCES permissao(id);