ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_evento_gerador;
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_evento_gerador CHECK (((evento_gerador)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EAC'::character varying)::text, ('EAV'::character varying)::text, ('EAR'::character varying)::text])));
