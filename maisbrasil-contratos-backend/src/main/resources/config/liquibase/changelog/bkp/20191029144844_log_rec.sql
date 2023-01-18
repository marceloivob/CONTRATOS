

-- Sequence: contratos.licitacao_log_rec_seq


-- DROP SEQUENCE contratos.licitacao_log_rec_seq;

CREATE SEQUENCE contratos.licitacao_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.licitacao_log_rec

-- DROP TABLE contratos.licitacao_log_rec;

CREATE TABLE contratos.licitacao_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.licitacao_log_rec_seq'::regclass), -- Coluna de id.
  data_homologacao date, 
  data_publicacao date, 
  entity_id bigint NOT NULL, 
  id_licitacao_fk bigint NOT NULL, 
  in_situacao character varying (3) NOT NULL, 
  modalidade character varying (50), 
  numero_ano character varying (1024) NOT NULL, 
  numero_processo character varying (1024), 
  objeto character varying (1024) NOT NULL, 
  processo_de_execucao character varying (50), 
  propostaid bigint, 
  regime_contratacao character varying (1024), 
  status_processo character varying (20) NOT NULL, 
  valor_processo NUMERIC(21,2) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT licitacao_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_licitacao_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.licitacao_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.licitacao_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_licitacao ON contratos.licitacao;
 -- DROP FUNCTION contratos.licitacao_trigger();
 CREATE OR REPLACE FUNCTION contratos.licitacao_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.licitacao_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       data_homologacao , 
       data_publicacao , 
       entity_id , 
       id_licitacao_fk , 
       in_situacao , 
       modalidade , 
       numero_ano , 
       numero_processo , 
       objeto , 
       processo_de_execucao , 
       propostaid , 
       regime_contratacao , 
       status_processo , 
       valor_processo 
      ) VALUES (
          1,
 	  nextval('contratos.licitacao_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.data_homologacao , 
      NEW.data_publicacao , 
      NEW.id , 
      NEW.id_licitacao_fk , 
      NEW.in_situacao , 
      NEW.modalidade , 
      NEW.numero_ano , 
      NEW.numero_processo , 
      NEW.objeto , 
      NEW.processo_de_execucao , 
      NEW.proposta_id , 
      NEW.regime_contratacao , 
      NEW.status_processo , 
      NEW.valor_processo 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.licitacao_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.licitacao_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       data_homologacao , 
       data_publicacao , 
       entity_id , 
       id_licitacao_fk , 
       in_situacao , 
       modalidade , 
       numero_ano , 
       numero_processo , 
       objeto , 
       processo_de_execucao , 
       propostaid , 
       regime_contratacao , 
       status_processo , 
       valor_processo 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.licitacao_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.data_homologacao , 
      OLD.data_publicacao , 
      OLD.id , 
      OLD.id_licitacao_fk , 
      OLD.in_situacao , 
      OLD.modalidade , 
      OLD.numero_ano , 
      OLD.numero_processo , 
      OLD.objeto , 
      OLD.processo_de_execucao , 
      OLD.proposta_id , 
      OLD.regime_contratacao , 
      OLD.status_processo , 
      OLD.valor_processo  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_licitacao on contratos.licitacao
 CREATE TRIGGER tg_licitacao
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.licitacao
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.licitacao_trigger(); 


-- Sequence: contratos.submeta_doc_complementar_log_rec_seq


-- DROP SEQUENCE contratos.submeta_doc_complementar_log_rec_seq;

CREATE SEQUENCE contratos.submeta_doc_complementar_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.submeta_doc_complementar_log_rec

-- DROP TABLE contratos.submeta_doc_complementar_log_rec;

CREATE TABLE contratos.submeta_doc_complementar_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.submeta_doc_complementar_log_rec_seq'::regclass), -- Coluna de id.
  data_associacao date, 
  doccomplementarid bigint, 
  entity_id bigint NOT NULL, 
  submetaid bigint, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT submeta_doc_complementar_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_submeta_doc_complementar_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.submeta_doc_complementar_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.submeta_doc_complementar_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_submeta_doc_complementar ON contratos.submeta_doc_complementar;
 -- DROP FUNCTION contratos.submeta_doc_complementar_trigger();
 CREATE OR REPLACE FUNCTION contratos.submeta_doc_complementar_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.submeta_doc_complementar_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       data_associacao , 
       doccomplementarid , 
       entity_id , 
       submetaid 
      ) VALUES (
          1,
 	  nextval('contratos.submeta_doc_complementar_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.data_associacao , 
      NEW.doc_complementar_id , 
      NEW.id , 
      NEW.submeta_id 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.submeta_doc_complementar_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.submeta_doc_complementar_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       data_associacao , 
       doccomplementarid , 
       entity_id , 
       submetaid 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.submeta_doc_complementar_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.data_associacao , 
      OLD.doc_complementar_id , 
      OLD.id , 
      OLD.submeta_id  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_submeta_doc_complementar on contratos.submeta_doc_complementar
 CREATE TRIGGER tg_submeta_doc_complementar
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.submeta_doc_complementar
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.submeta_doc_complementar_trigger(); 


-- Sequence: contratos.anexo_log_rec_seq


-- DROP SEQUENCE contratos.anexo_log_rec_seq;

CREATE SEQUENCE contratos.anexo_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.anexo_log_rec

-- DROP TABLE contratos.anexo_log_rec;

CREATE TABLE contratos.anexo_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.anexo_log_rec_seq'::regclass), -- Coluna de id.
  bucket character varying (25) NOT NULL, 
  caminho character varying (1024) NOT NULL, 
  contratoid bigint, 
  dt_upload date NOT NULL, 
  entity_id bigint NOT NULL, 
  id_cpf_enviado_por character varying (11) NOT NULL, 
  in_perfil_usuario character varying (10) NOT NULL, 
  in_tipo_anexo character varying (30) NOT NULL, 
  nm_arquivo character varying (100) NOT NULL, 
  nome_enviado_por character varying (60) NOT NULL, 
  termoaditivoid bigint, 
  tx_descricao character varying (30) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT anexo_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_anexo_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.anexo_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.anexo_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_anexo ON contratos.anexo;
 -- DROP FUNCTION contratos.anexo_trigger();
 CREATE OR REPLACE FUNCTION contratos.anexo_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.anexo_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       bucket , 
       caminho , 
       contratoid , 
       dt_upload , 
       entity_id , 
       id_cpf_enviado_por , 
       in_perfil_usuario , 
       in_tipo_anexo , 
       nm_arquivo , 
       nome_enviado_por , 
       termoaditivoid , 
       tx_descricao 
      ) VALUES (
          1,
 	  nextval('contratos.anexo_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.bucket , 
      NEW.caminho , 
      NEW.contrato_id , 
      NEW.dt_upload , 
      NEW.id , 
      NEW.id_cpf_enviado_por , 
      NEW.in_perfil_usuario , 
      NEW.in_tipo_anexo , 
      NEW.nm_arquivo , 
      NEW.nome_enviado_por , 
      NEW.termo_aditivo_id , 
      NEW.tx_descricao 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.anexo_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.anexo_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       bucket , 
       caminho , 
       contratoid , 
       dt_upload , 
       entity_id , 
       id_cpf_enviado_por , 
       in_perfil_usuario , 
       in_tipo_anexo , 
       nm_arquivo , 
       nome_enviado_por , 
       termoaditivoid , 
       tx_descricao 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.anexo_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.bucket , 
      OLD.caminho , 
      OLD.contrato_id , 
      OLD.dt_upload , 
      OLD.id , 
      OLD.id_cpf_enviado_por , 
      OLD.in_perfil_usuario , 
      OLD.in_tipo_anexo , 
      OLD.nm_arquivo , 
      OLD.nome_enviado_por , 
      OLD.termo_aditivo_id , 
      OLD.tx_descricao  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_anexo on contratos.anexo
 CREATE TRIGGER tg_anexo
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.anexo
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.anexo_trigger(); 


-- Sequence: contratos.lote_log_rec_seq


-- DROP SEQUENCE contratos.lote_log_rec_seq;

CREATE SEQUENCE contratos.lote_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.lote_log_rec

-- DROP TABLE contratos.lote_log_rec;

CREATE TABLE contratos.lote_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.lote_log_rec_seq'::regclass), -- Coluna de id.
  contratoid bigint, 
  fornecedorid bigint, 
  entity_id bigint NOT NULL, 
  licitacaoid bigint, 
  numero bigint NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT lote_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_lote_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.lote_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.lote_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_lote ON contratos.lote;
 -- DROP FUNCTION contratos.lote_trigger();
 CREATE OR REPLACE FUNCTION contratos.lote_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.lote_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       contratoid , 
       fornecedorid , 
       entity_id , 
       licitacaoid , 
       numero 
      ) VALUES (
          1,
 	  nextval('contratos.lote_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.contrato_id , 
      NEW.fornecedor_id , 
      NEW.id , 
      NEW.licitacao_id , 
      NEW.numero 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.lote_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.lote_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       contratoid , 
       fornecedorid , 
       entity_id , 
       licitacaoid , 
       numero 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.lote_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.contrato_id , 
      OLD.fornecedor_id , 
      OLD.id , 
      OLD.licitacao_id , 
      OLD.numero  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_lote on contratos.lote
 CREATE TRIGGER tg_lote
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.lote
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.lote_trigger(); 


-- Sequence: contratos.proposta_log_rec_seq


-- DROP SEQUENCE contratos.proposta_log_rec_seq;

CREATE SEQUENCE contratos.proposta_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.proposta_log_rec

-- DROP TABLE contratos.proposta_log_rec;

CREATE TABLE contratos.proposta_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.proposta_log_rec_seq'::regclass), -- Coluna de id.
  ano_convenio bigint, 
  ano_proposta bigint NOT NULL, 
  apelido_empreendimento character varying (50) NOT NULL, 
  categoria character varying (50) NOT NULL, 
  codigo_programa character varying (13) NOT NULL, 
  data_assinatura_convenio date, 
  entity_id bigint NOT NULL, 
  id_siconv bigint NOT NULL, 
  identificacao_proponente character varying (1024), 
  modalidade bigint NOT NULL, 
  nivel_contrato character varying (20), 
  nome_mandataria character varying (1024), 
  nome_objeto character varying (5000), 
  nome_programa character varying (255) NOT NULL, 
  nome_proponente character varying (1024), 
  numero_convenio bigint, 
  numero_proposta bigint NOT NULL, 
  pc_min_contrapartida NUMERIC(21,2), 
  spa_homologado boolean NOT NULL, 
  uf character varying (2) NOT NULL, 
  valor_contrapartida NUMERIC(21,2) NOT NULL, 
  valor_global NUMERIC(21,2) NOT NULL, 
  valor_repasse NUMERIC(21,2) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT proposta_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_proposta_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.proposta_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.proposta_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_proposta ON contratos.proposta;
 -- DROP FUNCTION contratos.proposta_trigger();
 CREATE OR REPLACE FUNCTION contratos.proposta_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.proposta_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       ano_convenio , 
       ano_proposta , 
       apelido_empreendimento , 
       categoria , 
       codigo_programa , 
       data_assinatura_convenio , 
       entity_id , 
       id_siconv , 
       identificacao_proponente , 
       modalidade , 
       nivel_contrato , 
       nome_mandataria , 
       nome_objeto , 
       nome_programa , 
       nome_proponente , 
       numero_convenio , 
       numero_proposta , 
       pc_min_contrapartida , 
       spa_homologado , 
       uf , 
       valor_contrapartida , 
       valor_global , 
       valor_repasse 
      ) VALUES (
          1,
 	  nextval('contratos.proposta_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.ano_convenio , 
      NEW.ano_proposta , 
      NEW.apelido_empreendimento , 
      NEW.categoria , 
      NEW.codigo_programa , 
      NEW.data_assinatura_convenio , 
      NEW.id , 
      NEW.id_siconv , 
      NEW.identificacao_proponente , 
      NEW.modalidade , 
      NEW.nivel_contrato , 
      NEW.nome_mandataria , 
      NEW.nome_objeto , 
      NEW.nome_programa , 
      NEW.nome_proponente , 
      NEW.numero_convenio , 
      NEW.numero_proposta , 
      NEW.pc_min_contrapartida , 
      NEW.spa_homologado , 
      NEW.uf , 
      NEW.valor_contrapartida , 
      NEW.valor_global , 
      NEW.valor_repasse 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.proposta_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.proposta_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       ano_convenio , 
       ano_proposta , 
       apelido_empreendimento , 
       categoria , 
       codigo_programa , 
       data_assinatura_convenio , 
       entity_id , 
       id_siconv , 
       identificacao_proponente , 
       modalidade , 
       nivel_contrato , 
       nome_mandataria , 
       nome_objeto , 
       nome_programa , 
       nome_proponente , 
       numero_convenio , 
       numero_proposta , 
       pc_min_contrapartida , 
       spa_homologado , 
       uf , 
       valor_contrapartida , 
       valor_global , 
       valor_repasse 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.proposta_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.ano_convenio , 
      OLD.ano_proposta , 
      OLD.apelido_empreendimento , 
      OLD.categoria , 
      OLD.codigo_programa , 
      OLD.data_assinatura_convenio , 
      OLD.id , 
      OLD.id_siconv , 
      OLD.identificacao_proponente , 
      OLD.modalidade , 
      OLD.nivel_contrato , 
      OLD.nome_mandataria , 
      OLD.nome_objeto , 
      OLD.nome_programa , 
      OLD.nome_proponente , 
      OLD.numero_convenio , 
      OLD.numero_proposta , 
      OLD.pc_min_contrapartida , 
      OLD.spa_homologado , 
      OLD.uf , 
      OLD.valor_contrapartida , 
      OLD.valor_global , 
      OLD.valor_repasse  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_proposta on contratos.proposta
 CREATE TRIGGER tg_proposta
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.proposta
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.proposta_trigger(); 


-- Sequence: contratos.historico_log_rec_seq


-- DROP SEQUENCE contratos.historico_log_rec_seq;

CREATE SEQUENCE contratos.historico_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.historico_log_rec

-- DROP TABLE contratos.historico_log_rec;

CREATE TABLE contratos.historico_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.historico_log_rec_seq'::regclass), -- Coluna de id.
  contratoid bigint NOT NULL, 
  cpf_responsavel character varying (11) NOT NULL, 
  data_registro date NOT NULL, 
  evento_gerador character varying (3) NOT NULL, 
  entity_id bigint NOT NULL, 
  nome_responsavel character varying (60) NOT NULL, 
  situacao_contrato character varying (3) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT historico_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_historico_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.historico_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.historico_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_historico ON contratos.historico;
 -- DROP FUNCTION contratos.historico_trigger();
 CREATE OR REPLACE FUNCTION contratos.historico_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.historico_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       contratoid , 
       cpf_responsavel , 
       data_registro , 
       evento_gerador , 
       entity_id , 
       nome_responsavel , 
       situacao_contrato 
      ) VALUES (
          1,
 	  nextval('contratos.historico_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.contrato_id , 
      NEW.cpf_responsavel , 
      NEW.data_registro , 
      NEW.evento_gerador , 
      NEW.id , 
      NEW.nome_responsavel , 
      NEW.situacao_contrato 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.historico_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.historico_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       contratoid , 
       cpf_responsavel , 
       data_registro , 
       evento_gerador , 
       entity_id , 
       nome_responsavel , 
       situacao_contrato 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.historico_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.contrato_id , 
      OLD.cpf_responsavel , 
      OLD.data_registro , 
      OLD.evento_gerador , 
      OLD.id , 
      OLD.nome_responsavel , 
      OLD.situacao_contrato  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_historico on contratos.historico
 CREATE TRIGGER tg_historico
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.historico
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.historico_trigger(); 


-- Sequence: contratos.contrato_log_rec_seq


-- DROP SEQUENCE contratos.contrato_log_rec_seq;

CREATE SEQUENCE contratos.contrato_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.contrato_log_rec

-- DROP TABLE contratos.contrato_log_rec;

CREATE TABLE contratos.contrato_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.contrato_log_rec_seq'::regclass), -- Coluna de id.
  apto_a_iniciar boolean NOT NULL, 
  data_assinatura date NOT NULL, 
  data_publicacao date NOT NULL, 
  fim_vigencia date NOT NULL, 
  entity_id bigint NOT NULL, 
  in_situacao character varying (3) NOT NULL, 
  inicio_vigencia date NOT NULL, 
  numero character varying (20) NOT NULL, 
  objeto character varying (5000) NOT NULL, 
  propostaid bigint, 
  valor_total NUMERIC(21,2) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT contrato_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_contrato_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.contrato_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.contrato_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_contrato ON contratos.contrato;
 -- DROP FUNCTION contratos.contrato_trigger();
 CREATE OR REPLACE FUNCTION contratos.contrato_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.contrato_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       apto_a_iniciar , 
       data_assinatura , 
       data_publicacao , 
       fim_vigencia , 
       entity_id , 
       in_situacao , 
       inicio_vigencia , 
       numero , 
       objeto , 
       propostaid , 
       valor_total 
      ) VALUES (
          1,
 	  nextval('contratos.contrato_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.apto_a_iniciar , 
      NEW.data_assinatura , 
      NEW.data_publicacao , 
      NEW.fim_vigencia , 
      NEW.id , 
      NEW.in_situacao , 
      NEW.inicio_vigencia , 
      NEW.numero , 
      NEW.objeto , 
      NEW.proposta_id , 
      NEW.valor_total 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.contrato_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.contrato_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       apto_a_iniciar , 
       data_assinatura , 
       data_publicacao , 
       fim_vigencia , 
       entity_id , 
       in_situacao , 
       inicio_vigencia , 
       numero , 
       objeto , 
       propostaid , 
       valor_total 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.contrato_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.apto_a_iniciar , 
      OLD.data_assinatura , 
      OLD.data_publicacao , 
      OLD.fim_vigencia , 
      OLD.id , 
      OLD.in_situacao , 
      OLD.inicio_vigencia , 
      OLD.numero , 
      OLD.objeto , 
      OLD.proposta_id , 
      OLD.valor_total  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_contrato on contratos.contrato
 CREATE TRIGGER tg_contrato
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.contrato
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.contrato_trigger(); 


-- Sequence: contratos.submeta_log_rec_seq


-- DROP SEQUENCE contratos.submeta_log_rec_seq;

CREATE SEQUENCE contratos.submeta_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.submeta_log_rec

-- DROP TABLE contratos.submeta_log_rec;

CREATE TABLE contratos.submeta_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.submeta_log_rec_seq'::regclass), -- Coluna de id.
  descricao character varying (100) NOT NULL, 
  entity_id bigint NOT NULL, 
  in_regime_execucao character varying (3) NOT NULL, 
  in_situacao character varying (3) NOT NULL, 
  loteid bigint, 
  metaid bigint, 
  numero bigint NOT NULL, 
  propostaid bigint, 
  vl_contrapartida NUMERIC(21,2) NOT NULL, 
  vl_outros NUMERIC(21,2) NOT NULL, 
  vl_repasse NUMERIC(21,2) NOT NULL, 
  vl_total_licitado NUMERIC(21,2) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT submeta_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_submeta_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.submeta_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.submeta_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_submeta ON contratos.submeta;
 -- DROP FUNCTION contratos.submeta_trigger();
 CREATE OR REPLACE FUNCTION contratos.submeta_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.submeta_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       descricao , 
       entity_id , 
       in_regime_execucao , 
       in_situacao , 
       loteid , 
       metaid , 
       numero , 
       propostaid , 
       vl_contrapartida , 
       vl_outros , 
       vl_repasse , 
       vl_total_licitado 
      ) VALUES (
          1,
 	  nextval('contratos.submeta_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.descricao , 
      NEW.id , 
      NEW.in_regime_execucao , 
      NEW.in_situacao , 
      NEW.lote_id , 
      NEW.meta_id , 
      NEW.numero , 
      NEW.proposta_id , 
      NEW.vl_contrapartida , 
      NEW.vl_outros , 
      NEW.vl_repasse , 
      NEW.vl_total_licitado 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.submeta_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.submeta_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       descricao , 
       entity_id , 
       in_regime_execucao , 
       in_situacao , 
       loteid , 
       metaid , 
       numero , 
       propostaid , 
       vl_contrapartida , 
       vl_outros , 
       vl_repasse , 
       vl_total_licitado 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.submeta_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.descricao , 
      OLD.id , 
      OLD.in_regime_execucao , 
      OLD.in_situacao , 
      OLD.lote_id , 
      OLD.meta_id , 
      OLD.numero , 
      OLD.proposta_id , 
      OLD.vl_contrapartida , 
      OLD.vl_outros , 
      OLD.vl_repasse , 
      OLD.vl_total_licitado  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_submeta on contratos.submeta
 CREATE TRIGGER tg_submeta
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.submeta
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.submeta_trigger(); 


-- Sequence: contratos.termo_aditivo_log_rec_seq


-- DROP SEQUENCE contratos.termo_aditivo_log_rec_seq;

CREATE SEQUENCE contratos.termo_aditivo_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.termo_aditivo_log_rec

-- DROP TABLE contratos.termo_aditivo_log_rec;

CREATE TABLE contratos.termo_aditivo_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.termo_aditivo_log_rec_seq'::regclass), -- Coluna de id.
  contratoid bigint, 
  data_assinatura date NOT NULL, 
  data_publicacao date NOT NULL, 
  entity_id bigint NOT NULL, 
  in_alteracao_clausula boolean NOT NULL, 
  in_alteracao_objeto boolean NOT NULL, 
  in_alteracao_vigencia boolean NOT NULL, 
  in_tipo_de_aditivo character varying (3) NOT NULL, 
  justificativa character varying (1000) NOT NULL, 
  nova_data_fim_vigencia date NOT NULL, 
  numero character varying (20) NOT NULL, 
  valor_acrescimo NUMERIC(21,2) NOT NULL, 
  valor_supressao NUMERIC(21,2) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT termo_aditivo_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_termo_aditivo_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.termo_aditivo_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.termo_aditivo_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_termo_aditivo ON contratos.termo_aditivo;
 -- DROP FUNCTION contratos.termo_aditivo_trigger();
 CREATE OR REPLACE FUNCTION contratos.termo_aditivo_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.termo_aditivo_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       contratoid , 
       data_assinatura , 
       data_publicacao , 
       entity_id , 
       in_alteracao_clausula , 
       in_alteracao_objeto , 
       in_alteracao_vigencia , 
       in_tipo_de_aditivo , 
       justificativa , 
       nova_data_fim_vigencia , 
       numero , 
       valor_acrescimo , 
       valor_supressao 
      ) VALUES (
          1,
 	  nextval('contratos.termo_aditivo_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.contrato_id , 
      NEW.data_assinatura , 
      NEW.data_publicacao , 
      NEW.id , 
      NEW.in_alteracao_clausula , 
      NEW.in_alteracao_objeto , 
      NEW.in_alteracao_vigencia , 
      NEW.in_tipo_de_aditivo , 
      NEW.justificativa , 
      NEW.nova_data_fim_vigencia , 
      NEW.numero , 
      NEW.valor_acrescimo , 
      NEW.valor_supressao 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.termo_aditivo_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.termo_aditivo_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       contratoid , 
       data_assinatura , 
       data_publicacao , 
       entity_id , 
       in_alteracao_clausula , 
       in_alteracao_objeto , 
       in_alteracao_vigencia , 
       in_tipo_de_aditivo , 
       justificativa , 
       nova_data_fim_vigencia , 
       numero , 
       valor_acrescimo , 
       valor_supressao 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.termo_aditivo_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.contrato_id , 
      OLD.data_assinatura , 
      OLD.data_publicacao , 
      OLD.id , 
      OLD.in_alteracao_clausula , 
      OLD.in_alteracao_objeto , 
      OLD.in_alteracao_vigencia , 
      OLD.in_tipo_de_aditivo , 
      OLD.justificativa , 
      OLD.nova_data_fim_vigencia , 
      OLD.numero , 
      OLD.valor_acrescimo , 
      OLD.valor_supressao  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_termo_aditivo on contratos.termo_aditivo
 CREATE TRIGGER tg_termo_aditivo
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.termo_aditivo
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.termo_aditivo_trigger(); 


-- Sequence: contratos.doc_complementar_log_rec_seq


-- DROP SEQUENCE contratos.doc_complementar_log_rec_seq;

CREATE SEQUENCE contratos.doc_complementar_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.doc_complementar_log_rec

-- DROP TABLE contratos.doc_complementar_log_rec;

CREATE TABLE contratos.doc_complementar_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.doc_complementar_log_rec_seq'::regclass), -- Coluna de id.
  anexoid bigint, 
  data_emissao date NOT NULL, 
  data_validade date NOT NULL, 
  entity_id bigint NOT NULL, 
  numero_documento character varying (40) NOT NULL, 
  orgao_emissor character varying (100) NOT NULL, 
  tipo character varying (50) NOT NULL, 
  tipo_manifesto_ambiental character varying (50), 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT doc_complementar_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_doc_complementar_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.doc_complementar_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.doc_complementar_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_doc_complementar ON contratos.doc_complementar;
 -- DROP FUNCTION contratos.doc_complementar_trigger();
 CREATE OR REPLACE FUNCTION contratos.doc_complementar_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.doc_complementar_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       anexoid , 
       data_emissao , 
       data_validade , 
       entity_id , 
       numero_documento , 
       orgao_emissor , 
       tipo , 
       tipo_manifesto_ambiental 
      ) VALUES (
          1,
 	  nextval('contratos.doc_complementar_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.anexo_id , 
      NEW.data_emissao , 
      NEW.data_validade , 
      NEW.id , 
      NEW.numero_documento , 
      NEW.orgao_emissor , 
      NEW.tipo , 
      NEW.tipo_manifesto_ambiental 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.doc_complementar_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.doc_complementar_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       anexoid , 
       data_emissao , 
       data_validade , 
       entity_id , 
       numero_documento , 
       orgao_emissor , 
       tipo , 
       tipo_manifesto_ambiental 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.doc_complementar_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.anexo_id , 
      OLD.data_emissao , 
      OLD.data_validade , 
      OLD.id , 
      OLD.numero_documento , 
      OLD.orgao_emissor , 
      OLD.tipo , 
      OLD.tipo_manifesto_ambiental  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_doc_complementar on contratos.doc_complementar
 CREATE TRIGGER tg_doc_complementar
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.doc_complementar
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.doc_complementar_trigger(); 


-- Sequence: contratos.meta_log_rec_seq


-- DROP SEQUENCE contratos.meta_log_rec_seq;

CREATE SEQUENCE contratos.meta_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.meta_log_rec

-- DROP TABLE contratos.meta_log_rec;

CREATE TABLE contratos.meta_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.meta_log_rec_seq'::regclass), -- Coluna de id.
  entity_id bigint NOT NULL, 
  id_meta_vrpl bigint NOT NULL, 
  in_social boolean NOT NULL, 
  numero bigint NOT NULL, 
  qt_itens NUMERIC(21,2) NOT NULL, 
  tx_descricao character varying (100) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT meta_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_meta_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.meta_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.meta_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_meta ON contratos.meta;
 -- DROP FUNCTION contratos.meta_trigger();
 CREATE OR REPLACE FUNCTION contratos.meta_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.meta_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       entity_id , 
       id_meta_vrpl , 
       in_social , 
       numero , 
       qt_itens , 
       tx_descricao 
      ) VALUES (
          1,
 	  nextval('contratos.meta_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.id , 
      NEW.id_meta_vrpl , 
      NEW.in_social , 
      NEW.numero , 
      NEW.qt_itens , 
      NEW.tx_descricao 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.meta_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.meta_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       entity_id , 
       id_meta_vrpl , 
       in_social , 
       numero , 
       qt_itens , 
       tx_descricao 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.meta_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.id , 
      OLD.id_meta_vrpl , 
      OLD.in_social , 
      OLD.numero , 
      OLD.qt_itens , 
      OLD.tx_descricao  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_meta on contratos.meta
 CREATE TRIGGER tg_meta
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.meta
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.meta_trigger(); 


-- Sequence: contratos.fornecedor_log_rec_seq


-- DROP SEQUENCE contratos.fornecedor_log_rec_seq;

CREATE SEQUENCE contratos.fornecedor_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.fornecedor_log_rec

-- DROP TABLE contratos.fornecedor_log_rec;

CREATE TABLE contratos.fornecedor_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.fornecedor_log_rec_seq'::regclass), -- Coluna de id.
  entity_id bigint NOT NULL, 
  identificacao character varying (20) NOT NULL, 
  razao_social character varying (150) NOT NULL, 
  tipo_identificacao character varying (4) NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT fornecedor_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_fornecedor_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.fornecedor_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.fornecedor_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_fornecedor ON contratos.fornecedor;
 -- DROP FUNCTION contratos.fornecedor_trigger();
 CREATE OR REPLACE FUNCTION contratos.fornecedor_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.fornecedor_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       entity_id , 
       identificacao , 
       razao_social , 
       tipo_identificacao 
      ) VALUES (
          1,
 	  nextval('contratos.fornecedor_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.id , 
      NEW.identificacao , 
      NEW.razao_social , 
      NEW.tipo_identificacao 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.fornecedor_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.fornecedor_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       entity_id , 
       identificacao , 
       razao_social , 
       tipo_identificacao 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.fornecedor_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.id , 
      OLD.identificacao , 
      OLD.razao_social , 
      OLD.tipo_identificacao  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_fornecedor on contratos.fornecedor
 CREATE TRIGGER tg_fornecedor
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.fornecedor
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.fornecedor_trigger(); 


-- Sequence: contratos.po_log_rec_seq


-- DROP SEQUENCE contratos.po_log_rec_seq;

CREATE SEQUENCE contratos.po_log_rec_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;




-- Table: contratos.po_log_rec

-- DROP TABLE contratos.po_log_rec;

CREATE TABLE contratos.po_log_rec
(
  id bigint NOT NULL DEFAULT nextval('contratos.po_log_rec_seq'::regclass), -- Coluna de id.
  dt_base_analise date NOT NULL, 
  dt_base_vrpl date, 
  dt_previsao_inicio_obra date NOT NULL, 
  dt_previsao_inicio_obra_analise date NOT NULL, 
  entity_id bigint NOT NULL, 
  id_po_vrpl bigint NOT NULL, 
  in_acompanhamento_eventos boolean, 
  in_desonerado boolean, 
  qt_meses_duracao_obra smallint NOT NULL, 
  referencia character varying (8), 
  sg_localidade character varying (2), 
  submetaid bigint NOT NULL, 
  versao bigint,
  adt_login character varying(60),
  adt_data_hora timestamp without time zone,
  adt_operacao character varying(6),
  CONSTRAINT po_log_rec_pkey PRIMARY KEY (id),
  CONSTRAINT ck_po_adt_operacao CHECK (adt_operacao::text = ANY (ARRAY['INSERT'::character varying::text, 'UPDATE'::character varying::text, 'DELETE'::character varying::text]))
)
WITH (
  OIDS=FALSE
);
COMMENT ON TABLE contratos.po_log_rec
  IS 'Tabela que representa ????';
COMMENT ON COLUMN contratos.po_log_rec.id IS 'Coluna de id.';


-- DROP TRIGGER tg_po ON contratos.po;
 -- DROP FUNCTION contratos.po_trigger();
 CREATE OR REPLACE FUNCTION contratos.po_trigger()
   RETURNS trigger AS 
 $BODY$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.po_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       dt_base_analise , 
       dt_base_vrpl , 
       dt_previsao_inicio_obra , 
       dt_previsao_inicio_obra_analise , 
       entity_id , 
       id_po_vrpl , 
       in_acompanhamento_eventos , 
       in_desonerado , 
       qt_meses_duracao_obra , 
       referencia , 
       sg_localidade , 
       submetaid 
      ) VALUES (
          1,
 	  nextval('contratos.po_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.dt_base_analise , 
      NEW.dt_base_vrpl , 
      NEW.dt_previsao_inicio_obra , 
      NEW.dt_previsao_inicio_obra_analise , 
      NEW.id , 
      NEW.id_po_vrpl , 
      NEW.in_acompanhamento_eventos , 
      NEW.in_desonerado , 
      NEW.qt_meses_duracao_obra , 
      NEW.referencia , 
      NEW.sg_localidade , 
      NEW.submeta_id 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.po_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.po_log_rec (
 	   versao,
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
       dt_base_analise , 
       dt_base_vrpl , 
       dt_previsao_inicio_obra , 
       dt_previsao_inicio_obra_analise , 
       entity_id , 
       id_po_vrpl , 
       in_acompanhamento_eventos , 
       in_desonerado , 
       qt_meses_duracao_obra , 
       referencia , 
       sg_localidade , 
       submetaid 
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.po_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.dt_base_analise , 
      OLD.dt_base_vrpl , 
      OLD.dt_previsao_inicio_obra , 
      OLD.dt_previsao_inicio_obra_analise , 
      OLD.id , 
      OLD.id_po_vrpl , 
      OLD.in_acompanhamento_eventos , 
      OLD.in_desonerado , 
      OLD.qt_meses_duracao_obra , 
      OLD.referencia , 
      OLD.sg_localidade , 
      OLD.submeta_id  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
 
 
 -- Trigger: contratos.tg_po on contratos.po
 CREATE TRIGGER tg_po
   AFTER INSERT OR UPDATE OR DELETE
   ON contratos.po
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.po_trigger(); 
