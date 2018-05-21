--------------------------------------------------------
------------- Table TipoPista --------------------------
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS TipoPista (
  id INT NOT NULL UNIQUE,
  descricao VARCHAR(30) NULL,
  PRIMARY KEY (idevento));
  
  
--------------------------------------------------------
------------- Table TipoEnvolvido ----------------------
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS TipoEnvolvido (
  id INT NOT NULL UNIQUE,
  descricao VARCHAR(30) NULL,
  PRIMARY KEY (idevento));

  
--------------------------------------------------------
------------- Table TipoVeiculo ------------------------
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS TipoVeiculo (
  id INT NOT NULL UNIQUE,
  descricao VARCHAR(30) NULL,
  PRIMARY KEY (idevento));

  
--------------------------------------------------------
------------- Table TipoAcidente -----------------------
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS TipoAcidente (
  id INT NOT NULL UNIQUE,
  descricao VARCHAR(30) NULL,
  PRIMARY KEY (idevento));

-- Table: public.acidente

CREATE TABLE public.acidente
(
  pessoa numeric(11,0) NOT NULL,
  veiculo integer NOT NULL,
  data date NOT NULL,
  horario time without time zone NOT NULL,
  ocorrencia integer NOT NULL,
  tipo_acidente integer NOT NULL,
  tipo_envolvido integer NOT NULL,
  situacao_pessoa integer NOT NULL,
  local_atendimento integer,
  CONSTRAINT acidente_pkey PRIMARY KEY (pessoa, veiculo, data, horario),
  CONSTRAINT acidente_local_atendimento_f_key FOREIGN KEY (local_atendimento)
      REFERENCES public.local_atendimento (id_local_atendimento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acidente_ocorrencia_f_key FOREIGN KEY (ocorrencia)
      REFERENCES public.ocorrencia (id_ocorrencia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acidente_pessoa_f_key FOREIGN KEY (pessoa)
      REFERENCES public.pessoa (cpf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acidente_situacao_pessoa_f_key FOREIGN KEY (situacao_pessoa)
      REFERENCES public.situacao_pessoa (id_situacao_pessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acidente_tipo_acidente_f_key FOREIGN KEY (tipo_acidente)
      REFERENCES public.tipo_acidente (id_tipo_acidente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acidente_tipo_envolvido_f_key FOREIGN KEY (tipo_envolvido)
      REFERENCES public.tipo_envolvido (id_tipo_envolvido) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT acidente_veiculo_f_key FOREIGN KEY (veiculo)
      REFERENCES public.veiculo (id_veiculo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


-- Table: public.condicoes_pista

CREATE TABLE public.condicoes_pista
(
  id_condicoes integer NOT NULL,
  descricao character varying,
  CONSTRAINT condicoes_pista_pkey PRIMARY KEY (id_condicoes),
  CONSTRAINT condicoes_pista_descricao_key UNIQUE (descricao),
  CONSTRAINT condicoes_pista_id_condicoes_check CHECK (id_condicoes > 0) NOT VALID
)


-- Table: public.encaminhamento

CREATE TABLE public.encaminhamento
(
  id_encaminhamento integer NOT NULL,
  pessoa numeric(11,0) NOT NULL,
  local_atendimento integer NOT NULL,
  endereco character varying(30),
  descricao character varying(30) NOT NULL,
  CONSTRAINT encaminhamento_pkey PRIMARY KEY (id_encaminhamento),
  CONSTRAINT encaminhamento_local_atendimento_f_key FOREIGN KEY (local_atendimento)
      REFERENCES public.local_atendimento (id_local_atendimento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT encaminhamento_pessoa_f_key FOREIGN KEY (pessoa)
      REFERENCES public.pessoa (cpf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


-- Table: public.escala

CREATE TABLE public.escala
(
  escala_id integer NOT NULL,
  data_inicio date NOT NULL,
  data_fim date NOT NULL,
  hora_inicio time without time zone,
  hora_fim time without time zone,
  CONSTRAINT escala_id_key PRIMARY KEY (escala_id),
  CONSTRAINT "dataInicio<=dataFim" CHECK (data_inicio < data_fim OR data_inicio = data_fim),
  CONSTRAINT escala_escala_id_check CHECK (escala_id > 0) NOT VALID
)


-- Table: public.escala_policial

CREATE TABLE public.escala_policial
(
  id_escala_policial integer NOT NULL,
  policial numeric(11,0) NOT NULL,
  escala integer NOT NULL,
  CONSTRAINT escala_policial_pkey PRIMARY KEY (id_escala_policial),
  CONSTRAINT escala_policial_escala_f_key FOREIGN KEY (escala)
      REFERENCES public.escala (escala_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT escala_policial_policial_f_key FOREIGN KEY (policial)
      REFERENCES public.policial (cpf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT escala_policial_id_escala_policial_check CHECK (id_escala_policial > 0) NOT VALID
)


-- Table: public.escala_policial

CREATE TABLE public.escala_policial
(
  id_escala_policial integer NOT NULL,
  policial numeric(11,0) NOT NULL,
  escala integer NOT NULL,
  CONSTRAINT escala_policial_pkey PRIMARY KEY (id_escala_policial),
  CONSTRAINT escala_policial_escala_f_key FOREIGN KEY (escala)
      REFERENCES public.escala (escala_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT escala_policial_policial_f_key FOREIGN KEY (policial)
      REFERENCES public.policial (cpf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT escala_policial_id_escala_policial_check CHECK (id_escala_policial > 0) NOT VALID
)


-- Table: public.local

CREATE TABLE public.local
(
  id_local integer NOT NULL DEFAULT nextval('seq_id_local'::regclass),
  km character varying(6) NOT NULL,
  rodovia integer NOT NULL,
  "condicaoPista" integer NOT NULL,
  "tipoPista" integer NOT NULL,
  CONSTRAINT id_local_pkey PRIMARY KEY (id_local),
  CONSTRAINT "condicaoPista_f_key" FOREIGN KEY ("condicaoPista")
      REFERENCES public.condicoes_pista (id_condicoes) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT rodovia_f_key FOREIGN KEY (rodovia)
      REFERENCES public.rodovia (id_rodovia) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "tipoPista_f_key" FOREIGN KEY ("tipoPista")
      REFERENCES public.tipo_pista (id_tipo_pista) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT local_id_local_check CHECK (id_local > 0) NOT VALID
)


-- Table: public.local_atendimento

CREATE TABLE public.local_atendimento
(
  id_local_atendimento integer NOT NULL,
  endereco character varying(30),
  descricao character varying(30) NOT NULL,
  CONSTRAINT local_atendimento_pkey PRIMARY KEY (id_local_atendimento),
  CONSTRAINT local_atendimento_descricao_key UNIQUE (descricao)
)


-- Table: public.ocorrencia

CREATE TABLE public.ocorrencia
(
  id_ocorrencia integer NOT NULL DEFAULT nextval('seq_id_ocorrencia'::regclass),
  descricao character varying(255),
  data date,
  horario time without time zone,
  local integer NOT NULL,
  policial numeric(11,0) NOT NULL,
  data_acidente date,
  horario_acidente time without time zone,
  CONSTRAINT ocorrencia_pkey PRIMARY KEY (id_ocorrencia),
  CONSTRAINT ocorrencia_local_f_key FOREIGN KEY (local)
      REFERENCES public.local (id_local) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ocorrencia_policial_f_key FOREIGN KEY (policial)
      REFERENCES public.policial (cpf) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


-- Table: public.pessoa

CREATE TABLE public.pessoa
(
  cpf numeric(11,0) NOT NULL,
  nome character varying(30),
  situacao_pessoa integer NOT NULL,
  local_atendimento integer NOT NULL,
  CONSTRAINT pessoa_pkey PRIMARY KEY (cpf),
  CONSTRAINT pessoa_local_atendimento_f_key FOREIGN KEY (local_atendimento)
      REFERENCES public.local_atendimento (id_local_atendimento) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pessoa_situacao_pessoa_f_key FOREIGN KEY (situacao_pessoa)
      REFERENCES public.situacao_pessoa (id_situacao_pessoa) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)


-- Table: public.policial

CREATE TABLE public.policial
(
  cpf numeric(11,0) NOT NULL,
  nome character varying(30) NOT NULL,
  CONSTRAINT policial_pkey PRIMARY KEY (cpf)
)


-- Table: public.rodovia;

CREATE TABLE public.rodovia
(
  id_rodovia integer NOT NULL DEFAULT nextval('seq_id_rodovia'::regclass),
  descricao character varying(30),
  CONSTRAINT rodovia_pkey PRIMARY KEY (id_rodovia),
  CONSTRAINT rodovia_descricao_key UNIQUE (descricao),
  CONSTRAINT rodovia_id_rodovia_check CHECK (id_rodovia > 0) NOT VALID
)


-- Table: public.situacao_pessoa

CREATE TABLE public.situacao_pessoa
(
  id_situacao_pessoa integer NOT NULL,
  descricao character varying(30) NOT NULL,
  CONSTRAINT situacao_pessoa_pkey PRIMARY KEY (id_situacao_pessoa),
  CONSTRAINT situacao_pessoa_descricao_key UNIQUE (descricao)
)


-- Table: public.tipo_acidente

CREATE TABLE public.tipo_acidente
(
  id_tipo_acidente integer NOT NULL DEFAULT nextval('seq_id_tipo_acidente'::regclass),
  descricao character varying(30),
  CONSTRAINT tipo_acidente_pkey PRIMARY KEY (id_tipo_acidente),
  CONSTRAINT tipo_acidente_descricao_key UNIQUE (descricao)
)


-- Table: public.tipo_envolvido

CREATE TABLE public.tipo_envolvido
(
  id_tipo_envolvido integer NOT NULL DEFAULT nextval('seq_id_tipo_envolvido'::regclass),
  descricao character varying(30),
  CONSTRAINT tipo_envolvido_pkey PRIMARY KEY (id_tipo_envolvido),
  CONSTRAINT tipo_envolvido_descricao_key UNIQUE (descricao)
)


-- Table: public.tipo_pista

CREATE TABLE public.tipo_pista
(
  id_tipo_pista integer NOT NULL DEFAULT nextval('seq_id_tipo_pista'::regclass),
  descricao character varying(30),
  CONSTRAINT tipo_pista_pkey PRIMARY KEY (id_tipo_pista),
  CONSTRAINT tipo_pista_descricao_key UNIQUE (descricao),
  CONSTRAINT tipo_pista_id_tipo_pista_check CHECK (id_tipo_pista > 0) NOT VALID
)


-- Table: public.tipo_veiculo

CREATE TABLE public.tipo_veiculo
(
  id_tipo_veiculo integer NOT NULL DEFAULT nextval('seq_id_tipo_veiculo'::regclass),
  descricao character varying(30),
  CONSTRAINT tipo_veiculo_pkey PRIMARY KEY (id_tipo_veiculo),
  CONSTRAINT tipo_veiculo_descricao_key UNIQUE (descricao)
)


-- Table: public.veiculo

CREATE TABLE public.veiculo
(
  id_veiculo integer NOT NULL DEFAULT nextval('seq_id_veiculo'::regclass),
  descricao character varying(30) NOT NULL,
  tipo integer NOT NULL,
  CONSTRAINT veiculo_pkey PRIMARY KEY (id_veiculo),
  CONSTRAINT veiculo_tipo_f_key FOREIGN KEY (tipo)
      REFERENCES public.tipo_veiculo (id_tipo_veiculo) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

-- Started on 2016-12-05 17:10:35 BRST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2334 (class 1262 OID 16385)
-- Name: ProjetoBD; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "ProjetoBD" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Portuguese_Brazil.1252' LC_CTYPE = 'Portuguese_Brazil.1252';


ALTER DATABASE "ProjetoBD" OWNER TO postgres;

\connect "ProjetoBD"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2336 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 207 (class 1259 OID 24808)
-- Name: acidente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE acidente (
    pessoa numeric(11,0) NOT NULL,
    veiculo integer NOT NULL,
    data date NOT NULL,
    horario time without time zone NOT NULL,
    ocorrencia integer NOT NULL,
    tipo_acidente integer NOT NULL,
    tipo_envolvido integer NOT NULL,
    situacao_pessoa integer NOT NULL,
    local_atendimento integer
);


ALTER TABLE acidente OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 16469)
-- Name: condicoes_pista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE condicoes_pista (
    id_condicoes integer NOT NULL,
    descricao character varying
);


ALTER TABLE condicoes_pista OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 24776)
-- Name: encaminhamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE encaminhamento (
    id_encaminhamento integer NOT NULL,
    pessoa numeric(11,0) NOT NULL,
    local_atendimento integer NOT NULL,
    endereco character varying(30),
    descricao character varying(30) NOT NULL
);


ALTER TABLE encaminhamento OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 24754)
-- Name: escala; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE escala (
    escala_id integer NOT NULL,
    data_inicio date NOT NULL,
    data_fim date NOT NULL,
    hora_inicio time without time zone,
    hora_fim time without time zone,
    CONSTRAINT "dataInicio<=dataFim" CHECK (((data_inicio < data_fim) OR (data_inicio = data_fim)))
);


ALTER TABLE escala OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 24793)
-- Name: escala_policial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE escala_policial (
    id_escala_policial integer NOT NULL,
    policial numeric(11,0) NOT NULL,
    escala integer NOT NULL
);


ALTER TABLE escala_policial OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 24956)
-- Name: seq_id_ocorrencia; Type: SEQUENCE; Schema: public; Owner: users
--

CREATE SEQUENCE seq_id_ocorrencia
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_id_ocorrencia OWNER TO users;

--
-- TOC entry 204 (class 1259 OID 24761)
-- Name: ocorrencia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE ocorrencia (
    id_ocorrencia integer DEFAULT nextval('seq_id_ocorrencia'::regclass) NOT NULL,
    descricao character varying(255),
    data date,
    horario time without time zone,
    local integer NOT NULL,
    policial numeric(11,0) NOT NULL,
    data_acidente date,
    horario_acidente time without time zone
);


ALTER TABLE ocorrencia OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 24988)
-- Name: info_ocorrencias_pista_view2; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW info_ocorrencias_pista_view2 AS
 SELECT ocorrencia.id_ocorrencia,
    ocorrencia.data,
    ocorrencia.horario,
    ocorrencia.data_acidente,
    ocorrencia.horario_acidente,
    ocorrencia.descricao,
    ( SELECT count(*) AS count
           FROM acidente
          WHERE (acidente.ocorrencia = ocorrencia.id_ocorrencia)) AS count
   FROM ocorrencia ocorrencia;


ALTER TABLE info_ocorrencias_pista_view2 OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 24982)
-- Name: seq_id_local; Type: SEQUENCE; Schema: public; Owner: users
--

CREATE SEQUENCE seq_id_local
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_id_local OWNER TO users;

--
-- TOC entry 197 (class 1259 OID 24651)
-- Name: local; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE local (
    id_local integer DEFAULT nextval('seq_id_local'::regclass) NOT NULL,
    km character varying(6) NOT NULL,
    rodovia integer NOT NULL,
    "condicaoPista" integer NOT NULL,
    "tipoPista" integer NOT NULL
);


ALTER TABLE local OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 24959)
-- Name: info_pista_view3; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW info_pista_view3 AS
 SELECT local.id_local,
    local.km,
    local.rodovia,
    local."condicaoPista",
    local."tipoPista",
    tab_ocorrencias.tipo_acidente
   FROM (local
     JOIN ( SELECT ocorrencia.local,
            acidente.tipo_acidente
           FROM (ocorrencia
             JOIN acidente ON ((acidente.ocorrencia = ocorrencia.id_ocorrencia)))) tab_ocorrencias ON ((tab_ocorrencias.local = local.id_local)));


ALTER TABLE info_pista_view3 OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 24729)
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pessoa (
    cpf numeric(11,0) NOT NULL,
    nome character varying(30),
    situacao_pessoa integer NOT NULL,
    local_atendimento integer NOT NULL
);


ALTER TABLE pessoa OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 24915)
-- Name: info_vitima_local_view2; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW info_vitima_local_view2 AS
 SELECT pessoa.cpf,
    pessoa.nome,
    pessoa.situacao_pessoa,
    pessoa.local_atendimento
   FROM (pessoa
     JOIN acidente ON ((pessoa.cpf = acidente.pessoa)));


ALTER TABLE info_vitima_local_view2 OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 24710)
-- Name: local_atendimento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE local_atendimento (
    id_local_atendimento integer NOT NULL,
    endereco character varying(30),
    descricao character varying(30) NOT NULL
);


ALTER TABLE local_atendimento OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 24749)
-- Name: policial; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE policial (
    cpf numeric(11,0) NOT NULL,
    nome character varying(30) NOT NULL
);


ALTER TABLE policial OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 16495)
-- Name: seq_id_rodovia; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id_rodovia
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE seq_id_rodovia OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 16459)
-- Name: rodovia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE rodovia (
    id_rodovia integer DEFAULT nextval('seq_id_rodovia'::regclass) NOT NULL,
    descricao character varying(30)
);


ALTER TABLE rodovia OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16497)
-- Name: seq_id_condicoes_pista; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id_condicoes_pista
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE seq_id_condicoes_pista OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 16449)
-- Name: seq_id_tipo_acidente; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id_tipo_acidente
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE seq_id_tipo_acidente OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 16429)
-- Name: seq_id_tipo_envolvido; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id_tipo_envolvido
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE seq_id_tipo_envolvido OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 16419)
-- Name: seq_id_tipo_pista; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id_tipo_pista
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE seq_id_tipo_pista OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 16439)
-- Name: seq_id_tipo_veiculo; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id_tipo_veiculo
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    MAXVALUE 999999999999999999
    CACHE 1;


