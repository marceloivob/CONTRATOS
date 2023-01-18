
ALTER TABLE contratos.termo_aditivo ADD COLUMN in_situacao varchar(3);

ALTER TABLE contratos.termo_aditivo DROP CONSTRAINT ck_termo_aditivo_in_tipo_de_aditivo;
ALTER TABLE contratos.termo_aditivo DROP COLUMN in_tipo_de_aditivo;
ALTER TABLE contratos.termo_aditivo ADD COLUMN in_alteracao_supressao_acrescimo varchar(3);
ALTER TABLE contratos.termo_aditivo ADD CONSTRAINT ck_termo_aditivo_in_alteracao_supressao_acrescimo CHECK (((in_alteracao_supressao_acrescimo)::text = ANY (ARRAY[('ACR'::character varying)::text, ('SUP'::character varying)::text])));

ALTER TABLE contratos.termo_aditivo RENAME COLUMN data_assinatura TO dt_assinatura;
ALTER TABLE contratos.termo_aditivo RENAME COLUMN data_publicacao TO dt_publicacao;

ALTER TABLE contratos.termo_aditivo DROP COLUMN valor_acrescimo;
ALTER TABLE contratos.termo_aditivo DROP COLUMN valor_supressao;
ALTER TABLE contratos.termo_aditivo ADD COLUMN vl_supressao_acrescimo numeric(21, 2);

ALTER TABLE contratos.termo_aditivo DROP COLUMN nova_data_fim_vigencia;
ALTER TABLE contratos.termo_aditivo ADD COLUMN dt_nova_data_fim_vigencia date;

ALTER TABLE contratos.termo_aditivo DROP COLUMN justificativa;
ALTER TABLE contratos.termo_aditivo ADD COLUMN tx_justificativa varchar(5000);

ALTER TABLE contratos.termo_aditivo ADD COLUMN tx_descricao_ampliacao_objeto varchar(5000);

ALTER TABLE contratos.termo_aditivo ADD CONSTRAINT ck_termo_aditivo_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EXC'::character varying)::text])));

COMMENT ON COLUMN contratos.termo_aditivo.in_alteracao_supressao_acrescimo IS 'Se diferente de nulo, existe acrescismo (ACR) ou supressao (SUP) de valor.';
COMMENT ON COLUMN contratos.termo_aditivo.vl_supressao_acrescimo IS 'Valor do acrescimo ou da supressao, se houver.';
COMMENT ON COLUMN contratos.termo_aditivo.dt_nova_data_fim_vigencia IS 'Nova data final de vigencia, se houver.';
COMMENT ON COLUMN contratos.termo_aditivo.tx_justificativa IS 'Justificativa.';
COMMENT ON COLUMN contratos.termo_aditivo.tx_descricao_ampliacao_objeto IS 'Descricao da ampliacao do objeto.';
COMMENT ON COLUMN contratos.termo_aditivo.in_situacao IS 'Situacao do termo aditivo RAS, CON, EXC';

ALTER TABLE contratos.termo_aditivo_log_rec DROP COLUMN in_tipo_de_aditivo;
ALTER TABLE contratos.termo_aditivo_log_rec ADD COLUMN in_alteracao_supressao_acrescimo varchar(3);

ALTER TABLE contratos.termo_aditivo_log_rec RENAME COLUMN data_assinatura TO dt_assinatura;
ALTER TABLE contratos.termo_aditivo_log_rec RENAME COLUMN data_publicacao TO dt_publicacao;

ALTER TABLE contratos.termo_aditivo_log_rec DROP COLUMN valor_acrescimo;
ALTER TABLE contratos.termo_aditivo_log_rec DROP COLUMN valor_supressao;
ALTER TABLE contratos.termo_aditivo_log_rec ADD COLUMN vl_supressao_acrescimo numeric(21, 2);

ALTER TABLE contratos.termo_aditivo_log_rec DROP COLUMN nova_data_fim_vigencia;
ALTER TABLE contratos.termo_aditivo_log_rec ADD COLUMN dt_nova_data_fim_vigencia date;

ALTER TABLE contratos.termo_aditivo_log_rec DROP COLUMN justificativa;
ALTER TABLE contratos.termo_aditivo_log_rec ADD COLUMN tx_justificativa varchar(5000);

