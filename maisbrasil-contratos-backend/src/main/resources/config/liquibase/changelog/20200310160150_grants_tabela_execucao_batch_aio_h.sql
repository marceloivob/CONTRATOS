ALTER TABLE contratos.execucao_batch_aio_seq OWNER TO owner_siconv4_h;

grant select,usage on contratos.execucao_batch_aio_seq to owner_siconv4_h;
grant select,usage on contratos.execucao_batch_aio_seq to usr_siconv4_h;

GRANT SELECT, INSERT, UPDATE, DELETE  ON TABLE contratos.execucao_batch_aio  TO usr_siconv4_h;