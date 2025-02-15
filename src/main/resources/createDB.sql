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

CREATE TABLE dataaula (
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

CREATE TABLE sala (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  sala varchar(10) NOT NULL
);

CREATE TABLE serie (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    serie VARCHAR(3) NOT NULL,
    domingo VARCHAR(1) NOT NULL,
    sala_id INT NOT NULL,
    escola_id INT NOT NULL,
  INDEX fk_escola_idx (escola_id ASC),
  CONSTRAINT fk_escola_serie
    FOREIGN KEY (escola_id)
    REFERENCES escola (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  INDEX fk_sala_idx (sala_id ASC),
  CONSTRAINT fk_sala_serie
    FOREIGN KEY (sala_id)
    REFERENCES sala (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE registropresencas (
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

CREATE TABLE usuarioacesso (
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

CREATE TABLE `domingodelazer`.`planodeaula` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `mes` DATE NOT NULL,
    `tema` VARCHAR(75) NOT NULL,
    `quebragelo` LONGTEXT NOT NULL,
    `historia` LONGTEXT NOT NULL,
    `atividade` LONGTEXT NOT NULL,
    `titulohistoria` MEDIUMTEXT NOT NULL,
    `material` MEDIUMTEXT NOT NULL,
    `objetivos` MEDIUMTEXT NOT NULL,
    `escola_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `escola_planodeaula_fk_idx` (`escola_id` ASC) VISIBLE,
    CONSTRAINT `escola_planodeaula_fk`
      FOREIGN KEY (`escola_id`)
      REFERENCES `domingodelazer`.`escola` (`id`)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION);

CREATE TABLE `domingodelazer`.`planoaulaserie` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `planoaula_id` INT NOT NULL,
  `serie_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_serie_planoaula_idx` (`planoaula_id` ASC) VISIBLE,
  INDEX `fk_planoaula_serie_idx` (`serie_id` ASC) VISIBLE,
  CONSTRAINT `fk_serie_planoaula`
    FOREIGN KEY (`planoaula_id`)
    REFERENCES `domingodelazer`.`planoaula` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_planoaula_serie`
    FOREIGN KEY (`serie_id`)
    REFERENCES `domingodelazer`.`serie` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