ALTER TABLE seq_id_tipo_veiculo OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 24985)
-- Name: seq_id_veiculo; Type: SEQUENCE; Schema: public; Owner: users
--

CREATE SEQUENCE seq_id_veiculo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE seq_id_veiculo OWNER TO users;

--
-- TOC entry 198 (class 1259 OID 24703)
-- Name: situacao_pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE situacao_pessoa (
    id_situacao_pessoa integer NOT NULL,
    descricao character varying(30) NOT NULL
);


ALTER TABLE situacao_pessoa OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 16451)
-- Name: tipo_acidente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tipo_acidente (
    id_tipo_acidente integer DEFAULT nextval('seq_id_tipo_acidente'::regclass) NOT NULL,
    descricao character varying(30)
);


ALTER TABLE tipo_acidente OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 16431)
-- Name: tipo_envolvido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tipo_envolvido (
    id_tipo_envolvido integer DEFAULT nextval('seq_id_tipo_envolvido'::regclass) NOT NULL,
    descricao character varying(30)
);


ALTER TABLE tipo_envolvido OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 16421)
-- Name: tipo_pista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tipo_pista (
    id_tipo_pista integer DEFAULT nextval('seq_id_tipo_pista'::regclass) NOT NULL,
    descricao character varying(30)
);


