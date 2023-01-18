alter table contratos.proposta 
add column TERMO_COMPROMISSO_TEM_MANDATAR BOOLEAN default false;

comment on column contratos.proposta.TERMO_COMPROMISSO_TEM_MANDATAR is 
'Indica se a modalidade tem instituicao mandatária (campo usado na modalidade termo de compromisso)';

alter table contratos.proposta_log_rec
add column TERMO_COMPROMISSO_TEM_MANDATAR BOOLEAN default false;

comment on column contratos.proposta_log_rec.TERMO_COMPROMISSO_TEM_MANDATAR is 
'Indica se a modalidade tem instituicao mandatária (campo usado na modalidade termo de compromisso)';