ALTER TABLE contratos.termo_aditivo_log_rec ADD COLUMN tx_descricao_ampliacao_objeto varchar(5000);
ALTER TABLE contratos.termo_aditivo_log_rec ADD COLUMN in_situacao varchar(3);

ALTER TABLE contratos.contrato DROP CONSTRAINT ck_contrato_in_situacao;
ALTER TABLE contratos.contrato ADD CONSTRAINT ck_contrato_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EXC'::character varying)::text, ('ADR'::character varying)::text, ('ADC'::character varying)::text])));

ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_evento_gerador;
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_evento_gerador CHECK (((evento_gerador)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EAC'::character varying)::text, ('EAV'::character varying)::text, ('EAR'::character varying)::text, ('CEA'::character varying)::text, ('EXC'::character varying)::text, ('ADR'::character varying)::text, ('ADC'::character varying)::text, ('ADE'::character varying)::text])));

ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_situacao_contrato;
ALTER TABLE contratos.historico ADD constraint ck_historico_situacao_contrato CHECK (((situacao_contrato)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EXC'::character varying)::text, ('ADR'::character varying)::text, ('ADC'::character varying)::text])));

ALTER TABLE contratos.historico ADD COLUMN termo_aditivo_id bigint;
ALTER TABLE contratos.historico ADD CONSTRAINT fk_historico_termo_aditivo_id FOREIGN KEY (termo_aditivo_id) REFERENCES contratos.termo_aditivo(id);
COMMENT ON COLUMN contratos.historico.termo_aditivo_id IS 'Id do termo aditivo relativo ao historico, se houver.';

ALTER TABLE contratos.historico_log_rec ADD COLUMN termo_aditivo_id bigint;

CREATE OR REPLACE FUNCTION contratos.historico_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
       situacao_contrato,
       termo_aditivo_id
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
      NEW.situacao_contrato ,
      NEW.termo_aditivo_id
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
       situacao_contrato ,
       termo_aditivo_id
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
      OLD.situacao_contrato  ,
      OLD.termo_aditivo_id
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $$;
   
CREATE OR REPLACE FUNCTION contratos.termo_aditivo_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
       dt_assinatura , 
       dt_publicacao , 
       entity_id , 
       in_alteracao_clausula , 
       in_alteracao_objeto , 
       in_alteracao_vigencia , 
       in_alteracao_supressao_acrescimo , 
       tx_justificativa , 
       dt_nova_data_fim_vigencia , 
       numero , 
       vl_supressao_acrescimo , 
       tx_descricao_ampliacao_objeto ,
       in_situacao
      ) VALUES (
          1,
 	  nextval('contratos.termo_aditivo_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.contrato_id , 
      NEW.dt_assinatura , 
      NEW.dt_publicacao , 
      NEW.id , 
      NEW.in_alteracao_clausula , 
      NEW.in_alteracao_objeto , 
      NEW.in_alteracao_vigencia , 
      NEW.in_alteracao_supressao_acrescimo , 
      NEW.tx_justificativa , 
      NEW.dt_nova_data_fim_vigencia , 
      NEW.numero , 
      NEW.vl_supressao_acrescimo , 
      NEW.tx_descricao_ampliacao_objeto ,
      NEW.in_situacao
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
       dt_assinatura , 
       dt_publicacao , 
       entity_id , 
       in_alteracao_clausula , 
       in_alteracao_objeto , 
       in_alteracao_vigencia , 
       in_alteracao_supressao_acrescimo , 
       tx_justificativa , 
       dt_nova_data_fim_vigencia , 
       numero , 
       vl_supressao_acrescimo , 
       tx_descricao_ampliacao_objeto ,
       in_situacao
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.termo_aditivo_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.contrato_id , 
      OLD.dt_assinatura , 
      OLD.dt_publicacao , 
      OLD.id , 
      OLD.in_alteracao_clausula , 
      OLD.in_alteracao_objeto , 
      OLD.in_alteracao_vigencia , 
      OLD.in_alteracao_supressao_acrescimo , 
      OLD.tx_justificativa , 
      OLD.dt_nova_data_fim_vigencia , 
      OLD.numero , 
      OLD.vl_supressao_acrescimo , 
      OLD.tx_descricao_ampliacao_objeto  ,
      OLD.in_situacao
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $$;
  
  
