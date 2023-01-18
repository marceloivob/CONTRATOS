
ALTER TABLE contratos.contrato ADD COLUMN dt_fim_vigencia_original date;
COMMENT ON COLUMN contratos.contrato.dt_fim_vigencia_original IS 'Data final de vigencia original do instrumento contratual. Não é alterada por termo aditivo.';

ALTER TABLE contratos.contrato_log_rec ADD COLUMN dt_fim_vigencia_original date;
   
CREATE OR REPLACE FUNCTION contratos.contrato_trigger()
 RETURNS trigger
 LANGUAGE plpgsql
AS $$
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
       valor_total ,
       dt_fim_vigencia_original
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
      NEW.valor_total ,
      NEW.dt_fim_vigencia_original
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
       valor_total ,
       dt_fim_vigencia_original
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
      OLD.valor_total  ,
      OLD.dt_fim_vigencia_original
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;   
   $$;
  
   set local contratos.cpf_usuario TO 'SS3460524';
   UPDATE contratos.contrato SET dt_fim_vigencia_original = fim_vigencia where fim_vigencia != null;
   
   
  
