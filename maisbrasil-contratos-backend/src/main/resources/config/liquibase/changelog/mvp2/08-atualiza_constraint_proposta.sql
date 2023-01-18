ALTER TABLE contratos.contrato DROP CONSTRAINT ck_contrato_in_situacao;

ALTER TABLE contratos.contrato ADD CONSTRAINT ck_contrato_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text])));

DROP TRIGGER IF EXISTS tg_aio ON contratos.aio;

CREATE TRIGGER tg_aio AFTER INSERT
OR DELETE
OR UPDATE ON
contratos.aio FOR EACH ROW EXECUTE PROCEDURE contratos.aio_trigger();