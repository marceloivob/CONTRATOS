package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.LicitacaoBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.LicitacaoDAO;

@Repository
@Transactional
public class LicitacaoRepository {

	@Autowired
	private Jdbi jdbi;

	public LicitacaoBD inserirLicitacao(LicitacaoBD licitacaoBD) {
		return jdbi.withHandle(transaction -> {
			LicitacaoBD licitacaoInserida = transaction.attach(LicitacaoDAO.class).inserirLicitacao(licitacaoBD);

			return licitacaoInserida;
		});
	}

	public LicitacaoBD alterarLicitacao(LicitacaoBD licitacaoBD) {
		return jdbi.withHandle(transaction -> {
			LicitacaoBD licitacaoAlterada = transaction.attach(LicitacaoDAO.class).alterarLicitacao(licitacaoBD);

			return licitacaoAlterada;
		});
	}

	public void excluirLicitacao(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(LicitacaoDAO.class).excluirLicitacao(id));
	}

	public Optional<LicitacaoBD> recuperarLicitacaoPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<LicitacaoBD> licitacaoPorId = transaction.attach(LicitacaoDAO.class).recuperarLicitacaoPorId(id);

			return licitacaoPorId;
		});
	}

	public List<LicitacaoBD> recuperarTodosLicitacao() {
		return jdbi.withHandle(transaction -> {
			List<LicitacaoBD> todasLicitacoes = transaction.attach(LicitacaoDAO.class).recuperarTodosLicitacao();

			return todasLicitacoes;
		});
	}

	public void inserirLicitacaoBatch(Collection<LicitacaoBD> licitacaos) {
		jdbi.useHandle(transaction -> transaction.attach(LicitacaoDAO.class).inserirLicitacaoBatch(licitacaos));
	}

	public Optional<LicitacaoBD> recuperarLicitacaoPorFK(Long licitacaoFK) {
		return jdbi.withHandle(transaction -> {
			Optional<LicitacaoBD> licitacaoPorFk = transaction.attach(LicitacaoDAO.class)
					.recuperarLicitacaoPorFK(licitacaoFK);

			return licitacaoPorFk;
		});
	}

	public List<LicitacaoBD> recuperarLicitacoesPorProposta(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<LicitacaoBD> licitacaoPorProposta = transaction.attach(LicitacaoDAO.class)
					.recuperarLicitacoesPorProposta(idProposta);

			return licitacaoPorProposta;
		});
	}

	public List<LicitacaoBD> recuperarLicitacoesDaPropostaPorIdFornecedor(Long idFornecedor, Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<LicitacaoBD> licitacaoDaPropostaPorIdFornecedor = transaction.attach(LicitacaoDAO.class)
					.recuperarLicitacoesDaPropostaPorIdFornecedor(idFornecedor, idProposta);

			return licitacaoDaPropostaPorIdFornecedor;
		});
	}

	public List<LicitacaoBD> recuperarLicitacoesPorPropostaIdSiconv(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<LicitacaoBD> licitacaoDaPropostaPorPropostaIdSiconv = transaction.attach(LicitacaoDAO.class)
					.recuperarLicitacoesPorPropostaIdSiconv(idProposta);

			return licitacaoDaPropostaPorPropostaIdSiconv;
		});

	}

}
