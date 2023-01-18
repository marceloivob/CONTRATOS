CREATE TABLE contratos.anexo (
    id bigint DEFAULT nextval('contratos.anexo_seq'::regclass) NOT NULL,
    tx_descricao character varying(100) NOT NULL,
    in_tipo_anexo character varying(30) NOT NULL,
    dt_upload date NOT NULL,
    id_cpf_enviado_por character varying(11) NOT NULL,
    nome_enviado_por character varying(60) NOT NULL,
    in_perfil_usuario character varying(10) NOT NULL,
    nm_arquivo character varying(100) NOT NULL,
    caminho character varying(1024) NOT NULL,
    bucket character varying(25) NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    contrato_id bigint,
    termo_aditivo_id bigint,
    CONSTRAINT ck_anexo_in_perfil_usuario CHECK (((in_perfil_usuario)::text = ANY (ARRAY[('PROPONENTE'::character varying)::text, ('CONCEDENTE'::character varying)::text, ('MANDATARIA'::character varying)::text]))),
    CONSTRAINT ck_anexo_in_tipo_anexo CHECK (((in_tipo_anexo)::text = ANY (ARRAY[('INSTRUMENTO_CONTRATUAL'::character varying)::text, ('PUBLICACAO_EXTRATO'::character varying)::text, ('OUTROS'::character varying)::text, ('TERMO_ADITIVO'::character varying)::text, ('PUBLICACAO_TERMO_ADITIVO'::character varying)::text])))
);


CREATE TABLE contratos.anexo_log_rec (
    id bigint DEFAULT nextval('contratos.anexo_log_rec_seq'::regclass) NOT NULL,
    bucket character varying(25) NOT NULL,
    caminho character varying(1024) NOT NULL,
    contratoid bigint,
    dt_upload date NOT NULL,
    entity_id bigint NOT NULL,
    id_cpf_enviado_por character varying(11) NOT NULL,
    in_perfil_usuario character varying(10) NOT NULL,
    in_tipo_anexo character varying(30) NOT NULL,
    nm_arquivo character varying(100) NOT NULL,
    nome_enviado_por character varying(60) NOT NULL,
    termoaditivoid bigint,
    tx_descricao character varying(100) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_anexo_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);


CREATE TABLE contratos.contrato (
    id bigint DEFAULT nextval('contratos.contrato_seq'::regclass) NOT NULL,
    numero character varying(20) NOT NULL,
    objeto character varying(5000) NOT NULL,
    data_assinatura date NOT NULL,
    data_publicacao date NOT NULL,
    inicio_vigencia date NOT NULL,
    fim_vigencia date NOT NULL,
    valor_total numeric(21,2) NOT NULL,
    in_situacao character varying(3) NOT NULL,
    apto_a_iniciar boolean NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    proposta_id bigint,
    CONSTRAINT ck_contrato_in_situacao CHECK (((in_situacao)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('AIO'::character varying)::text])))
);



CREATE TABLE contratos.contrato_log_rec (
    id bigint DEFAULT nextval('contratos.contrato_log_rec_seq'::regclass) NOT NULL,
    apto_a_iniciar boolean NOT NULL,
    data_assinatura date NOT NULL,
    data_publicacao date NOT NULL,
    fim_vigencia date NOT NULL,
    entity_id bigint NOT NULL,
    in_situacao character varying(3) NOT NULL,
    inicio_vigencia date NOT NULL,
    numero character varying(20) NOT NULL,
    objeto character varying(5000) NOT NULL,
    propostaid bigint,
    valor_total numeric(21,2) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_contrato_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);


CREATE TABLE contratos.doc_complementar (
    id bigint DEFAULT nextval('contratos.doc_complementar_seq'::regclass) NOT NULL,
    numero_documento character varying(40) NOT NULL,
    orgao_emissor character varying(100) NOT NULL,
    data_emissao date NOT NULL,
    data_validade date NOT NULL,
    tipo character varying(50) NOT NULL,
    tipo_manifesto_ambiental character varying(50),
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    anexo_id bigint
);


CREATE TABLE contratos.doc_complementar_log_rec (
    id bigint DEFAULT nextval('contratos.doc_complementar_log_rec_seq'::regclass) NOT NULL,
    anexoid bigint,
    data_emissao date NOT NULL,
    data_validade date NOT NULL,
    entity_id bigint NOT NULL,
    numero_documento character varying(40) NOT NULL,
    orgao_emissor character varying(100) NOT NULL,
    tipo character varying(50) NOT NULL,
    tipo_manifesto_ambiental character varying(50),
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_doc_complementar_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);



