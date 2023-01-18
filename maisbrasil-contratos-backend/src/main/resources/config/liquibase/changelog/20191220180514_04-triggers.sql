CREATE FUNCTION contratos.anexo_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;


CREATE FUNCTION contratos.contrato_trigger() RETURNS trigger
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
   $$;



CREATE FUNCTION contratos.doc_complementar_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;



CREATE FUNCTION contratos.fornecedor_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;



CREATE FUNCTION contratos.historico_trigger() RETURNS trigger
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
   $$;



CREATE FUNCTION contratos.licitacao_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;



CREATE FUNCTION contratos.lote_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;



CREATE FUNCTION contratos.meta_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;



CREATE FUNCTION contratos.po_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;



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
   $$;




CREATE FUNCTION contratos.submeta_doc_complementar_trigger() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
   $$;




CREATE FUNCTION contratos.submeta_trigger() RETURNS trigger
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
   $$;



CREATE FUNCTION contratos.termo_aditivo_trigger() RETURNS trigger
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
   $$;



CREATE TRIGGER tg_anexo AFTER INSERT OR DELETE OR UPDATE ON contratos.anexo FOR EACH ROW EXECUTE PROCEDURE contratos.anexo_trigger();

CREATE TRIGGER tg_contrato AFTER INSERT OR DELETE OR UPDATE ON contratos.contrato FOR EACH ROW EXECUTE PROCEDURE contratos.contrato_trigger();

CREATE TRIGGER tg_doc_complementar AFTER INSERT OR DELETE OR UPDATE ON contratos.doc_complementar FOR EACH ROW EXECUTE PROCEDURE contratos.doc_complementar_trigger();

CREATE TRIGGER tg_fornecedor AFTER INSERT OR DELETE OR UPDATE ON contratos.fornecedor FOR EACH ROW EXECUTE PROCEDURE contratos.fornecedor_trigger();

CREATE TRIGGER tg_historico AFTER INSERT OR DELETE OR UPDATE ON contratos.historico FOR EACH ROW EXECUTE PROCEDURE contratos.historico_trigger();

CREATE TRIGGER tg_licitacao AFTER INSERT OR DELETE OR UPDATE ON contratos.licitacao FOR EACH ROW EXECUTE PROCEDURE contratos.licitacao_trigger();

CREATE TRIGGER tg_lote AFTER INSERT OR DELETE OR UPDATE ON contratos.lote FOR EACH ROW EXECUTE PROCEDURE contratos.lote_trigger();

CREATE TRIGGER tg_meta AFTER INSERT OR DELETE OR UPDATE ON contratos.meta FOR EACH ROW EXECUTE PROCEDURE contratos.meta_trigger();

CREATE TRIGGER tg_po AFTER INSERT OR DELETE OR UPDATE ON contratos.po FOR EACH ROW EXECUTE PROCEDURE contratos.po_trigger();

CREATE TRIGGER tg_proposta AFTER INSERT OR DELETE OR UPDATE ON contratos.proposta FOR EACH ROW EXECUTE PROCEDURE contratos.proposta_trigger();

CREATE TRIGGER tg_submeta AFTER INSERT OR DELETE OR UPDATE ON contratos.submeta FOR EACH ROW EXECUTE PROCEDURE contratos.submeta_trigger();

CREATE TRIGGER tg_submeta_doc_complementar AFTER INSERT OR DELETE OR UPDATE ON contratos.submeta_doc_complementar FOR EACH ROW EXECUTE PROCEDURE contratos.submeta_doc_complementar_trigger();

CREATE TRIGGER tg_termo_aditivo AFTER INSERT OR DELETE OR UPDATE ON contratos.termo_aditivo FOR EACH ROW EXECUTE PROCEDURE contratos.termo_aditivo_trigger();
