CREATE SEQUENCE contratos.execucao_batch_aio_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
CREATE TABLE contratos.execucao_batch_aio (
    id bigint DEFAULT nextval('contratos.execucao_batch_aio_seq'::regclass) NOT NULL,
    inicio_execucao timestamp not null,
    fim_execucao timestamp not null,
    total_processados bigint not null,
    lista_processados character varying(100000),
    total_aptos_emissao_aio bigint not null,
    lista_aptos_emissao_aio character varying(100000),
    total_emissao_sucesso bigint not null,
    total_falha bigint not null,
    lista_falha character varying(100000)
);

COMMENT ON TABLE contratos.execucao_batch_aio IS 'Tabela de registros de execucao da rotina batch para emissão de AIO';

COMMENT ON COLUMN contratos.execucao_batch_aio.inicio_execucao IS 'Horario de ínicio da execucao da rotina batch';
COMMENT ON COLUMN contratos.execucao_batch_aio.fim_execucao IS 'Horario de fim da execucao da rotina batch';
COMMENT ON COLUMN contratos.execucao_batch_aio.total_processados IS 'Total de propostas analisadas pela rotina';
COMMENT ON COLUMN contratos.execucao_batch_aio.lista_processados IS 'Lista dos Ids SICONV das propostas analisadas pela rotina, separadas por ;';
COMMENT ON COLUMN contratos.execucao_batch_aio.total_aptos_emissao_aio IS 'Total de propostas aptas à emissao de AIO';
COMMENT ON COLUMN contratos.execucao_batch_aio.lista_aptos_emissao_aio IS 'Lista dos Ids SICONV das propostas aptas à emissao de AIO, separadas por ;';
COMMENT ON COLUMN contratos.execucao_batch_aio.total_emissao_sucesso IS 'Total de propostas com AIO emitida com sucesso';
COMMENT ON COLUMN contratos.execucao_batch_aio.total_falha IS 'Total de propostas que falharam ao emitir AIO';
COMMENT ON COLUMN contratos.execucao_batch_aio.lista_falha IS 'Lista dos Ids SICONV das propostas que apresentaram falha ao emitir AIO, separadas por ;';
