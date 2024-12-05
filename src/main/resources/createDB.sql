CREATE DATABASE domingodelazer;

USE domingodelazer;

CREATE TABLE escola (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  nome varchar(500) NOT NULL
);

CREATE TABLE usuario (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL,
    senha VARCHAR(300) NOT NULL,
    admin BOOLEAN NOT NULL DEFAULT FALSE,
    email VARCHAR(200)
);

CREATE TABLE dataAula (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    dataaula DATETIME NOT NULL,
    domingo VARCHAR(1) NOT NULL,
    escola_id INT NOT NULL,
  INDEX fk_escola_idx (escola_id ASC),
  CONSTRAINT fk_escola_dataaula
    FOREIGN KEY (escola_id)
    REFERENCES escola (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE serie (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    serie VARCHAR(3) NOT NULL,
    sala VARCHAR(7) NOT NULL,
    domingo VARCHAR(1) NOT NULL,
    escola_id INT NOT NULL,
  INDEX fk_escola_idx (escola_id ASC),
  CONSTRAINT fk_escola_serie
    FOREIGN KEY (escola_id)
    REFERENCES escola (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE registroPresencas (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    fevereiro INT NULL DEFAULT 0,
    marco INT NULL DEFAULT 0,
    abril INT NULL DEFAULT 0,
    maio INT NULL DEFAULT 0,
    junho INT NULL DEFAULT 0,
    agosto INT NULL DEFAULT 0,
    setembro INT NULL DEFAULT 0,
    outubro INT NULL DEFAULT 0,
    novembro INT NULL DEFAULT 0
);

CREATE TABLE arquivosaluno (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  foto BLOB NULL,
  matricula BLOB NULL
);

CREATE TABLE usuarioAcesso (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  escola_id INT NOT NULL,
  usuario_id INT NOT NULL,
  INDEX fk_escola_idx (escola_id ASC),
  INDEX fk_usuario_idx (usuario_id ASC),
  CONSTRAINT fk_escola_acesso
    FOREIGN KEY (escola_id)
    REFERENCES escola (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_usuario
    FOREIGN KEY (usuario_id)
    REFERENCES usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE aluno (
  id INT NOT NULL AUTO_INCREMENT,
  codigo VARCHAR(6) NULL,
  nome VARCHAR(100) NOT NULL,
  sexo VARCHAR(1) NULL,
  nascimento DATE NOT NULL,
  endereco VARCHAR(400) NULL,
  sapato INT NULL,
  calca INT NULL,
  camisa INT NULL,
  nomeResponsavel VARCHAR(100) NULL,
  telefoneResponsavel VARCHAR(15) NULL,
  emailResponsavel VARCHAR(200) NULL,
  ativo BOOLEAN NULL,
  sairSozinho BOOLEAN NULL,
  numeroSacolinha VARCHAR(10) NULL,
  serie_id INT NOT NULL,
  registro_id INT NOT NULL,
  escola_id INT NOT NULL,
  arquivo_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_serie_idx (serie_id ASC),
  INDEX fk_registro_idx (registro_id ASC),
  INDEX fk_escola_idx (escola_id ASC),
  INDEX fk_arquivo_idx (arquivo_id ASC),
  CONSTRAINT fk_serie
    FOREIGN KEY (serie_id)
    REFERENCES serie (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_registro
    FOREIGN KEY (registro_id)
    REFERENCES registroPresencas (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_escola_aluno
    FOREIGN KEY (escola_id)
    REFERENCES escola (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_arquivo
    FOREIGN KEY (arquivo_id)
    REFERENCES arquivosaluno (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);