-- ============================
-- Usuário admin
-- ============================
-- INSERT INTO usuarios (nome, email, senha, role, ativo)
-- ('admin', 'admin@admin.com', '{noop}123456', 'ADMIN', true);

-- ============================
-- Gêneros
-- ============================
INSERT IGNORE INTO generos (id, nome) VALUES (1, 'Masculino');
INSERT IGNORE INTO generos (id, nome) VALUES (2, 'Feminino');
INSERT IGNORE INTO generos (id, nome) VALUES (3, 'Infantil');
INSERT IGNORE INTO generos (id, nome) VALUES (4, 'Unissex');

-- ============================
-- Categorias
-- ============================
INSERT IGNORE INTO categorias (id, nome) VALUES (1, 'Casual');
INSERT IGNORE INTO categorias (id, nome) VALUES (2, 'Esporte');
INSERT IGNORE INTO categorias (id, nome) VALUES (3, 'Inverno');
INSERT IGNORE INTO categorias (id, nome) VALUES (4, 'Verão');
INSERT IGNORE INTO categorias (id, nome) VALUES (5, 'Academia');

-- ============================
-- Tamanhos (roupa)
-- ============================
INSERT IGNORE INTO tamanhos (id, nome) VALUES (1, 'PP');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (2, 'P');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (3, 'M');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (4, 'G');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (5, 'GG');

-- ============================
-- Tamanhos (calçados)
-- ============================
INSERT IGNORE INTO tamanhos (id, nome) VALUES (6, '35');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (7, '36');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (8, '37');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (9, '38');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (10, '39');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (11, '40');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (12, '41');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (13, '42');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (14, '43');
INSERT IGNORE INTO tamanhos (id, nome) VALUES (15, '44');