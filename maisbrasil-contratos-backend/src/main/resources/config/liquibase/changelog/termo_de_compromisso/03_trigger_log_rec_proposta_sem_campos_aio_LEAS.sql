CREATE OR REPLACE FUNCTION contratos.proposta_trigger()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
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
       termo_compromisso_tem_mandatar
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
      NEW.termo_compromisso_tem_mandatar
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
       termo_compromisso_tem_mandatar
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
      OLD.termo_compromisso_tem_mandatar
       );
       RETURN OLD;
   END IF;
   RETURN NULL;
   END;
   $function$
;

