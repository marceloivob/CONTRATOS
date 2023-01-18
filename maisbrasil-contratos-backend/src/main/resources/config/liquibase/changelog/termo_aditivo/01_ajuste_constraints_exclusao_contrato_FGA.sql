
ALTER TABLE contratos.contrato DROP CONSTRAINT ck_contrato_in_situacao;
ALTER TABLE contratos.contrato ADD CONSTRAINT ck_contrato_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EXC'::character varying)::text])));

ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_evento_gerador;
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_evento_gerador CHECK (((evento_gerador)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EAC'::character varying)::text, ('EAV'::character varying)::text, ('EAR'::character varying)::text, ('CEA'::character varying)::text, ('EXC'::character varying)::text])));

ALTER TABLE contratos.historico DROP CONSTRAINT ck_historico_situacao_contrato;
ALTER TABLE contratos.historico ADD constraint ck_historico_situacao_contrato CHECK (((situacao_contrato)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('EXC'::character varying)::text])));