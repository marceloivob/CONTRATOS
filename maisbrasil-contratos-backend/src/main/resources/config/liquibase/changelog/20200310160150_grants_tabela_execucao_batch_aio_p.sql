ALTER TABLE contratos.execucao_batch_aio_seq OWNER TO owner_siconv_p;

grant select,usage on contratos.execucao_batch_aio_seq to owner_siconv_p;

GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.execucao_batch_aio  TO owner_siconv_p;