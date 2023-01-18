package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.LoteDAO;

@Repository
@Transactional
public class LoteRepository {

	@Autowired
	private Jdbi jdbi;

	public LoteBD inserirLote(LoteBD loteBD) {
		return jdbi.withHandle(transaction -> {
			LoteBD loteInserido = transaction.attach(LoteDAO.class).inserirLote(loteBD);

			return loteInserido;
		});
	}

	public LoteBD alterarLote(LoteBD loteBD) {
		return jdbi.withHandle(transaction -> {
			LoteBD loteAlterado = transaction.attach(LoteDAO.class).alterarLote(loteBD);

			return loteAlterado;
		});
	}

	public void excluirLote(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(LoteDAO.class).excluirLote(id));
	}

	public Optional<LoteBD> recuperarLotePorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<LoteBD> loteAlterado = transaction.attach(LoteDAO.class).recuperarLotePorId(id);

			return loteAlterado;
		});
	}

	public List<LoteBD> recuperarTodosLotesDaProposta(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<LoteBD> todosLotesPorProposta = transaction.attach(LoteDAO.class)
					.recuperarTodosLotesDaProposta(idProposta);

			return todosLotesPorProposta;
		});
	}

	public void inserirLoteBatch(Collection<LoteBD> lotes) {
		jdbi.useHandle(transaction -> transaction.attach(LoteDAO.class).inserirLoteBatch(lotes));
	}

	public List<LoteBD> recuperarLotesDaLicitacao(Long idLicitacao) {
		return jdbi.withHandle(transaction -> {
			List<LoteBD> lotesDaLicitacao = transaction.attach(LoteDAO.class).recuperarLotesDaLicitacao(idLicitacao);

			return lotesDaLicitacao;
		});
	}

	public List<LoteBD> recuperarLotesPorIdFornecedorEIdLicitacao(Long idFornecedor, Long idLicitacao) {
		return jdbi.withHandle(transaction -> {
			List<LoteBD> lotesPorIdFornecedorEIdLicitacao = transaction.attach(LoteDAO.class)
					.recuperarLotesPorIdFornecedorEIdLicitacao(idFornecedor, idLicitacao);

			return lotesPorIdFornecedorEIdLicitacao;
		});
	}

	public void removerLotesDoContrato(Long idContrato) {
		jdbi.useHandle(transaction -> transaction.attach(LoteDAO.class).removerLotesDoContrato(idContrato));
	}

	public void associarLotesAoContrato(List<Long> ids, Long idContrato) {
		jdbi.useHandle(transaction -> transaction.attach(LoteDAO.class).associarLotesAoContrato(ids, idContrato));
	}

	public List<LoteBD> recuperarLotesDoContrato(Long idContrato) {
		return jdbi.withHandle(transaction -> {
			List<LoteBD> lotesDoContrato = transaction.attach(LoteDAO.class).recuperarLotesDoContrato(idContrato);

			return lotesDoContrato;
		});

	}

}
