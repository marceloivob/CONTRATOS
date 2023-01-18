CREATE OR REPLACE FUNCTION contratos.aio_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
   DECLARE wl_versao integer;
   DECLARE wl_id integer;
   BEGIN
   -- Aqui temos um bloco IF que confirmará o tipo de operação.
   IF (TG_OP = 'INSERT') THEN
       INSERT INTO contratos.aio_log_rec (
       versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
	   proposta_fk,
       contrato_emissor_fk,
       dt_emissao_aio,
       emissor,
       entity_id  
      ) VALUES (
          1,
 	  nextval('contratos.aio_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario') ,
	  LOCALTIMESTAMP ,
	  NEW.adt_operacao ,
      NEW.proposta_fk , 
      NEW.contrato_emissor_fk , 
      NEW.dt_emissao_aio , 
      NEW.emissor , 
      NEW.id 
       );
       RETURN NEW;
   -- Aqui temos um bloco IF que confirmará o tipo de operação UPDATE.
   ELSIF (TG_OP = 'UPDATE') or (TG_OP = 'DELETE') THEN
       --Verifica qual vai ser a versão do registro na tabela de log
       SELECT max(versao) into wl_versao from contratos.aio_log_rec WHERE entity_id = OLD.id;
       
       INSERT INTO contratos.aio_log_rec (
 	   versao , 
	   id ,
	   adt_login ,
	   adt_data_hora ,
	   adt_operacao ,
	   proposta_fk,
       contrato_emissor_fk,
       dt_emissao_aio,
       emissor,
       entity_id  
       ) VALUES (
 	  OLD.versao,
	  nextval('contratos.aio_log_rec_seq'), 
	  current_setting('contratos.cpf_usuario'),
	  LOCALTIMESTAMP ,
	  TG_OP ,
      OLD.proposta_fk , 
      OLD.contrato_emissor_fk , 
      OLD.dt_emissao_aio , 
      OLD.emissor , 
      OLD.id  
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $$;