--TODO

--anexo 
CREATE SEQUENCE contratos.anexo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.anexo ALTER COLUMN id SET DEFAULT nextval('contratos.anexo_seq'::regclass);

--contrato   
CREATE SEQUENCE contratos.contrato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.contrato ALTER COLUMN id SET DEFAULT nextval('contratos.contrato_seq'::regclass);


  --doc_complementar
CREATE SEQUENCE contratos.doc_complementar_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.doc_complementar ALTER COLUMN id SET DEFAULT nextval('contratos.doc_complementar_seq'::regclass);
  
  
  -- fornecedor
  CREATE SEQUENCE contratos.fornecedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.fornecedor ALTER COLUMN id SET DEFAULT nextval('contratos.fornecedor_seq'::regclass);

  
  -- historico
    CREATE SEQUENCE contratos.historico_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.historico ALTER COLUMN id SET DEFAULT nextval('contratos.historico_seq'::regclass);

  -- licitacao 
    CREATE SEQUENCE contratos.licitacao_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.licitacao ALTER COLUMN id SET DEFAULT nextval('contratos.licitacao_seq'::regclass);

  -- lote
    CREATE SEQUENCE contratos.lote_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.lote ALTER COLUMN id SET DEFAULT nextval('contratos.lote_seq'::regclass);

  --meta
    CREATE SEQUENCE contratos.meta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.meta ALTER COLUMN id SET DEFAULT nextval('contratos.meta_seq'::regclass);
  
  -- proposta
    CREATE SEQUENCE contratos.proposta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.proposta ALTER COLUMN id SET DEFAULT nextval('contratos.proposta_seq'::regclass);

 
  --submeta 
  CREATE SEQUENCE contratos.submeta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.submeta ALTER COLUMN id SET DEFAULT nextval('contratos.submeta_seq'::regclass);

  
  --submeta_doc_complementar
    CREATE SEQUENCE contratos.submeta_doc_complementar_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.submeta_doc_complementar ALTER COLUMN id SET DEFAULT nextval('contratos.submeta_doc_complementar_seq'::regclass);

  -- termo_aditivo
    CREATE SEQUENCE contratos.termo_aditivo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.termo_aditivo ALTER COLUMN id SET DEFAULT nextval('contratos.termo_aditivo_seq'::regclass);


-- Anexo CONSTRAINTS  
ALTER TABLE contratos.anexo ADD CONSTRAINT ck_anexo_in_perfil_usuario 
CHECK (((in_perfil_usuario)::text = ANY (ARRAY[
				('PROPONENTE'::character varying)::text, 
				('CONCEDENTE'::character varying)::text, 
				('MANDATARIA'::character varying)::text
		])));


ALTER TABLE contratos.anexo ADD CONSTRAINT ck_anexo_in_tipo_anexo 
CHECK (((in_tipo_anexo)::text = ANY (ARRAY[
				('INSTRUMENTO_CONTRATUAL'::character varying)::text, 
				('PUBLICACAO_EXTRATO'::character varying)::text, 
				('OUTROS'::character varying)::text,
				('TERMO_ADITIVO'::character varying)::text,
				('PUBLICACAO_TERMO_ADITIVO'::character varying)::text
		])));
		

-- Contrato CONSTRAINTS  
ALTER TABLE contratos.contrato ADD CONSTRAINT ck_contrato_in_situacao
CHECK (((in_situacao)::text = ANY (ARRAY[
				('RAS'::character varying)::text, 
				('CON'::character varying)::text,
				('AIO'::character varying)::text
		])));

-- Historico CONSTRAINTS  
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_situacao_contrato
CHECK (((situacao_contrato)::text = ANY (ARRAY[
				('RAS'::character varying)::text, 
				('CON'::character varying)::text,
				('AIO'::character varying)::text
		])));
		
ALTER TABLE contratos.historico ADD CONSTRAINT ck_historico_evento_gerador
CHECK (((evento_gerador)::text = ANY (ARRAY[
				('RAS'::character varying)::text, 
				('CON'::character varying)::text,
				('AIO'::character varying)::text
		])));


