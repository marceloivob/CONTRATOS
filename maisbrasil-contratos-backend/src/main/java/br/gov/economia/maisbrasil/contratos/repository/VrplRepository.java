package br.gov.economia.maisbrasil.contratos.repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.integracao.FornecedorIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LicitacaoIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LoteIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.MetaIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PoIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PropostaIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.SubmetaIntegracao;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.VrplDAO;

@Repository
@Transactional
public class VrplRepository {

	@Autowired
	@Qualifier("jdbiForVRPL")
	private Jdbi jdbi;

	public Optional<PropostaIntegracao> recuperarPropostaPorId(Long idPropostaSiconv) {
		return jdbi.withHandle(transaction -> {
			Optional<PropostaIntegracao> propostaPorId = transaction.attach(VrplDAO.class).recuperarPropostaPorId(idPropostaSiconv);

			return propostaPorId;
		});
	}

	public List<LicitacaoIntegracao> recuperarLicitacoesAceitasDaProposta(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<LicitacaoIntegracao> licitacoesAceitasDaProposta = transaction.attach(VrplDAO.class)
					.recuperarLicitacoesAceitasDaProposta(idProposta);

			return licitacoesAceitasDaProposta;
		});
	}

	public List<FornecedorIntegracao> recuperarFornecedoresDaLicitacao(Long idLicitacao) {
		return jdbi.withHandle(transaction -> {
			List<FornecedorIntegracao> fornecedoresDaLicitacao = transaction.attach(VrplDAO.class)
					.recuperarFornecedoresDaLicitacao(idLicitacao);

			return fornecedoresDaLicitacao;
		});
	}

	public List<LoteIntegracao> recuperarLotesDaLicitacaoLote(Long idLicitacao) {
		return jdbi.withHandle(transaction -> {
			List<LoteIntegracao> lotesDaLicitacao = transaction.attach(VrplDAO.class)
					.recuperarLotesDaLicitacaoLote(idLicitacao);

			return lotesDaLicitacao;
		});
	}

	public List<SubmetaIntegracao> recuperarSubmetasDoLote(Long idLote) {
		return jdbi.withHandle(transaction -> {
			List<SubmetaIntegracao> submetasDoLote = transaction.attach(VrplDAO.class).recuperarSubmetasDoLote(idLote);

			return submetasDoLote;
		});
	}

	public MetaIntegracao recuperarMetaDaSubmeta(Long idSubmeta) {
		return jdbi.withHandle(transaction -> {
			MetaIntegracao metaDaSubmeta = transaction.attach(VrplDAO.class).recuperarMetaDaSubmeta(idSubmeta);

			return metaDaSubmeta;
		});
	}

	public FornecedorIntegracao recuperarFornecedorPorId(Long idFornecedor) {
		return jdbi.withHandle(transaction -> {
			FornecedorIntegracao fornecedorPorId = transaction.attach(VrplDAO.class)
					.recuperarFornecedorPorId(idFornecedor);

			return fornecedorPorId;
		});
	}

	public PoIntegracao recuperarPoPorSubmeta(Long idSubmeta) {
		return jdbi.withHandle(transaction -> {
			PoIntegracao poPorSubmeta = transaction.attach(VrplDAO.class).recuperarPoPorSubmeta(idSubmeta);

			return poPorSubmeta;
		});
	}

	public Optional<String> recuperarNumeroProcesso(Long idLicitacaoSiconv) {
		return jdbi.withHandle(transaction -> {
			Optional<String> numeroProcesso = transaction.attach(VrplDAO.class)
					.recuperarNumeroProcesso(idLicitacaoSiconv);

			return numeroProcesso;
		});

	}
	
	public Optional<String> recuperarSituacaoAceiteProcessoExecucao(Long idLicitacaoSiconv) {
		return jdbi.withHandle(transaction -> {
			Optional<String> situacaoAceiteProcessoExecucao = transaction.attach(VrplDAO.class)
					.recuperarSituacaoAceiteProcessoExecucao(idLicitacaoSiconv);

			return situacaoAceiteProcessoExecucao;
		});

	}

}
