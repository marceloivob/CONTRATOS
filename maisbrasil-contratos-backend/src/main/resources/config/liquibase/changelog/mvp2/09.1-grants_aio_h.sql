ALTER TABLE contratos.aio_log_rec_seq OWNER TO owner_siconv4_h;

GRANT SELECT, USAGE  ON SEQUENCE contratos.aio_log_rec_seq  TO owner_siconv4_h;

GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.aio  TO owner_siconv4_h;
GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.aio_log_rec  TO owner_siconv4_h;

ALTER FUNCTION contratos.aio_concorrencia_trigger() OWNER TO owner_siconv4_h;
ALTER FUNCTION contratos.aio_trigger() OWNER TO owner_siconv4_h;
