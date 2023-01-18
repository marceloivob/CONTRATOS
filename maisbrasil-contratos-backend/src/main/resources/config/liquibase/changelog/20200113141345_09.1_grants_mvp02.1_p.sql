ALTER TABLE contratos.validacoes_datas_seq OWNER TO owner_siconv_p;
ALTER TABLE contratos.validacoes_datas_log_rec_seq OWNER TO owner_siconv_p;

GRANT SELECT, USAGE  ON SEQUENCE contratos.validacoes_datas_seq  TO owner_siconv_p;
GRANT SELECT, USAGE  ON SEQUENCE contratos.validacoes_datas_log_rec_seq  TO owner_siconv_p;

GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.validacoes_datas  TO owner_siconv_p;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.validacoes_datas_log_rec  TO owner_siconv_p;

ALTER FUNCTION contratos.validacoes_datas_trigger() OWNER TO owner_siconv_p;
ALTER FUNCTION contratos.proposta_trigger() OWNER TO owner_siconv_p;
