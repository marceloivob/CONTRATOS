

-- licitacao

ALTER TABLE contratos.licitacao_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.licitacao_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.licitacao_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.licitacao_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.licitacao_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.licitacao_trigger() OWNER TO owner_siconv_p;   


-- submeta_doc_complementar

ALTER TABLE contratos.submeta_doc_complementar_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.submeta_doc_complementar_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.submeta_doc_complementar_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.submeta_doc_complementar_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.submeta_doc_complementar_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.submeta_doc_complementar_trigger() OWNER TO owner_siconv_p;   


-- anexo

ALTER TABLE contratos.anexo_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.anexo_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.anexo_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.anexo_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.anexo_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.anexo_trigger() OWNER TO owner_siconv_p;   


-- lote

ALTER TABLE contratos.lote_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.lote_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.lote_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.lote_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.lote_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.lote_trigger() OWNER TO owner_siconv_p;   


-- proposta

ALTER TABLE contratos.proposta_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.proposta_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.proposta_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.proposta_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.proposta_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.proposta_trigger() OWNER TO owner_siconv_p;   


-- historico

ALTER TABLE contratos.historico_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.historico_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.historico_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.historico_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.historico_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.historico_trigger() OWNER TO owner_siconv_p;   


-- contrato

ALTER TABLE contratos.contrato_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.contrato_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.contrato_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.contrato_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.contrato_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.contrato_trigger() OWNER TO owner_siconv_p;   


-- submeta

ALTER TABLE contratos.submeta_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.submeta_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.submeta_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.submeta_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.submeta_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.submeta_trigger() OWNER TO owner_siconv_p;   


-- termo_aditivo

ALTER TABLE contratos.termo_aditivo_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.termo_aditivo_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.termo_aditivo_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.termo_aditivo_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.termo_aditivo_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.termo_aditivo_trigger() OWNER TO owner_siconv_p;   


-- doc_complementar

ALTER TABLE contratos.doc_complementar_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.doc_complementar_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.doc_complementar_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.doc_complementar_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.doc_complementar_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.doc_complementar_trigger() OWNER TO owner_siconv_p;   


-- meta

ALTER TABLE contratos.meta_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.meta_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.meta_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.meta_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.meta_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.meta_trigger() OWNER TO owner_siconv_p;   


-- fornecedor

ALTER TABLE contratos.fornecedor_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.fornecedor_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.fornecedor_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.fornecedor_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.fornecedor_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.fornecedor_trigger() OWNER TO owner_siconv_p;   


-- po

ALTER TABLE contratos.po_log_rec_seq OWNER TO owner_siconv_p;
GRANT ALL ON SEQUENCE contratos.po_log_rec_seq TO owner_siconv_p;

ALTER TABLE contratos.po_log_rec OWNER TO owner_siconv_p;
GRANT ALL ON TABLE contratos.po_log_rec TO owner_siconv_p;

-- ALTER FUNCTION contratos.po_concorrencia_trigger() OWNER TO owner_siconv_p;
   
ALTER FUNCTION contratos.po_trigger() OWNER TO owner_siconv_p;   
