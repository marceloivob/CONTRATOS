ALTER TABLE contratos.anexo
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;

ALTER TABLE contratos.contrato
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;
   
ALTER TABLE contratos.doc_complementar
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;
   
ALTER TABLE contratos.fornecedor
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;   
   
ALTER TABLE contratos.licitacao
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;
   
ALTER TABLE contratos.lote
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;
   
ALTER TABLE contratos.meta
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;   

ALTER TABLE contratos.proposta
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;
   
ALTER TABLE contratos.submeta
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;

ALTER TABLE contratos.termo_aditivo
    ALTER COLUMN adt_data_hora SET DATA TYPE timestamp;
  
      
