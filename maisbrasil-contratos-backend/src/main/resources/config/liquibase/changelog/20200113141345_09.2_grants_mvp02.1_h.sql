ALTER TABLE contratos.validacoes_datas_seq OWNER TO owner_siconv4_h;
ALTER TABLE contratos.validacoes_datas_log_rec_seq OWNER TO owner_siconv4_h;

GRANT SELECT, USAGE  ON SEQUENCE contratos.validacoes_datas_seq  TO usr_siconv4_h;
GRANT SELECT, USAGE  ON SEQUENCE contratos.validacoes_datas_log_rec_seq  TO usr_siconv4_h;

GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.validacoes_datas  TO usr_siconv4_h;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.validacoes_datas_log_rec  TO usr_siconv4_h;

ALTER FUNCTION contratos.validacoes_datas_trigger() OWNER TO owner_siconv4_h;
ALTER FUNCTION contratos.proposta_trigger() OWNER TO owner_siconv4_h;