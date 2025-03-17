CREATE TABLE produto (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(255),
    preco DECIMAL(10, 2) NOT NULL,
    estoque INT NOT NULL
);

CREATE TABLE pedido (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(50) NOT NULL,
    usuario_id BIGINT,
    valor_total DECIMAL(10, 2),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE pedido_produto (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    pedido_id BIGINT,
    produto_id BIGINT,
    quantidade INT NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);