

-- DROP TRIGGER tg_licitacao_concorrencia ON contratos.licitacao;
-- DROP FUNCTION contratos.licitacao_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.licitacao_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.licitacao WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela licitacao valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela licitacao valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_licitacao_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.licitacao
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.licitacao_concorrencia_trigger();


-- DROP TRIGGER tg_submeta_doc_complementar_concorrencia ON contratos.submeta_doc_complementar;
-- DROP FUNCTION contratos.submeta_doc_complementar_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.submeta_doc_complementar_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.submeta_doc_complementar WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela submeta_doc_complementar valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela submeta_doc_complementar valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_submeta_doc_complementar_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.submeta_doc_complementar
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.submeta_doc_complementar_concorrencia_trigger();


-- DROP TRIGGER tg_anexo_concorrencia ON contratos.anexo;
-- DROP FUNCTION contratos.anexo_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.anexo_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.anexo WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela anexo valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela anexo valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_anexo_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.anexo
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.anexo_concorrencia_trigger();


-- DROP TRIGGER tg_lote_concorrencia ON contratos.lote;
-- DROP FUNCTION contratos.lote_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.lote_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.lote WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela lote valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela lote valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_lote_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.lote
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.lote_concorrencia_trigger();


-- DROP TRIGGER tg_doc_complementar_concorrencia ON contratos.doc_complementar;
-- DROP FUNCTION contratos.doc_complementar_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.doc_complementar_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.doc_complementar WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela doc_complementar valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela doc_complementar valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_doc_complementar_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.doc_complementar
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.doc_complementar_concorrencia_trigger();


-- DROP TRIGGER tg_proposta_concorrencia ON contratos.proposta;
-- DROP FUNCTION contratos.proposta_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.proposta_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.proposta WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela proposta valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela proposta valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_proposta_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.proposta
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.proposta_concorrencia_trigger();


-- DROP TRIGGER tg_historico_concorrencia ON contratos.historico;
-- DROP FUNCTION contratos.historico_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.historico_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.historico WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela historico valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela historico valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_historico_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.historico
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.historico_concorrencia_trigger();


-- DROP TRIGGER tg_contrato_concorrencia ON contratos.contrato;
-- DROP FUNCTION contratos.contrato_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.contrato_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.contrato WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela contrato valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela contrato valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_contrato_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.contrato
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.contrato_concorrencia_trigger();


-- DROP TRIGGER tg_submeta_concorrencia ON contratos.submeta;
-- DROP FUNCTION contratos.submeta_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.submeta_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.submeta WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela submeta valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela submeta valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_submeta_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.submeta
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.submeta_concorrencia_trigger();


-- DROP TRIGGER tg_termo_aditivo_concorrencia ON contratos.termo_aditivo;
-- DROP FUNCTION contratos.termo_aditivo_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.termo_aditivo_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.termo_aditivo WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela termo_aditivo valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela termo_aditivo valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_termo_aditivo_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.termo_aditivo
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.termo_aditivo_concorrencia_trigger();


-- DROP TRIGGER tg_meta_concorrencia ON contratos.meta;
-- DROP FUNCTION contratos.meta_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.meta_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.meta WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela meta valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela meta valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_meta_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.meta
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.meta_concorrencia_trigger();


-- DROP TRIGGER tg_fornecedor_concorrencia ON contratos.fornecedor;
-- DROP FUNCTION contratos.fornecedor_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.fornecedor_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.fornecedor WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela fornecedor valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela fornecedor valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_fornecedor_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.fornecedor
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.fornecedor_concorrencia_trigger();


-- DROP TRIGGER tg_po_concorrencia ON contratos.po;
-- DROP FUNCTION contratos.po_concorrencia_trigger();
 
 
 CREATE OR REPLACE FUNCTION contratos.po_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.po WHERE id = OLD.id;
	   
	   IF wl_versao is null then
       	   wl_versao := 0;
       END IF;
	   
       IF (TG_OP = 'UPDATE') THEN
	   		IF (wl_versao + 1) <> NEW.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela po valor atual -> %', (wl_versao + 1), NEW.versao
		  	   USING ERRCODE = '23501';
	   		END IF;   
	   		RETURN NEW;
	   ELSIF (TG_OP = 'DELETE') THEN
	   	    IF (wl_versao) <> OLD.versao then 
		       RAISE EXCEPTION 'Versao invalida da versao --> % na tabela po valor atual -> %', wl_versao, OLD.versao
		  	   USING ERRCODE = '23501';
	   		END IF;
	   		RETURN OLD;
	   END IF;
	   
	 RETURN NULL;
 	 END;
   $BODY$
   LANGUAGE plpgsql VOLATILE
   COST 100;
   
   CREATE TRIGGER tg_po_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.po
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.po_concorrencia_trigger();
