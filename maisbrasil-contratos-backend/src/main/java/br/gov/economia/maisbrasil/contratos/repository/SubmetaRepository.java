package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.SubmetaDAO;

@Repository
@Transactional
public class SubmetaRepository {

	@Autowired
	private Jdbi jdbi;

	public SubmetaBD inserirSubmeta(SubmetaBD submetaBD) {
		return jdbi.withHandle(transaction -> {
			SubmetaBD submetaInserida = transaction.attach(SubmetaDAO.class).inserirSubmeta(submetaBD);

			return submetaInserida;
		});
	}

	public SubmetaBD alterarSubmeta(SubmetaBD submetaBD) {
		return jdbi.withHandle(transaction -> {
			SubmetaBD submetaAlterada = transaction.attach(SubmetaDAO.class).alterarSubmeta(submetaBD);

			return submetaAlterada;
		});
	}

	public void excluirSubmeta(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(SubmetaDAO.class).excluirSubmeta(id));
	}

	public Optional<SubmetaBD> recuperarSubmetaPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<SubmetaBD> submetaPorId = transaction.attach(SubmetaDAO.class).recuperarSubmetaPorId(id);

			return submetaPorId;
		});
	}

	public List<SubmetaBD> recuperarTodosSubmeta() {
		return jdbi.withHandle(transaction -> {
			List<SubmetaBD> submetas = transaction.attach(SubmetaDAO.class).recuperarTodosSubmeta();

			return submetas;
		});
	}

	public void inserirSubmetaBatch(Collection<SubmetaBD> submetas) {
		jdbi.useHandle(transaction -> transaction.attach(SubmetaDAO.class).inserirSubmetaBatch(submetas));
	}

	public List<SubmetaBD> recuperarSubmetasDoLote(Long idLote) {
		return jdbi.withHandle(transaction -> {
			List<SubmetaBD> submetasDoLote = transaction.attach(SubmetaDAO.class).recuperarSubmetasDoLote(idLote);

			return submetasDoLote;
		});
	}
}