ALTER TABLE tipo_pista OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 16441)
-- Name: tipo_veiculo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE tipo_veiculo (
    id_tipo_veiculo integer DEFAULT nextval('seq_id_tipo_veiculo'::regclass) NOT NULL,
    descricao character varying(30)
);


ALTER TABLE tipo_veiculo OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 24717)
-- Name: veiculo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE veiculo (
    id_veiculo integer DEFAULT nextval('seq_id_veiculo'::regclass) NOT NULL,
    descricao character varying(30) NOT NULL,
    tipo integer NOT NULL
);


ALTER TABLE veiculo OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 24997)
-- Name: view1; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW view1 AS
 SELECT pessoa.nome,
    tab1.pessoa,
    tab1.veiculo,
    tab1.id_ocorrencia,
    tab1.descricao,
    tab1.data,
    tab1.horario,
    tab1.local,
    tab1.policial,
    tab1.data_acidente,
    tab1.horario_acidente
   FROM (pessoa
     JOIN ( SELECT acidente.pessoa,
            acidente.veiculo,
            ocorrencia.id_ocorrencia,
            ocorrencia.descricao,
            ocorrencia.data,
            ocorrencia.horario,
            ocorrencia.local,
            ocorrencia.policial,
            ocorrencia.data_acidente,
            ocorrencia.horario_acidente
           FROM (ocorrencia
             JOIN acidente ON ((ocorrencia.id_ocorrencia = acidente.ocorrencia)))) tab1 ON ((pessoa.cpf = tab1.pessoa)));


