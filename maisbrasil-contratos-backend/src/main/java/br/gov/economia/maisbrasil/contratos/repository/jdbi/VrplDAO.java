package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import br.gov.economia.maisbrasil.contratos.domain.integracao.FornecedorIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LicitacaoIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LoteIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.MetaIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PoIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PropostaIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.SubmetaIntegracao;


public interface VrplDAO {

	@SqlQuery("SELECT * FROM siconv.vrpl_proposta WHERE id_siconv = :idSiconv and versao_in_atual = true")
	@RegisterFieldMapper(PropostaIntegracao.class)
	Optional<PropostaIntegracao> recuperarPropostaPorId(@Bind("idSiconv") Long idSiconv);

	@SqlQuery("SELECT lic.*, h.dt_registro as data_aceite FROM siconv.vrpl_licitacao lic, siconv.vrpl_proposta pro, siconv.vrpl_historico_licitacao h WHERE lic.in_situacao = 'ACT' and h.licitacao_fk = lic.id and h.in_evento = 'ACT' and h.dt_registro = (select max(h2.dt_registro) from siconv.vrpl_historico_licitacao h2 where h2.licitacao_fk = lic.id and h2.in_evento = 'ACT') and pro.id_siconv = :idProposta and pro.id = lic.proposta_fk and pro.versao_in_atual = true")
	@RegisterFieldMapper(LicitacaoIntegracao.class)
	List<LicitacaoIntegracao> recuperarLicitacoesAceitasDaProposta(@Bind("idProposta") Long idProposta);
	
	@SqlQuery("SELECT forn.* FROM siconv.vrpl_fornecedor forn WHERE forn.licitacao_fk = :idLicitacao")
	@RegisterFieldMapper(FornecedorIntegracao.class)
	List<FornecedorIntegracao> recuperarFornecedoresDaLicitacao(@Bind("idLicitacao") Long idLicitacao);

	@SqlQuery("SELECT lot.* FROM siconv.vrpl_lote_licitacao lot WHERE lot.licitacao_fk = :idLicitacao")
	@RegisterFieldMapper(LoteIntegracao.class)
	List<LoteIntegracao> recuperarLotesDaLicitacaoLote(@Bind("idLicitacao") Long idLicitacao);
	
	@SqlQuery("SELECT sub.* FROM siconv.vrpl_submeta sub WHERE sub.vrpl_lote_licitacao_fk = :idLote")
	@RegisterFieldMapper(SubmetaIntegracao.class)
	List<SubmetaIntegracao> recuperarSubmetasDoLote(@Bind("idLote") Long idLote);
	
	@SqlQuery("SELECT met.* FROM siconv.vrpl_meta met, siconv.vrpl_submeta sub WHERE sub.id = :idSubmeta and sub.meta_fk = met.id")
	@RegisterFieldMapper(MetaIntegracao.class)
	MetaIntegracao recuperarMetaDaSubmeta(@Bind("idSubmeta") Long idSubmeta);
	
	@SqlQuery("SELECT forn.* FROM siconv.vrpl_fornecedor forn WHERE forn.id = :idFornecedor")
	@RegisterFieldMapper(FornecedorIntegracao.class)
	FornecedorIntegracao recuperarFornecedorPorId(@Bind("idFornecedor") Long idFornecedor);
	
	@SqlQuery("SELECT * FROM siconv.vrpl_po WHERE submeta_fk = :idSubmeta")
	@RegisterFieldMapper(PoIntegracao.class)
	PoIntegracao recuperarPoPorSubmeta(@Bind("idSubmeta") Long idSubmeta);
	
	@SqlQuery("SELECT numero_processo FROM siconv.licitacao WHERE id = :idLicitacaoSiconv")
	Optional<String> recuperarNumeroProcesso(@Bind("idLicitacaoSiconv") Long idLicitacaoSiconv);
	
	@SqlQuery("SELECT situacao_aceite_processo_execu FROM siconv.licitacao WHERE id = :idLicitacaoSiconv")
	Optional<String> recuperarSituacaoAceiteProcessoExecucao(@Bind("idLicitacaoSiconv") Long idLicitacaoSiconv);
}
