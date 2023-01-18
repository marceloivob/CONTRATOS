ALTER TABLE contratos.proposta DROP COLUMN IF EXISTS id_contrato_emissor_aio;
ALTER TABLE contratos.proposta DROP COLUMN IF EXISTS data_emissao_aio;

ALTER TABLE contratos.proposta_log_rec DROP COLUMN IF EXISTS id_contrato_emissor_aio;
ALTER TABLE contratos.proposta_log_rec DROP COLUMN IF EXISTS data_emissao_aio;
