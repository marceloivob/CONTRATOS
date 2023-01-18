
 CREATE OR REPLACE FUNCTION contratos.aio_concorrencia_trigger()
  RETURNS trigger AS 
  $BODY$
  DECLARE wl_versao integer;
     BEGIN
	   SELECT versao into wl_versao from contratos.aio WHERE id = OLD.id;
	   
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
   
   CREATE TRIGGER tg_aio_concorrencia
   BEFORE UPDATE OR DELETE
   ON contratos.aio
   FOR EACH ROW
   EXECUTE PROCEDURE contratos.aio_concorrencia_trigger();

