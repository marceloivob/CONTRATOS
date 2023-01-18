CREATE OR REPLACE FUNCTION contratos.validacoes_datas_trigger() RETURNS trigger
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