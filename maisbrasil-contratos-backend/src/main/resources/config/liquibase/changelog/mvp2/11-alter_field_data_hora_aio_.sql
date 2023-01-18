ALTER TABLE contratos.aio         ALTER COLUMN adt_data_hora  TYPE timestamp USING ('2020-01-01'::date + adt_data_hora::time);

ALTER TABLE contratos.aio_log_rec ALTER COLUMN adt_data_hora  TYPE timestamp USING ('2000-1-1'::date + adt_data_hora::time);