ALTER TABLE view1 OWNER TO postgres;

--
-- TOC entry 2326 (class 0 OID 24808)
-- Dependencies: 207
-- Data for Name: acidente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO acidente VALUES (4564689, 4, '2016-11-01', '22:31:00', 6, 1, 1, 1, 1);
INSERT INTO acidente VALUES (123123, 7, '2016-11-01', '00:37:16', 7, 1, 1, 1, 1);
INSERT INTO acidente VALUES (666, 8, '2016-11-01', '00:37:16', 7, 1, 1, 1, 1);
INSERT INTO acidente VALUES (7777777, 9, '2016-11-01', '00:37:16', 7, 1, 1, 1, 1);
INSERT INTO acidente VALUES (4444, 10, '2016-11-01', '00:37:16', 7, 1, 1, 1, 1);
INSERT INTO acidente VALUES (55555, 11, '2016-11-01', '00:41:24', 8, 1, 1, 1, 1);
INSERT INTO acidente VALUES (77777777, 12, '2016-11-01', '00:41:24', 8, 1, 2, 3, 1);
INSERT INTO acidente VALUES (12312, 13, '2016-11-09', '00:43:41', 9, 1, 1, 1, 1);
INSERT INTO acidente VALUES (66666666666, 14, '2016-11-09', '01:03:57', 11, 1, 1, 1, 1);
INSERT INTO acidente VALUES (77777777777, 15, '2016-11-16', '01:04:14', 12, 1, 2, 1, 2);
INSERT INTO acidente VALUES (77777777777, 17, '2016-11-17', '01:11:05', 16, 2, 1, 4, 3);
INSERT INTO acidente VALUES (77777777777, 19, '2016-11-03', '01:12:59', 18, 1, 1, 1, 1);
INSERT INTO acidente VALUES (55666666666, 20, '2016-11-17', '10:50:00', 19, 2, 3, 3, 4);
INSERT INTO acidente VALUES (77676767676, 21, '2016-11-17', '11:51:02', 20, 1, 1, 1, 1);
INSERT INTO acidente VALUES (76767673333, 22, '2016-11-17', '11:51:02', 20, 1, 2, 1, 3);
INSERT INTO acidente VALUES (87874384738, 24, '2016-11-15', '11:54:33', 21, 1, 1, 1, 1);
INSERT INTO acidente VALUES (76767644564, 25, '2016-11-15', '11:54:33', 21, 2, 3, 4, 3);
INSERT INTO acidente VALUES (12767676767, 26, '2016-11-15', '11:54:33', 21, 1, 1, 1, 1);
INSERT INTO acidente VALUES (11111113123, 27, '2016-11-03', '11:59:55', 22, 1, 1, 1, 1);
INSERT INTO acidente VALUES (83821893927, 28, '2016-11-03', '11:59:55', 22, 1, 1, 1, 1);
INSERT INTO acidente VALUES (99999999999, 29, '2016-11-09', '15:27:00', 23, 3, 1, 3, 2);
INSERT INTO acidente VALUES (94823842094, 30, '2016-11-17', '15:36:00', 24, 1, 1, 2, 4);
INSERT INTO acidente VALUES (84293482034, 31, '2016-11-17', '15:36:00', 24, 1, 1, 1, 1);
INSERT INTO acidente VALUES (22222222222, 32, '2016-11-17', '19:14:18', 26, 1, 1, 1, 1);


--
-- TOC entry 2313 (class 0 OID 16469)
-- Dependencies: 194
-- Data for Name: condicoes_pista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO condicoes_pista VALUES (1, 'Seca');
INSERT INTO condicoes_pista VALUES (2, 'Molhada');


--
-- TOC entry 2324 (class 0 OID 24776)
-- Dependencies: 205
-- Data for Name: encaminhamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2322 (class 0 OID 24754)
-- Dependencies: 203
-- Data for Name: escala; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2325 (class 0 OID 24793)
-- Dependencies: 206
-- Data for Name: escala_policial; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2316 (class 0 OID 24651)
-- Dependencies: 197
-- Data for Name: local; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO local VALUES (1, '1111', 4, 1, 1);
INSERT INTO local VALUES (2, '1231', 5, 1, 1);
INSERT INTO local VALUES (3, '123', 6, 2, 2);
INSERT INTO local VALUES (4, '123', 7, 2, 2);
INSERT INTO local VALUES (5, '123', 0, 2, 3);
INSERT INTO local VALUES (6, '123', 8, 1, 1);
INSERT INTO local VALUES (7, 'asda', 9, 1, 1);
INSERT INTO local VALUES (8, '123', 10, 1, 1);
INSERT INTO local VALUES (9, '123', 11, 1, 1);
INSERT INTO local VALUES (10, '1231', 10, 1, 1);
INSERT INTO local VALUES (11, '123', 11, 1, 1);
INSERT INTO local VALUES (12, '123', 12, 1, 1);
INSERT INTO local VALUES (13, '3123', 12, 2, 3);
INSERT INTO local VALUES (14, '4123', 13, 1, 1);
INSERT INTO local VALUES (15, '4123', 13, 1, 1);
INSERT INTO local VALUES (16, '1231', 14, 1, 1);
INSERT INTO local VALUES (17, '3456', 15, 1, 1);
INSERT INTO local VALUES (18, '1312', 12, 1, 1);
INSERT INTO local VALUES (19, '31', 16, 1, 1);
INSERT INTO local VALUES (20, '123', 17, 2, 2);
INSERT INTO local VALUES (21, '444', 18, 1, 1);
INSERT INTO local VALUES (22, '123', 11, 1, 2);
INSERT INTO local VALUES (23, '123', 18, 1, 1);
INSERT INTO local VALUES (24, '123', 19, 2, 2);
INSERT INTO local VALUES (25, '1231', 0, 2, 3);
INSERT INTO local VALUES (26, '', 20, 1, 1);
INSERT INTO local VALUES (27, '6', 21, 1, 1);


