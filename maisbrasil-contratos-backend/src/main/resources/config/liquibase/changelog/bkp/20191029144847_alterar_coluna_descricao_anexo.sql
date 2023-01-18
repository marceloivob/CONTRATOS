-- so pode ser feito desta maneira sem drop por estar aumentando o tamanho da coluna
ALTER TABLE contratos.anexo ALTER COLUMN tx_descricao TYPE character varying(100);
ALTER TABLE contratos.anexo_log_rec ALTER COLUMN tx_descricao TYPE character varying(100);