CREATE TABLE contratos.fornecedor (
    id bigint DEFAULT nextval('contratos.fornecedor_seq'::regclass) NOT NULL,
    razao_social character varying(150) NOT NULL,
    tipo_identificacao character varying(4) NOT NULL,
    identificacao character varying(20) NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL
);



CREATE TABLE contratos.fornecedor_log_rec (
    id bigint DEFAULT nextval('contratos.fornecedor_log_rec_seq'::regclass) NOT NULL,
    entity_id bigint NOT NULL,
    identificacao character varying(20) NOT NULL,
    razao_social character varying(150) NOT NULL,
    tipo_identificacao character varying(4) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_fornecedor_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);



CREATE TABLE contratos.historico (
    id bigint DEFAULT nextval('contratos.historico_seq'::regclass) NOT NULL,
    situacao_contrato character varying(3) NOT NULL,
    evento_gerador character varying(3) NOT NULL,
    nome_responsavel character varying(60) NOT NULL,
    cpf_responsavel character varying(11) NOT NULL,
    data_registro date NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    contrato_id bigint NOT NULL,
    CONSTRAINT ck_historico_evento_gerador CHECK (((evento_gerador)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('AIO'::character varying)::text]))),
    CONSTRAINT ck_historico_situacao_contrato CHECK (((situacao_contrato)::text = ANY (ARRAY[('RAS'::character varying)::text, ('CON'::character varying)::text, ('AIO'::character varying)::text])))
);



CREATE TABLE contratos.historico_log_rec (
    id bigint DEFAULT nextval('contratos.historico_log_rec_seq'::regclass) NOT NULL,
    contratoid bigint NOT NULL,
    cpf_responsavel character varying(11) NOT NULL,
    data_registro date NOT NULL,
    evento_gerador character varying(3) NOT NULL,
    entity_id bigint NOT NULL,
    nome_responsavel character varying(60) NOT NULL,
    situacao_contrato character varying(3) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_historico_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);



CREATE TABLE contratos.licitacao (
    id bigint DEFAULT nextval('contratos.licitacao_seq'::regclass) NOT NULL,
    numero_ano character varying(1024) NOT NULL,
    objeto character varying(1024) NOT NULL,
    valor_processo numeric(21,2) NOT NULL,
    status_processo character varying(20) NOT NULL,
    id_licitacao_fk integer NOT NULL,
    in_situacao character varying(3) NOT NULL,
    modalidade character varying(50),
    regime_contratacao character varying(1024),
    data_publicacao date,
    data_homologacao date,
    processo_de_execucao character varying(50),
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    proposta_id bigint,
    numero_processo character varying(1024),
    situacao_aceite_processo_execu character varying(1024)
);



CREATE TABLE contratos.licitacao_log_rec (
    id bigint DEFAULT nextval('contratos.licitacao_log_rec_seq'::regclass) NOT NULL,
    data_homologacao date,
    data_publicacao date,
    entity_id bigint NOT NULL,
    id_licitacao_fk bigint NOT NULL,
    in_situacao character varying(3) NOT NULL,
    modalidade character varying(50),
    numero_ano character varying(1024) NOT NULL,
    numero_processo character varying(1024),
    objeto character varying(1024) NOT NULL,
    processo_de_execucao character varying(50),
    propostaid bigint,
    regime_contratacao character varying(1024),
    status_processo character varying(20) NOT NULL,
    valor_processo numeric(21,2) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_licitacao_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);



CREATE TABLE contratos.lote (
    id bigint DEFAULT nextval('contratos.lote_seq'::regclass) NOT NULL,
    numero integer NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    contrato_id bigint,
    licitacao_id bigint,
    fornecedor_id bigint
);



CREATE TABLE contratos.lote_log_rec (
    id bigint DEFAULT nextval('contratos.lote_log_rec_seq'::regclass) NOT NULL,
    contratoid bigint,
    fornecedorid bigint,
    entity_id bigint NOT NULL,
    licitacaoid bigint,
    numero bigint NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_lote_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);


CREATE TABLE contratos.meta (
    id bigint DEFAULT nextval('contratos.meta_seq'::regclass) NOT NULL,
    id_meta_vrpl integer NOT NULL,
    tx_descricao character varying(100) NOT NULL,
    numero integer NOT NULL,
    qt_itens numeric(21,2) NOT NULL,
    in_social boolean NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL
);


CREATE TABLE contratos.meta_log_rec (
    id bigint DEFAULT nextval('contratos.meta_log_rec_seq'::regclass) NOT NULL,
    entity_id bigint NOT NULL,
    id_meta_vrpl bigint NOT NULL,
    in_social boolean NOT NULL,
    numero bigint NOT NULL,
    qt_itens numeric(21,2) NOT NULL,
    tx_descricao character varying(100) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_meta_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);