--
-- TOC entry 2318 (class 0 OID 24710)
-- Dependencies: 199
-- Data for Name: local_atendimento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO local_atendimento VALUES (1, 'endereco 1', 'local 1');
INSERT INTO local_atendimento VALUES (2, 'endereco 2', 'local 2');
INSERT INTO local_atendimento VALUES (3, 'endereco 3', 'local 3');
INSERT INTO local_atendimento VALUES (4, 'sem endereco', 'nenhum');


--
-- TOC entry 2323 (class 0 OID 24761)
-- Dependencies: 204
-- Data for Name: ocorrencia; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO ocorrencia VALUES (4, 'asdadada', '2016-11-17', '00:14:44', 4, 11111111111, '2016-11-01', '22:14:00');
INSERT INTO ocorrencia VALUES (5, 'asdasda', '2016-11-17', '00:30:21', 6, 1, '2016-11-01', '00:29:57');
INSERT INTO ocorrencia VALUES (6, 'asdasdsadsada', '2016-11-17', '00:31:46', 7, 1, '2016-11-01', '22:31:00');
INSERT INTO ocorrencia VALUES (7, 'asdasdad', '2016-11-17', '00:37:46', 8, 1, '2016-11-01', '00:37:16');
INSERT INTO ocorrencia VALUES (8, 'aaaaaaaa', '2016-11-17', '00:41:48', 9, 1, '2016-11-01', '00:41:24');
INSERT INTO ocorrencia VALUES (9, 'asdasda', '2016-11-17', '00:43:52', 10, 1, '2016-11-09', '00:43:41');
INSERT INTO ocorrencia VALUES (10, 'asdasd', '2016-11-17', '00:46:04', 11, 1, '2016-11-01', '00:45:55');
INSERT INTO ocorrencia VALUES (11, 'asdasda', '2016-11-17', '01:04:11', 12, 11111111111, '2016-11-09', '01:03:57');
INSERT INTO ocorrencia VALUES (12, 'asdasda', '2016-11-17', '01:04:32', 13, 11111111111, '2016-11-16', '01:04:14');
INSERT INTO ocorrencia VALUES (13, 'dfhdfg', '2016-11-17', '01:05:05', 14, 11111111111, '2016-11-02', '01:04:35');
INSERT INTO ocorrencia VALUES (14, 'dfhdfg', '2016-11-17', '01:05:20', 15, 11111111111, '2016-11-02', '01:04:35');
INSERT INTO ocorrencia VALUES (15, 'asdada', '2016-11-17', '01:10:01', 16, 11111111111, '2016-11-09', '01:09:50');
INSERT INTO ocorrencia VALUES (16, 'gjryujyrtuj', '2016-11-17', '01:11:21', 17, 11111111111, '2016-11-17', '01:11:05');
INSERT INTO ocorrencia VALUES (17, 'asdasdas', '2016-11-17', '01:12:20', 18, 11111111111, '2016-11-15', '01:12:07');
INSERT INTO ocorrencia VALUES (18, 'asdasdas', '2016-11-17', '01:13:14', 19, 11111111111, '2016-11-03', '01:12:59');
INSERT INTO ocorrencia VALUES (19, 'asdada', '2016-11-17', '11:50:58', 20, 11111111111, '2016-11-17', '10:50:00');
INSERT INTO ocorrencia VALUES (20, 'bbbbbbbb', '2016-11-17', '11:51:55', 21, 11111111111, '2016-11-17', '11:51:02');
INSERT INTO ocorrencia VALUES (21, 'asdasda', '2016-11-17', '11:55:11', 22, 11111111111, '2016-11-15', '11:54:33');
INSERT INTO ocorrencia VALUES (22, 'asdasdsa', '2016-11-17', '12:00:22', 23, 11111111111, '2016-11-03', '11:59:55');
INSERT INTO ocorrencia VALUES (23, 'hdiaushduia	', '2016-11-17', '18:30:01', 24, 11111111111, '2016-11-09', '15:27:00');
INSERT INTO ocorrencia VALUES (24, 'shaiushia', '2016-11-17', '18:37:28', 25, 11111111111, '2016-11-17', '15:36:00');
INSERT INTO ocorrencia VALUES (25, '', '2016-11-17', '18:48:06', 26, 11111111111, '2016-11-17', '18:46:42');
INSERT INTO ocorrencia VALUES (26, 'XXCFSD', '2016-11-17', '19:15:00', 27, 11111111111, '2016-11-17', '19:14:18');


--
-- TOC entry 2320 (class 0 OID 24729)
-- Dependencies: 201
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pessoa VALUES (123123, 'asdasda', 3, 1);
INSERT INTO pessoa VALUES (4564689, 'aaaaaaaa', 1, 1);
INSERT INTO pessoa VALUES (33333, 'bbbbb', 1, 1);
INSERT INTO pessoa VALUES (444444, 'ffffffff', 1, 1);
INSERT INTO pessoa VALUES (666, 'nnnn', 1, 1);
INSERT INTO pessoa VALUES (7777777, 'bbbbbbb', 1, 1);
INSERT INTO pessoa VALUES (4444, 'ccccc', 1, 1);
INSERT INTO pessoa VALUES (55555, 'aaaaa', 1, 1);
INSERT INTO pessoa VALUES (77777777, 'htyhtyhtyh', 3, 1);
INSERT INTO pessoa VALUES (12312, 'asda', 1, 1);
INSERT INTO pessoa VALUES (66666666666, 'asdasda', 1, 1);
INSERT INTO pessoa VALUES (11111111111, 'aaaaa', 1, 1);
INSERT INTO pessoa VALUES (77777777777, '45674756', 1, 1);
INSERT INTO pessoa VALUES (55666666666, 'teste', 3, 4);
INSERT INTO pessoa VALUES (77676767676, 'asdasd', 1, 1);
INSERT INTO pessoa VALUES (76767673333, 'fulano', 1, 3);
INSERT INTO pessoa VALUES (63434444444, 'ffffffffff', 1, 1);
INSERT INTO pessoa VALUES (87874384738, 'dddddd', 1, 1);
INSERT INTO pessoa VALUES (76767644564, 'teste', 4, 3);
INSERT INTO pessoa VALUES (12767676767, 'asdasd', 1, 1);
INSERT INTO pessoa VALUES (11111113123, 'hhh', 1, 1);
INSERT INTO pessoa VALUES (83821893927, 'ashskdh', 1, 1);
INSERT INTO pessoa VALUES (99999999999, 'pedro', 3, 2);
INSERT INTO pessoa VALUES (94823842094, 'hiduai', 2, 4);
INSERT INTO pessoa VALUES (84293482034, 'hdisdjaoi', 1, 1);
INSERT INTO pessoa VALUES (22222222222, 'LEONARDO', 1, 1);


