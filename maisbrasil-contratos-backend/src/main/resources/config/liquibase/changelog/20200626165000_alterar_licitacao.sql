ALTER TABLE contratos.licitacao ADD COLUMN versao_nr bigint NOT NULL DEFAULT 0;
ALTER TABLE contratos.licitacao_log_rec ADD COLUMN versao_nr bigint NOT NULL DEFAULT 0;

COMMENT ON COLUMN contratos.licitacao.versao_nr is 'Versão do registro da licitacao referente ao controle de versão';
COMMENT ON COLUMN contratos.licitacao_log_rec.versao_nr is 'Versão do registro da licitacao referente ao controle de versão';

CREATE OR REPLACE FUNCTION contratos.licitacao_trigger()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
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
       valor_processo,
       versao_nr
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
      NEW.valor_processo, 
      new.versao_nr
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
       valor_processo,
       versao_nr
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
      OLD.valor_processo,
      old.versao_nr
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $function$
;