CREATE TABLE contratos.po (
    id bigint DEFAULT nextval('contratos.po_seq'::regclass) NOT NULL,
    id_po_vrpl bigint NOT NULL,
    submeta_id bigint NOT NULL,
    dt_previsao_inicio_obra date NOT NULL,
    qt_meses_duracao_obra smallint NOT NULL,
    in_acompanhamento_eventos boolean,
    in_desonerado boolean,
    sg_localidade character varying(2),
    dt_base_analise date DEFAULT '2019-01-01'::date NOT NULL,
    referencia character varying(8) DEFAULT 'analise'::character varying,
    dt_base_vrpl date,
    dt_previsao_inicio_obra_analise date NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora timestamp without time zone NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    versao_nr integer DEFAULT 0 NOT NULL,
    versao_id character varying(20),
    versao_nm_evento character varying(3),
    versao bigint DEFAULT 0 NOT NULL
);

CREATE TABLE contratos.po_log_rec (
    id bigint DEFAULT nextval('contratos.po_log_rec_seq'::regclass) NOT NULL,
    dt_base_analise date NOT NULL,
    dt_base_vrpl date,
    dt_previsao_inicio_obra date NOT NULL,
    dt_previsao_inicio_obra_analise date NOT NULL,
    entity_id bigint NOT NULL,
    id_po_vrpl bigint NOT NULL,
    in_acompanhamento_eventos boolean,
    in_desonerado boolean,
    qt_meses_duracao_obra smallint NOT NULL,
    referencia character varying(8),
    sg_localidade character varying(2),
    submetaid bigint NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_po_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);




CREATE TABLE contratos.proposta (
    id bigint DEFAULT nextval('contratos.proposta_seq'::regclass) NOT NULL,
    id_siconv integer NOT NULL,
    numero_proposta integer NOT NULL,
    ano_proposta integer NOT NULL,
    valor_global numeric(21,2) NOT NULL,
    valor_repasse numeric(21,2) NOT NULL,
    valor_contrapartida numeric(21,2) NOT NULL,
    modalidade integer NOT NULL,
    nome_objeto character varying(5000),
    numero_convenio integer,
    ano_convenio integer,
    data_assinatura_convenio date,
    codigo_programa character varying(13) NOT NULL,
    nome_programa character varying(255) NOT NULL,
    identificacao_proponente character varying(1024),
    nome_proponente character varying(1024),
    uf character varying(2) NOT NULL,
    pc_min_contrapartida numeric(21,2),
    nome_mandataria character varying(1024),
    categoria character varying(50) NOT NULL,
    nivel_contrato character varying(20),
    spa_homologado boolean NOT NULL,
    apelido_empreendimento character varying(50) NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL
);




CREATE TABLE contratos.proposta_log_rec (
    id bigint DEFAULT nextval('contratos.proposta_log_rec_seq'::regclass) NOT NULL,
    ano_convenio bigint,
    ano_proposta bigint NOT NULL,
    apelido_empreendimento character varying(50) NOT NULL,
    categoria character varying(50) NOT NULL,
    codigo_programa character varying(13) NOT NULL,
    data_assinatura_convenio date,
    entity_id bigint NOT NULL,
    id_siconv bigint NOT NULL,
    identificacao_proponente character varying(1024),
    modalidade bigint NOT NULL,
    nivel_contrato character varying(20),
    nome_mandataria character varying(1024),
    nome_objeto character varying(5000),
    nome_programa character varying(255) NOT NULL,
    nome_proponente character varying(1024),
    numero_convenio bigint,
    numero_proposta bigint NOT NULL,
    pc_min_contrapartida numeric(21,2),
    spa_homologado boolean NOT NULL,
    uf character varying(2) NOT NULL,
    valor_contrapartida numeric(21,2) NOT NULL,
    valor_global numeric(21,2) NOT NULL,
    valor_repasse numeric(21,2) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_proposta_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);



CREATE TABLE contratos.submeta (
    id bigint DEFAULT nextval('contratos.submeta_seq'::regclass) NOT NULL,
    numero integer NOT NULL,
    vl_repasse numeric(21,2) NOT NULL,
    vl_outros numeric(21,2) NOT NULL,
    in_regime_execucao character varying(3) NOT NULL,
    vl_contrapartida numeric(21,2) NOT NULL,
    vl_total_licitado numeric(21,2) NOT NULL,
    in_situacao character varying(3) NOT NULL,
    descricao character varying(100) NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    meta_id bigint,
    proposta_id bigint,
    lote_id bigint
);



