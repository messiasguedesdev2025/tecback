-- Configuração para H2 em memória com create-drop

-- 1. LIMPEZA
DELETE FROM series;
DELETE FROM filmes;
DELETE FROM generos;
DELETE FROM planos;

-- 2. GÊNEROS
INSERT INTO generos (id, nome, descricao) VALUES (1, 'Ação', 'Cenas de combate, perseguições e grandes jornadas.');
INSERT INTO generos (id, nome, descricao) VALUES (2, 'Comédia', 'Conteúdo focado em humor e sátira social.');
INSERT INTO generos (id, nome, descricao) VALUES (3, 'Drama', 'Histórias focadas em emoções e desenvolvimento profundo de personagens.');
INSERT INTO generos (id, nome, descricao) VALUES (4, 'Ficção Científica', 'Conteúdo focado em tecnologia futurista, espaço e distopias.');

-- 3. FILMES
-- Mantive a estrutura padrão de filmes, assumindo que sua entidade Filme está correta.
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('Mad Max: Estrada da Fúria', 'Em um futuro pós-apocalíptico, uma mulher se rebela contra um tirano.', 120, 1, 2015, 16, TRUE, 14);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('Duro de Matar', 'Um policial de Nova York deve salvar reféns em um arranha-céu de Los Angeles.', 132, 1, 1988, 16, TRUE, 10);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('Superbad: É Hoje', 'Dois amigos se esforçam para comprar álcool para uma festa antes de irem para a faculdade.', 113, 2, 2007, 18, TRUE, 8);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('O Grande Hotel Budapeste', 'As aventuras de um lendário concierge e seu jovem aprendiz em um famoso hotel europeu.', 100, 2, 2014, 14, TRUE, 7);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('A Lista de Schindler', 'Um empresário alemão salva mais de mil refugiados judeus do Holocausto.', 195, 3, 1993, 16, TRUE, 5);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('Um Sonho de Liberdade', 'Um banqueiro é condenado à prisão por um crime que não cometeu e traça um plano de fuga.', 142, 3, 1994, 14, TRUE, 15);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('A Origem', 'Um ladrão que rouba segredos corporativos através de tecnologia de compartilhamento de sonhos.', 148, 4, 2010, 14, TRUE, 15);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('Blade Runner 2049', 'Um caçador de andróides descobre um segredo que pode mergulhar a sociedade no caos.', 164, 4, 2017, 14, TRUE, 12);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('007: Cassino Royale', 'James Bond deve derrotar um banqueiro de terroristas em um jogo de pôquer de alto risco.', 144, 1, 2006, 14, TRUE, 11);
INSERT INTO filmes (titulo, descricao, duracao_minutos, genero_id, ano_lancamento, classificacao_indicativa, disponivel, estoque) VALUES ('Forrest Gump: O Contador de Histórias', 'A vida e as aventuras de um homem simples que testemunha eventos históricos.', 142, 3, 1994, 10, TRUE, 9);

-- 4. SÉRIES (CORRIGIDO PARA SUA CLASSE JAVA)
-- Colunas usadas: titulo, descricao, genero_id, temporadas, total_episodios
-- Removido: classificacao_indicativa, em_andamento, duracao_minutos (pois não existem na classe Serie)

INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('Dark', 'O desaparecimento de duas crianças expõe as relações fraturadas entre quatro famílias.', 4, 3, 26);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('Stranger Things', 'Jovens amigos em busca de respostas após o desaparecimento de um deles.', 4, 4, 34);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('The Office (US)', 'O cotidiano de um escritório sob a liderança de um chefe excêntrico.', 2, 9, 201);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('Breaking Bad', 'Um professor de química com câncer começa a produzir metanfetamina.', 3, 5, 62);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('The Crown', 'A história do reinado da Rainha Elizabeth II.', 3, 6, 60);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('Cobra Kai', 'Décadas após a rivalidade, Daniel e Johnny reabrem seus dojôs.', 1, 6, 60);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('O Mandaloriano', 'Um caçador de recompensas solitário nos confins da galáxia.', 4, 3, 24);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('Brooklyn Nine-Nine', 'Histórias de detetives da 99ª Delegacia do Brooklyn.', 2, 8, 153);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('Ozark', 'Um consultor financeiro lava dinheiro para um cartel de drogas.', 3, 4, 44);
INSERT INTO series (titulo, descricao, genero_id, temporadas, total_episodios) VALUES ('The Witcher', 'Um caçador de monstros luta para encontrar seu lugar no mundo.', 1, 3, 24);

-- 5. PLANOS
INSERT INTO planos (nome, descricao, preco, duracao_meses, limite_dispositivos) VALUES ('Plano Básico (HD)', 'Acesso a conteúdo em HD em 1 tela simultânea.', 29.90, 1, 1);
INSERT INTO planos (nome, descricao, preco, duracao_meses, limite_dispositivos) VALUES ('Plano Padrão (Full HD)', 'Acesso a conteúdo em Full HD em 2 telas simultâneas.', 39.90, 1, 2);
INSERT INTO planos (nome, descricao, preco, duracao_meses, limite_dispositivos) VALUES ('Plano Premium (4K)', 'Acesso a conteúdo em 4K em 4 telas simultâneas.', 59.90, 1, 4);
INSERT INTO planos (nome, descricao, preco, duracao_meses, limite_dispositivos) VALUES ('Plano Anual Padrão', 'Plano Padrão com desconto para pagamento anual.', 399.00, 12, 2);
INSERT INTO planos (nome, descricao, preco, duracao_meses, limite_dispositivos) VALUES ('Promoção Estudantil', 'Plano Básico com desconto para estudantes.', 19.90, 1, 1);
INSERT INTO planos (nome, descricao, preco, duracao_meses, limite_dispositivos) VALUES ('Plano Corporativo', 'Plano 4K com 10 acessos simultâneos para empresas.', 99.90, 1, 10);

-- 6. SEQUÊNCIAS
ALTER SEQUENCE IF EXISTS GENERO_SEQ RESTART WITH 70;
ALTER SEQUENCE IF EXISTS FILMES_SEQ RESTART WITH 70;
ALTER SEQUENCE IF EXISTS SERIES_SEQ RESTART WITH 70;
ALTER SEQUENCE IF EXISTS PLANOS_SEQ RESTART WITH 70;
ALTER SEQUENCE IF EXISTS hibernate_sequence RESTART WITH 70;