--
-- TOC entry 2321 (class 0 OID 24749)
-- Dependencies: 202
-- Data for Name: policial; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO policial VALUES (11111111111, 'teste');
INSERT INTO policial VALUES (1, 'policial teste');


--
-- TOC entry 2312 (class 0 OID 16459)
-- Dependencies: 193
-- Data for Name: rodovia; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO rodovia VALUES (0, 'br-101');
INSERT INTO rodovia VALUES (4, 'BR-282');
INSERT INTO rodovia VALUES (5, 'BR-470');
INSERT INTO rodovia VALUES (6, 'BR-280');
INSERT INTO rodovia VALUES (7, 'BR-116');
INSERT INTO rodovia VALUES (8, 'BR-153');
INSERT INTO rodovia VALUES (9, 'BR-283');
INSERT INTO rodovia VALUES (10, 'SC-301');
INSERT INTO rodovia VALUES (11, 'SC-403');
INSERT INTO rodovia VALUES (12, 'SC-400');
INSERT INTO rodovia VALUES (13, 'SC-401');
INSERT INTO rodovia VALUES (14, 'SC-402');
INSERT INTO rodovia VALUES (15, 'SC-404');
INSERT INTO rodovia VALUES (16, 'SC-405');
INSERT INTO rodovia VALUES (17, 'SC-406');
INSERT INTO rodovia VALUES (18, 'SC-407');
INSERT INTO rodovia VALUES (19, 'SC-408');
INSERT INTO rodovia VALUES (20, 'SC-409');
INSERT INTO rodovia VALUES (21, 'BR-118');


--
-- TOC entry 2337 (class 0 OID 0)
-- Dependencies: 196
-- Name: seq_id_condicoes_pista; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id_condicoes_pista', 0, false);


--
-- TOC entry 2338 (class 0 OID 0)
-- Dependencies: 211
-- Name: seq_id_local; Type: SEQUENCE SET; Schema: public; Owner: users
--

SELECT pg_catalog.setval('seq_id_local', 27, true);


--
-- TOC entry 2339 (class 0 OID 0)
-- Dependencies: 209
-- Name: seq_id_ocorrencia; Type: SEQUENCE SET; Schema: public; Owner: users
--

SELECT pg_catalog.setval('seq_id_ocorrencia', 26, true);


--
-- TOC entry 2340 (class 0 OID 0)
-- Dependencies: 195
-- Name: seq_id_rodovia; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id_rodovia', 21, true);


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 191
-- Name: seq_id_tipo_acidente; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id_tipo_acidente', 0, false);


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 187
-- Name: seq_id_tipo_envolvido; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id_tipo_envolvido', 0, false);


--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 185
-- Name: seq_id_tipo_pista; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id_tipo_pista', 2, true);


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 189
-- Name: seq_id_tipo_veiculo; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id_tipo_veiculo', 0, false);


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 212
-- Name: seq_id_veiculo; Type: SEQUENCE SET; Schema: public; Owner: users
--

SELECT pg_catalog.setval('seq_id_veiculo', 32, true);


--
-- TOC entry 2317 (class 0 OID 24703)
-- Dependencies: 198
-- Data for Name: situacao_pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO situacao_pessoa VALUES (1, 'Ilesa');
INSERT INTO situacao_pessoa VALUES (2, 'Ferida');
INSERT INTO situacao_pessoa VALUES (3, 'Gravemente ferida');
INSERT INTO situacao_pessoa VALUES (4, 'Morta');


--
-- TOC entry 2311 (class 0 OID 16451)
-- Dependencies: 192
-- Data for Name: tipo_acidente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipo_acidente VALUES (4, 'CAPOTAMENTO');
INSERT INTO tipo_acidente VALUES (5, 'COLISÃO TRASEIRA');
INSERT INTO tipo_acidente VALUES (1, 'ATROPELAMENTO');
INSERT INTO tipo_acidente VALUES (2, 'COLISÃO FRONTAL');
INSERT INTO tipo_acidente VALUES (3, 'ENGAVETAMENTO');


--
-- TOC entry 2307 (class 0 OID 16431)
-- Dependencies: 188
-- Data for Name: tipo_envolvido; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipo_envolvido VALUES (1, 'Motorista');
INSERT INTO tipo_envolvido VALUES (2, 'Passageiro');
INSERT INTO tipo_envolvido VALUES (3, 'Pedestre');


--
-- TOC entry 2305 (class 0 OID 16421)
-- Dependencies: 186
-- Data for Name: tipo_pista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipo_pista VALUES (1, 'Pista de areia');
INSERT INTO tipo_pista VALUES (2, 'Pista de asfalto');
INSERT INTO tipo_pista VALUES (3, 'Pista de brita');


--
-- TOC entry 2309 (class 0 OID 16441)
-- Dependencies: 190
-- Data for Name: tipo_veiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipo_veiculo VALUES (1, 'Caminhão');
INSERT INTO tipo_veiculo VALUES (2, 'Carro');
INSERT INTO tipo_veiculo VALUES (3, 'Motocicleta');


