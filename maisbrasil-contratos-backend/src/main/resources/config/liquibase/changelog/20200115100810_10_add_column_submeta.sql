ALTER TABLE contratos.submeta ADD COLUMN id_submeta_vrpl bigint;
ALTER TABLE contratos.submeta_log_rec ADD COLUMN id_submeta_vrpl bigint;

COMMENT ON COLUMN contratos.submeta.id_submeta_vrpl IS 'Id dessa Submeta no VRPL.';


CREATE OR REPLACE FUNCTION contratos.submeta_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
       vl_total_licitado ,
       id_submeta_vrpl 
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
      NEW.vl_total_licitado ,
      NEW.id_submeta_vrpl 
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
       vl_total_licitado ,
       id_submeta_vrpl 
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
      OLD.vl_total_licitado ,
      OLD.id_submeta_vrpl  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $$;