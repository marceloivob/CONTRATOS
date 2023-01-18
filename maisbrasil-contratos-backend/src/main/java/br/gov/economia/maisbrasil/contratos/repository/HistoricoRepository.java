package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.HistoricoDAO;

@Repository
@Transactional
public class HistoricoRepository {

	@Autowired
	private Jdbi jdbi;

	public HistoricoBD inserirHistorico(HistoricoBD historicoBD) {
		return jdbi.withHandle(transaction -> {
			HistoricoBD historicoInserido = transaction.attach(HistoricoDAO.class).inserirHistorico(historicoBD);

			return historicoInserido;
		});
	}

	public HistoricoBD alterarHistorico(HistoricoBD historicoBD) {
		return jdbi.withHandle(transaction -> {
			HistoricoBD historicoAlterado = transaction.attach(HistoricoDAO.class).alterarHistorico(historicoBD);

			return historicoAlterado;
		});
	}

	public void excluirHistorico(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(HistoricoDAO.class).excluirHistorico(id));
	}

	public void excluirHistoricoDoContrato(Long idContrato) {
		jdbi.useHandle(transaction -> transaction.attach(HistoricoDAO.class).excluirHistoricoDoContrato(idContrato));
	}

	public Optional<HistoricoBD> recuperarHistoricoPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<HistoricoBD> historicoPorId = transaction.attach(HistoricoDAO.class).recuperarHistoricoPorId(id);

			return historicoPorId;
		});
	}

	public List<HistoricoBD> recuperarTodosHistorico() {
		return jdbi.withHandle(transaction -> {
			List<HistoricoBD> todosHistoricos = transaction.attach(HistoricoDAO.class).recuperarTodosHistorico();

			return todosHistoricos;
		});
	}

	public void inserirHistoricoBatch(Collection<HistoricoBD> historicos) {
		jdbi.useHandle(transaction -> transaction.attach(HistoricoDAO.class).inserirHistoricoBatch(historicos));
	}
}
