
ALTER TABLE contratos.contrato DROP CONSTRAINT ck_contrato_in_situacao;
ALTER TABLE contratos.contrato ADD CONSTRAINT ck_contrato_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EXC'::character varying)::text])));
