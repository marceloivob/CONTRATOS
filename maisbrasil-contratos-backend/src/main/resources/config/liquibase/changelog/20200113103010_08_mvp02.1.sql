ALTER TABLE contratos.proposta ADD COLUMN id_contrato_emissor_aio bigint;
ALTER TABLE contratos.proposta_log_rec ADD COLUMN id_contrato_emissor_aio bigint;

ALTER TABLE contratos.proposta ADD COLUMN data_emissao_aio date;
ALTER TABLE contratos.proposta_log_rec ADD COLUMN data_emissao_aio date;

COMMENT ON COLUMN contratos.proposta.id_contrato_emissor_aio IS 'Id do contrato emissor de AIO; Caso NULL, AIO não emitido.';
COMMENT ON COLUMN contratos.proposta_log_rec.id_contrato_emissor_aio IS 'Id do contrato emissor de AIO; Caso NULL, AIO não emitido.';

COMMENT ON COLUMN contratos.proposta.data_emissao_aio IS 'Data de emissão do AIO';
COMMENT ON COLUMN contratos.proposta_log_rec.data_emissao_aio IS 'Data de emissão do AIO';

CREATE SEQUENCE contratos.validacoes_datas_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
CREATE SEQUENCE contratos.validacoes_datas_log_rec_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE contratos.validacoes_datas (
    id bigint DEFAULT nextval('contratos.validacoes_datas_seq'::regclass) NOT NULL,
    ano numeric(4) NOT NULL,
    inicio date,
    fim date,
    in_tipo_validacao character varying(30) NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    UNIQUE (ano, in_tipo_validacao),
    CONSTRAINT ck_validacoes_datas_in_tipo CHECK (((in_tipo_validacao)::text = ANY (ARRAY[('PERIODO_ELEITORAL'::character varying)::text, ('RESTOS_A_PAGAR'::character varying)::text]))),
    CONSTRAINT ck_validacoes_datas_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);  

COMMENT ON COLUMN contratos.validacoes_datas.ano IS 'O ano que se deseja obter as datas para validações.';
COMMENT ON COLUMN contratos.validacoes_datas.inicio IS 'Data de inicio da validação, se houver.';
COMMENT ON COLUMN contratos.validacoes_datas.fim IS 'Data de fim da validação, se houver.';
COMMENT ON COLUMN contratos.validacoes_datas.in_tipo_validacao IS 'PERIODO_ELEITORAL ou RESTOS_A_PAGAR.';


CREATE TABLE contratos.validacoes_datas_log_rec (
    id bigint DEFAULT nextval('contratos.validacoes_datas_log_rec_seq'::regclass) NOT NULL,
    entity_id bigint NOT NULL,
    ano numeric(4) NOT NULL,
    inicio date,
    fim date,
    in_tipo_validacao character varying(30) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_doc_complementar_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);

COMMENT ON COLUMN contratos.validacoes_datas_log_rec.ano IS 'O ano que se deseja obter as datas para validações.';
COMMENT ON COLUMN contratos.validacoes_datas_log_rec.inicio IS 'Data de inicio da validação, se houver.';
COMMENT ON COLUMN contratos.validacoes_datas_log_rec.fim IS 'Data de fim da validação, se houver.';
COMMENT ON COLUMN contratos.validacoes_datas_log_rec.in_tipo_validacao IS 'PERIODO_ELEITORAL ou RESTOS_A_PAGAR.';


CREATE FUNCTION contratos.validacoes_datas_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.validacoes_datas_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
	   entity_id , 
       ano , 
       inicio , 
       fim , 
       in_tipo_validacao 
      ) VALUES (
          1,
 	  nextval('contratos.validacoes_datas_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
	  NEW.id , 
      NEW.ano , 
      NEW.inicio , 
      NEW.fim , 
      NEW.in_tipo_validacao 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.validacoes_datas_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.contrato_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
	   entity_id , 
       ano , 
       inicio , 
       fim , 
       in_tipo_validacao
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.validacoes_datas_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
	  OLD.id , 
      OLD.ano , 
      OLD.inicio , 
      OLD.fim , 
      OLD.in_tipo_validacao 
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $$;

CREATE TRIGGER tg_validacoes_datas AFTER INSERT OR DELETE OR UPDATE ON contratos.validacoes_datas FOR EACH ROW EXECUTE PROCEDURE contratos.validacoes_datas_trigger();

DROP TRIGGER tg_proposta ON contratos.proposta;

DROP FUNCTION contratos.proposta_trigger();

CREATE FUNCTION contratos.proposta_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
       valor_repasse ,
       id_contrato_emissor_aio ,
       data_emissao_aio
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
      NEW.valor_repasse ,
      NEW.id_contrato_emissor_aio ,
      NEW.data_emissao_aio
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
       valor_repasse ,
       id_contrato_emissor_aio ,
       data_emissao_aio
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
      OLD.valor_repasse ,
      OLD.id_contrato_emissor_aio ,
      OLD.data_emissao_aio
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $$;

CREATE TRIGGER tg_proposta AFTER INSERT OR DELETE OR UPDATE ON contratos.proposta FOR EACH ROW EXECUTE PROCEDURE contratos.proposta_trigger();