--
-- TOC entry 2319 (class 0 OID 24717)
-- Dependencies: 200
-- Data for Name: veiculo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO veiculo VALUES (2, 'asdasdadsa', 1);
INSERT INTO veiculo VALUES (3, 'asdasda', 1);
INSERT INTO veiculo VALUES (4, 'aaaaa', 1);
INSERT INTO veiculo VALUES (5, 'aaaaaaa', 1);
INSERT INTO veiculo VALUES (6, 'ffffffffff', 1);
INSERT INTO veiculo VALUES (7, 'aaaaaaaaa', 1);
INSERT INTO veiculo VALUES (8, 'nnnnnnn', 1);
INSERT INTO veiculo VALUES (9, 'bbbbbb', 1);
INSERT INTO veiculo VALUES (10, 'cccccccc', 1);
INSERT INTO veiculo VALUES (11, 'aaaaaaaa', 1);
INSERT INTO veiculo VALUES (12, 'tyhythtyht', 3);
INSERT INTO veiculo VALUES (13, 'asda', 1);
INSERT INTO veiculo VALUES (14, 'asdada', 1);
INSERT INTO veiculo VALUES (15, '546745', 2);
INSERT INTO veiculo VALUES (16, 'jjjjjj', 1);
INSERT INTO veiculo VALUES (17, 'fasdfa', 3);
INSERT INTO veiculo VALUES (19, 'aaaa', 1);
INSERT INTO veiculo VALUES (20, 'dasda', 2);
INSERT INTO veiculo VALUES (21, 'asd', 1);
INSERT INTO veiculo VALUES (22, 'xxx', 3);
INSERT INTO veiculo VALUES (24, 'ddddddd', 1);
INSERT INTO veiculo VALUES (25, 'aaaaaaa', 2);
INSERT INTO veiculo VALUES (26, 'asdsa', 1);
INSERT INTO veiculo VALUES (27, 'asda', 1);
INSERT INTO veiculo VALUES (28, 'hdhskdhk', 1);
INSERT INTO veiculo VALUES (29, 'asdada', 2);
INSERT INTO veiculo VALUES (30, 'hiuhssahui', 1);
INSERT INTO veiculo VALUES (31, 'dhasiu', 1);
INSERT INTO veiculo VALUES (32, 'FIESTA', 2);


--
-- TOC entry 2163 (class 2606 OID 24812)
-- Name: acidente acidente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_pkey PRIMARY KEY (pessoa, veiculo, data, horario);


--
-- TOC entry 2135 (class 2606 OID 16494)
-- Name: condicoes_pista condicoes_pista_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY condicoes_pista
    ADD CONSTRAINT condicoes_pista_descricao_key UNIQUE (descricao);


--
-- TOC entry 2106 (class 2606 OID 24977)
-- Name: condicoes_pista condicoes_pista_id_condicoes_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE condicoes_pista
    ADD CONSTRAINT condicoes_pista_id_condicoes_check CHECK ((id_condicoes > 0)) NOT VALID;


--
-- TOC entry 2137 (class 2606 OID 16476)
-- Name: condicoes_pista condicoes_pista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY condicoes_pista
    ADD CONSTRAINT condicoes_pista_pkey PRIMARY KEY (id_condicoes);


--
-- TOC entry 2159 (class 2606 OID 24780)
-- Name: encaminhamento encaminhamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT encaminhamento_pkey PRIMARY KEY (id_encaminhamento);


--
-- TOC entry 2111 (class 2606 OID 24980)
-- Name: escala escala_escala_id_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE escala
    ADD CONSTRAINT escala_escala_id_check CHECK ((escala_id > 0)) NOT VALID;


--
-- TOC entry 2155 (class 2606 OID 24758)
-- Name: escala escala_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY escala
    ADD CONSTRAINT escala_id_key PRIMARY KEY (escala_id);


--
-- TOC entry 2113 (class 2606 OID 24981)
-- Name: escala_policial escala_policial_id_escala_policial_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE escala_policial
    ADD CONSTRAINT escala_policial_id_escala_policial_check CHECK ((id_escala_policial > 0)) NOT VALID;


--
-- TOC entry 2161 (class 2606 OID 24797)
-- Name: escala_policial escala_policial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY escala_policial
    ADD CONSTRAINT escala_policial_pkey PRIMARY KEY (id_escala_policial);


--
-- TOC entry 2139 (class 2606 OID 24672)
-- Name: local id_local_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY local
    ADD CONSTRAINT id_local_pkey PRIMARY KEY (id_local);


--
-- TOC entry 2145 (class 2606 OID 24716)
-- Name: local_atendimento local_atendimento_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY local_atendimento
    ADD CONSTRAINT local_atendimento_descricao_key UNIQUE (descricao);


--
-- TOC entry 2147 (class 2606 OID 24714)
-- Name: local_atendimento local_atendimento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY local_atendimento
    ADD CONSTRAINT local_atendimento_pkey PRIMARY KEY (id_local_atendimento);


--
-- TOC entry 2108 (class 2606 OID 24968)
-- Name: local local_id_local_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE local
    ADD CONSTRAINT local_id_local_check CHECK ((id_local > 0)) NOT VALID;


--
-- TOC entry 2157 (class 2606 OID 24765)
-- Name: ocorrencia ocorrencia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT ocorrencia_pkey PRIMARY KEY (id_ocorrencia);


--
-- TOC entry 2151 (class 2606 OID 24733)
-- Name: pessoa pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (cpf);


--
-- TOC entry 2153 (class 2606 OID 24753)
-- Name: policial policial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY policial
    ADD CONSTRAINT policial_pkey PRIMARY KEY (cpf);


--
-- TOC entry 2131 (class 2606 OID 24970)
-- Name: rodovia rodovia_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rodovia
    ADD CONSTRAINT rodovia_descricao_key UNIQUE (descricao);


--
-- TOC entry 2105 (class 2606 OID 24976)
-- Name: rodovia rodovia_id_rodovia_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE rodovia
    ADD CONSTRAINT rodovia_id_rodovia_check CHECK ((id_rodovia > 0)) NOT VALID;


--
-- TOC entry 2133 (class 2606 OID 16468)
-- Name: rodovia rodovia_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY rodovia
    ADD CONSTRAINT rodovia_pkey PRIMARY KEY (id_rodovia);


--
-- TOC entry 2141 (class 2606 OID 24709)
-- Name: situacao_pessoa situacao_pessoa_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY situacao_pessoa
    ADD CONSTRAINT situacao_pessoa_descricao_key UNIQUE (descricao);


--
-- TOC entry 2143 (class 2606 OID 24707)
-- Name: situacao_pessoa situacao_pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY situacao_pessoa
    ADD CONSTRAINT situacao_pessoa_pkey PRIMARY KEY (id_situacao_pessoa);


--
-- TOC entry 2127 (class 2606 OID 16458)
-- Name: tipo_acidente tipo_acidente_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_acidente
    ADD CONSTRAINT tipo_acidente_descricao_key UNIQUE (descricao);


--
-- TOC entry 2129 (class 2606 OID 16456)
-- Name: tipo_acidente tipo_acidente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_acidente
    ADD CONSTRAINT tipo_acidente_pkey PRIMARY KEY (id_tipo_acidente);


--
-- TOC entry 2119 (class 2606 OID 16438)
-- Name: tipo_envolvido tipo_envolvido_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_envolvido
    ADD CONSTRAINT tipo_envolvido_descricao_key UNIQUE (descricao);


--
-- TOC entry 2121 (class 2606 OID 16436)
-- Name: tipo_envolvido tipo_envolvido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_envolvido
    ADD CONSTRAINT tipo_envolvido_pkey PRIMARY KEY (id_tipo_envolvido);


