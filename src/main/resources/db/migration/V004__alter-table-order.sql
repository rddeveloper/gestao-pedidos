
ALTER TABLE pedido
ADD COLUMN data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE pedido
ADD COLUMN data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE produto
ADD COLUMN data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE produto
ADD COLUMN data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE pedido_produto
ADD COLUMN data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP;