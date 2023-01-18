CREATE SEQUENCE contratos.aio_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1;


CREATE TABLE contratos.aio (
    id int8 NOT NULL DEFAULT nextval('contratos.aio_seq'::regclass),
    proposta_fk int8 NOT null,
    contrato_emissor_fk int8 NULL,
    dt_emissao_aio date NULL,
    emissor varchar NOT NULL,
    versao int8 NOT NULL,
    adt_login varchar(30) NOT NULL,
    adt_data_hora time NOT NULL,
    adt_operacao varchar(6) NOT null,
    
    CONSTRAINT aio_pk PRIMARY KEY (id),
    CONSTRAINT ck_aio_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::varchar)::text, ('UPDATE'::varchar)::text, ('DELETE'::varchar)::text]))),
    CONSTRAINT aio_contrato_fk FOREIGN KEY (contrato_emissor_fk) REFERENCES contratos.contrato(id),
    CONSTRAINT aio_proposta_fk FOREIGN KEY (proposta_fk) REFERENCES contratos.proposta(id)
    
);
COMMENT ON TABLE contratos.aio IS 'Tabela contendo informações sobre a Autorização para Início da Obra (AIO)';

COMMENT ON COLUMN contratos.aio.id IS 'Identificador do AIO';
COMMENT ON COLUMN contratos.aio.proposta_fk IS 'Identificador da Proposta';
COMMENT ON COLUMN contratos.aio.contrato_emissor_fk IS 'Identificador do Contrato Emissor do AIO';
COMMENT ON COLUMN contratos.aio.dt_emissao_aio IS 'Data de Emissão do AIO';
COMMENT ON COLUMN contratos.aio.adt_login IS 'Usuário que alterou o registro';
COMMENT ON COLUMN contratos.aio.adt_data_hora IS 'Data/Hora de modificação do registro';
COMMENT ON COLUMN contratos.aio.adt_operacao IS 'Operacão (INSERT/UPDATE/DELETE) da última ação no registro';