CREATE TABLE contratos.submeta_doc_complementar (
    id bigint DEFAULT nextval('contratos.submeta_doc_complementar_seq'::regclass) NOT NULL,
    data_associacao date,
    submeta_id bigint,
    doc_complementar_id bigint
);



CREATE TABLE contratos.submeta_doc_complementar_log_rec (
    id bigint DEFAULT nextval('contratos.submeta_doc_complementar_log_rec_seq'::regclass) NOT NULL,
    data_associacao date,
    doccomplementarid bigint,
    entity_id bigint NOT NULL,
    submetaid bigint,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_submeta_doc_complementar_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);


CREATE TABLE contratos.submeta_log_rec (
    id bigint DEFAULT nextval('contratos.submeta_log_rec_seq'::regclass) NOT NULL,
    descricao character varying(100) NOT NULL,
    entity_id bigint NOT NULL,
    in_regime_execucao character varying(3) NOT NULL,
    in_situacao character varying(3) NOT NULL,
    loteid bigint,
    metaid bigint,
    numero bigint NOT NULL,
    propostaid bigint,
    vl_contrapartida numeric(21,2) NOT NULL,
    vl_outros numeric(21,2) NOT NULL,
    vl_repasse numeric(21,2) NOT NULL,
    vl_total_licitado numeric(21,2) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_submeta_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);


CREATE TABLE contratos.termo_aditivo (
    id bigint DEFAULT nextval('contratos.termo_aditivo_seq'::regclass) NOT NULL,
    in_tipo_de_aditivo character varying(3) NOT NULL,
    in_alteracao_vigencia boolean NOT NULL,
    in_alteracao_clausula boolean NOT NULL,
    in_alteracao_objeto boolean NOT NULL,
    numero character varying(20) NOT NULL,
    data_assinatura date NOT NULL,
    data_publicacao date NOT NULL,
    valor_acrescimo numeric(21,2) NOT NULL,
    valor_supressao numeric(21,2) NOT NULL,
    nova_data_fim_vigencia date NOT NULL,
    justificativa character varying(1000) NOT NULL,
    versao bigint NOT NULL,
    adt_login character varying(60) NOT NULL,
    adt_data_hora date NOT NULL,
    adt_operacao character varying(6) NOT NULL,
    contrato_id bigint,
    CONSTRAINT ck_termo_aditivo_in_tipo_de_aditivo CHECK (((in_tipo_de_aditivo)::text = ANY (ARRAY[('ACR'::character varying)::text, ('SUP'::character varying)::text])))
);



CREATE TABLE contratos.termo_aditivo_log_rec (
    id bigint DEFAULT nextval('contratos.termo_aditivo_log_rec_seq'::regclass) NOT NULL,
    contratoid bigint,
    data_assinatura date NOT NULL,
    data_publicacao date NOT NULL,
    entity_id bigint NOT NULL,
    in_alteracao_clausula boolean NOT NULL,
    in_alteracao_objeto boolean NOT NULL,
    in_alteracao_vigencia boolean NOT NULL,
    in_tipo_de_aditivo character varying(3) NOT NULL,
    justificativa character varying(1000) NOT NULL,
    nova_data_fim_vigencia date NOT NULL,
    numero character varying(20) NOT NULL,
    valor_acrescimo numeric(21,2) NOT NULL,
    valor_supressao numeric(21,2) NOT NULL,
    versao bigint,
    adt_login character varying(60),
    adt_data_hora timestamp without time zone,
    adt_operacao character varying(6),
    CONSTRAINT ck_termo_aditivo_adt_operacao CHECK (((adt_operacao)::text = ANY (ARRAY[('INSERT'::character varying)::text, ('UPDATE'::character varying)::text, ('DELETE'::character varying)::text])))
);



ALTER TABLE ONLY contratos.anexo_log_rec
    ADD CONSTRAINT anexo_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.anexo
    ADD CONSTRAINT anexo_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.contrato_log_rec
    ADD CONSTRAINT contrato_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.contrato
    ADD CONSTRAINT contrato_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.doc_complementar_log_rec
    ADD CONSTRAINT doc_complementar_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.doc_complementar
    ADD CONSTRAINT doc_complementar_pkey PRIMARY KEY (id);

