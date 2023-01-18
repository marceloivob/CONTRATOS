ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_evento_gerador;
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_evento_gerador CHECK (((evento_gerador)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EAC'::character varying)::text, ('EAO'::character varying)::text, ('EAL'::character varying)::text])));

ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_situacao_contrato;
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_situacao_contrato CHECK (((situacao_contrato)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('AIO'::character varying)::text, ('AAI'::character varying)::text])));

ALTER TABLE contratos.contrato DROP CONSTRAINT ck_contrato_in_situacao;
ALTER TABLE contratos.contrato ADD CONSTRAINT ck_contrato_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('AIO'::character varying)::text, ('AAI'::character varying)::text])));

ALTER TABLE contratos.contrato DROP COLUMN apto_a_iniciar;
ALTER TABLE contratos.contrato_log_rec DROP COLUMN apto_a_iniciar;

CREATE OR REPLACE FUNCTION contratos.contrato_trigger()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
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
   $function$
;