--
-- TOC entry 2115 (class 2606 OID 16428)
-- Name: tipo_pista tipo_pista_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_pista
    ADD CONSTRAINT tipo_pista_descricao_key UNIQUE (descricao);


--
-- TOC entry 2100 (class 2606 OID 24978)
-- Name: tipo_pista tipo_pista_id_tipo_pista_check; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE tipo_pista
    ADD CONSTRAINT tipo_pista_id_tipo_pista_check CHECK ((id_tipo_pista > 0)) NOT VALID;


--
-- TOC entry 2117 (class 2606 OID 16426)
-- Name: tipo_pista tipo_pista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_pista
    ADD CONSTRAINT tipo_pista_pkey PRIMARY KEY (id_tipo_pista);


--
-- TOC entry 2123 (class 2606 OID 16448)
-- Name: tipo_veiculo tipo_veiculo_descricao_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_veiculo
    ADD CONSTRAINT tipo_veiculo_descricao_key UNIQUE (descricao);


--
-- TOC entry 2125 (class 2606 OID 16446)
-- Name: tipo_veiculo tipo_veiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipo_veiculo
    ADD CONSTRAINT tipo_veiculo_pkey PRIMARY KEY (id_tipo_veiculo);


--
-- TOC entry 2149 (class 2606 OID 24721)
-- Name: veiculo veiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY veiculo
    ADD CONSTRAINT veiculo_pkey PRIMARY KEY (id_veiculo);


--
-- TOC entry 2182 (class 2606 OID 24843)
-- Name: acidente acidente_local_atendimento_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_local_atendimento_f_key FOREIGN KEY (local_atendimento) REFERENCES local_atendimento(id_local_atendimento);


--
-- TOC entry 2178 (class 2606 OID 24823)
-- Name: acidente acidente_ocorrencia_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_ocorrencia_f_key FOREIGN KEY (ocorrencia) REFERENCES ocorrencia(id_ocorrencia);


--
-- TOC entry 2176 (class 2606 OID 24813)
-- Name: acidente acidente_pessoa_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_pessoa_f_key FOREIGN KEY (pessoa) REFERENCES pessoa(cpf);


--
-- TOC entry 2181 (class 2606 OID 24838)
-- Name: acidente acidente_situacao_pessoa_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_situacao_pessoa_f_key FOREIGN KEY (situacao_pessoa) REFERENCES situacao_pessoa(id_situacao_pessoa);


--
-- TOC entry 2179 (class 2606 OID 24828)
-- Name: acidente acidente_tipo_acidente_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_tipo_acidente_f_key FOREIGN KEY (tipo_acidente) REFERENCES tipo_acidente(id_tipo_acidente);


--
-- TOC entry 2180 (class 2606 OID 24833)
-- Name: acidente acidente_tipo_envolvido_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_tipo_envolvido_f_key FOREIGN KEY (tipo_envolvido) REFERENCES tipo_envolvido(id_tipo_envolvido);


--
-- TOC entry 2177 (class 2606 OID 24818)
-- Name: acidente acidente_veiculo_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY acidente
    ADD CONSTRAINT acidente_veiculo_f_key FOREIGN KEY (veiculo) REFERENCES veiculo(id_veiculo);


--
-- TOC entry 2164 (class 2606 OID 24698)
-- Name: local condicaoPista_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY local
    ADD CONSTRAINT "condicaoPista_f_key" FOREIGN KEY ("condicaoPista") REFERENCES condicoes_pista(id_condicoes);


--
-- TOC entry 2173 (class 2606 OID 24788)
-- Name: encaminhamento encaminhamento_local_atendimento_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT encaminhamento_local_atendimento_f_key FOREIGN KEY (local_atendimento) REFERENCES local_atendimento(id_local_atendimento);


--
-- TOC entry 2172 (class 2606 OID 24783)
-- Name: encaminhamento encaminhamento_pessoa_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY encaminhamento
    ADD CONSTRAINT encaminhamento_pessoa_f_key FOREIGN KEY (pessoa) REFERENCES pessoa(cpf);


--
-- TOC entry 2175 (class 2606 OID 24803)
-- Name: escala_policial escala_policial_escala_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY escala_policial
    ADD CONSTRAINT escala_policial_escala_f_key FOREIGN KEY (escala) REFERENCES escala(escala_id);


--
-- TOC entry 2174 (class 2606 OID 24798)
-- Name: escala_policial escala_policial_policial_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY escala_policial
    ADD CONSTRAINT escala_policial_policial_f_key FOREIGN KEY (policial) REFERENCES policial(cpf);


--
-- TOC entry 2170 (class 2606 OID 24766)
-- Name: ocorrencia ocorrencia_local_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT ocorrencia_local_f_key FOREIGN KEY (local) REFERENCES local(id_local);


--
-- TOC entry 2171 (class 2606 OID 24771)
-- Name: ocorrencia ocorrencia_policial_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ocorrencia
    ADD CONSTRAINT ocorrencia_policial_f_key FOREIGN KEY (policial) REFERENCES policial(cpf);


--
-- TOC entry 2169 (class 2606 OID 24739)
-- Name: pessoa pessoa_local_atendimento_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT pessoa_local_atendimento_f_key FOREIGN KEY (local_atendimento) REFERENCES local_atendimento(id_local_atendimento);


--
-- TOC entry 2168 (class 2606 OID 24734)
-- Name: pessoa pessoa_situacao_pessoa_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT pessoa_situacao_pessoa_f_key FOREIGN KEY (situacao_pessoa) REFERENCES situacao_pessoa(id_situacao_pessoa);


--
-- TOC entry 2165 (class 2606 OID 24693)
-- Name: local rodovia_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY local
    ADD CONSTRAINT rodovia_f_key FOREIGN KEY (rodovia) REFERENCES rodovia(id_rodovia);


--
-- TOC entry 2166 (class 2606 OID 24688)
-- Name: local tipoPista_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY local
    ADD CONSTRAINT "tipoPista_f_key" FOREIGN KEY ("tipoPista") REFERENCES tipo_pista(id_tipo_pista);


--
-- TOC entry 2167 (class 2606 OID 24724)
-- Name: veiculo veiculo_tipo_f_key; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY veiculo
    ADD CONSTRAINT veiculo_tipo_f_key FOREIGN KEY (tipo) REFERENCES tipo_veiculo(id_tipo_veiculo);


-- Completed on 2016-12-05 17:10:36 BRST

--
-- PostgreSQL database dump complete
--
