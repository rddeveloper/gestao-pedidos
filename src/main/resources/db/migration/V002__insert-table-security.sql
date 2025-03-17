-- Inserindo Perfis
INSERT INTO perfil (nome, descricao) VALUES
('ADMIN', ' administrador do sistema'),
('USER ', 'usuário operante do sistema');

-- Inserindo Permissões
INSERT INTO permissao (nome, descricao) VALUES
('CADASTRAR_PRODUTO', 'cadastrar produto'),
('LISTAR_PRODUTO', 'listar produto'),
('EDITAR_PRODUTO', 'editar produto'),
('DELETAR_PRODUTO', 'deletar produto'),
('CADASTRAR_PEDIDO', 'cadastrar pedido');

-- Associando Permissões aos Perfis
INSERT INTO perfil_permissao (perfil_id, permissao_id) VALUES
(1, 1), -- ADMIN pode cadastrar produto
(1, 2), -- ADMIN pode listar produto
(1, 3), -- ADMIN pode editar produto
(1, 4), -- ADMIN pode deletar produto
(2, 5), -- USER pode cadastrar pedido
(2, 2); -- USER pode listar produto