ALTER TABLE ONLY contratos.fornecedor_log_rec
    ADD CONSTRAINT fornecedor_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.fornecedor
    ADD CONSTRAINT fornecedor_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.historico_log_rec
    ADD CONSTRAINT historico_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.historico
    ADD CONSTRAINT historico_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.licitacao_log_rec
    ADD CONSTRAINT licitacao_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.licitacao
    ADD CONSTRAINT licitacao_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.lote_log_rec
    ADD CONSTRAINT lote_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.lote
    ADD CONSTRAINT lote_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.meta_log_rec
    ADD CONSTRAINT meta_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.meta
    ADD CONSTRAINT meta_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.po_log_rec
    ADD CONSTRAINT po_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.po
    ADD CONSTRAINT po_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.proposta_log_rec
    ADD CONSTRAINT proposta_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.proposta
    ADD CONSTRAINT proposta_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.submeta_doc_complementar_log_rec
    ADD CONSTRAINT submeta_doc_complementar_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.submeta_doc_complementar
    ADD CONSTRAINT submeta_doc_complementar_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.submeta_log_rec
    ADD CONSTRAINT submeta_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.submeta
    ADD CONSTRAINT submeta_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.termo_aditivo_log_rec
    ADD CONSTRAINT termo_aditivo_log_rec_pkey PRIMARY KEY (id);


ALTER TABLE ONLY contratos.termo_aditivo
    ADD CONSTRAINT termo_aditivo_pkey PRIMARY KEY (id);


CREATE INDEX ind_contratos_po_submeta_id_idx ON contratos.po USING btree (submeta_id);



ALTER TABLE ONLY contratos.anexo
    ADD CONSTRAINT fk_anexo_contrato_id FOREIGN KEY (contrato_id) REFERENCES contratos.contrato(id);

ALTER TABLE ONLY contratos.anexo
    ADD CONSTRAINT fk_anexo_termo_aditivo_id FOREIGN KEY (termo_aditivo_id) REFERENCES contratos.termo_aditivo(id);

ALTER TABLE ONLY contratos.contrato
    ADD CONSTRAINT fk_contrato_proposta_id FOREIGN KEY (proposta_id) REFERENCES contratos.proposta(id);

ALTER TABLE ONLY contratos.doc_complementar
    ADD CONSTRAINT fk_doc_complementar_anexo_id FOREIGN KEY (anexo_id) REFERENCES contratos.anexo(id);

ALTER TABLE ONLY contratos.historico
    ADD CONSTRAINT fk_historico_contrato_id FOREIGN KEY (contrato_id) REFERENCES contratos.contrato(id);

ALTER TABLE ONLY contratos.licitacao
    ADD CONSTRAINT fk_licitacao_proposta_id FOREIGN KEY (proposta_id) REFERENCES contratos.proposta(id);

ALTER TABLE ONLY contratos.lote
    ADD CONSTRAINT fk_lote_contrato_id FOREIGN KEY (contrato_id) REFERENCES contratos.contrato(id);

ALTER TABLE ONLY contratos.lote
    ADD CONSTRAINT fk_lote_fornecedor_id FOREIGN KEY (fornecedor_id) REFERENCES contratos.fornecedor(id);

ALTER TABLE ONLY contratos.lote
    ADD CONSTRAINT fk_lote_licitacao_id FOREIGN KEY (licitacao_id) REFERENCES contratos.licitacao(id);

ALTER TABLE ONLY contratos.po
    ADD CONSTRAINT fk_po_submeta_id FOREIGN KEY (submeta_id) REFERENCES contratos.submeta(id);

ALTER TABLE ONLY contratos.submeta_doc_complementar
    ADD CONSTRAINT fk_submeta_doc_complementar_doc_complementar_id FOREIGN KEY (doc_complementar_id) REFERENCES contratos.doc_complementar(id);

ALTER TABLE ONLY contratos.submeta_doc_complementar
    ADD CONSTRAINT fk_submeta_doc_complementar_submeta_id FOREIGN KEY (submeta_id) REFERENCES contratos.submeta(id);

ALTER TABLE ONLY contratos.submeta
    ADD CONSTRAINT fk_submeta_lote_id FOREIGN KEY (lote_id) REFERENCES contratos.lote(id);

ALTER TABLE ONLY contratos.submeta
    ADD CONSTRAINT fk_submeta_meta_id FOREIGN KEY (meta_id) REFERENCES contratos.meta(id);

ALTER TABLE ONLY contratos.submeta
    ADD CONSTRAINT fk_submeta_proposta_id FOREIGN KEY (proposta_id) REFERENCES contratos.proposta(id);

ALTER TABLE ONLY contratos.termo_aditivo
    ADD CONSTRAINT fk_termo_aditivo_contrato_id FOREIGN KEY (contrato_id) REFERENCES contratos.contrato(id);