-- TermoAditivo CONSTRAINTS  
ALTER TABLE contratos.termo_aditivo ADD CONSTRAINT ck_termo_aditivo_in_tipo_de_aditivo
CHECK (((in_tipo_de_aditivo)::text = ANY (ARRAY[
				('ACR'::character varying)::text, 
				('SUP'::character varying)::text
		])));


-- Escopo não CONSTRAINT CHECK não realizado 
---- Submeta: in_regime_execucao, in_situacao
---- Licitacao: in_situacao


-- INSERT INTO contratos.proposta (id, id_siconv,numero_proposta,ano_proposta,valor_global,valor_repasse,valor_contrapartida,modalidade,nome_objeto,numero_convenio,ano_convenio,data_assinatura_convenio,codigo_programa,nome_programa,identificacao_proponente,nome_proponente,uf,pc_min_contrapartida,nome_mandataria,categoria,nivel_contrato,spa_homologado,adt_login,adt_data_hora,adt_operacao,versao,apelido_empreendimento) VALUES 
-- (1, 1332647,31755,2018,259984.73,257271.43,2713.30,2,'Pavimentação de ruas do Município de Sapiranga.',867607,2018,'2018-05-18','5600020180001','PLANEJAMENTO URBANO','87366159000102','MUNICIPIO DE SAPIRANGA','RS',1.00,'CAIXA ECONOMICA FEDERAL','OBRAS_SERVICOS_ENGENHARIA','CONTRATO_NIVEL_I',true,'00442999070','2019-10-04 09:20:22.041','INSERT',1,'Pavimentação ruas Espírito Santo e Martin Berg');
-- 
-- INSERT INTO contratos.contrato
-- (numero, objeto, data_assinatura, data_publicacao, inicio_vigencia, fim_vigencia, valor_total, in_situacao, apto_a_iniciar, versao, adt_login, adt_data_hora, adt_operacao, proposta_id)
-- VALUES('001', 'objeto', now(), now(), now(), now(), 123.05, 'RAS', false, 0, '', now(), '', 1);



CREATE TABLE contratos.po (
	id bigserial NOT NULL, -- Coluna de id
	id_po_vrpl int8 NOT NULL, -- Identificador da PO na fase de Análise
	submeta_id int8 NOT NULL, -- Chave estrangeira para Submeta
	dt_previsao_inicio_obra date NOT NULL, -- Data de previsao do inicio da obra do VRPL
	qt_meses_duracao_obra int2 NOT NULL, -- Quantidade de meses de duracao da obra
	in_acompanhamento_eventos bool NULL, -- Indica se PO acompanhada por eventos
	in_desonerado bool NULL, -- Indica se PO desonerada
	sg_localidade varchar(2) NULL, -- Sigla da localidade
	dt_base_analise date NOT NULL DEFAULT '2019-01-01'::date, -- Data base oriunda da fase de análise
	referencia varchar(8) NULL DEFAULT 'analise'::character varying, -- Referência (analise/database) para apresentação dos preços dos serviços
	dt_base_vrpl date NULL, -- Data base preenchida no VRPL
	dt_previsao_inicio_obra_analise date NOT NULL, -- Data de previsao do inicio da obra da Análise
	
	adt_login varchar(60) NOT NULL, -- Usuário que alterou o registro
	adt_data_hora timestamp NOT NULL, -- Data/Hora de modificação do registro
	adt_operacao varchar(6) NOT NULL, -- Operacão (INSERT/UPDATE/DELETE) da última ação no registro
	
	versao_nr int4 NOT NULL DEFAULT 0, -- Identificador do conjunto de dados referente ao versionamento
	versao_id varchar(20) NULL, -- Identificador de agrupamento das versões
	versao_nm_evento varchar(3) NULL, -- Nome do evento que deu origem à nova versão
	versao int8 NOT NULL DEFAULT 0, -- Versão usada para controlar a concorrência
	
	CONSTRAINT po_pkey PRIMARY KEY (id),
	CONSTRAINT fk_po_submeta_id FOREIGN KEY (submeta_id) REFERENCES contratos.submeta(id)
);
CREATE INDEX ind_contratos_po_submeta_id_idx ON contratos.po USING btree (submeta_id);

--po
CREATE SEQUENCE contratos.po_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

   
   ALTER TABLE contratos.po ALTER COLUMN id SET DEFAULT nextval('contratos.po_seq'::regclass);

