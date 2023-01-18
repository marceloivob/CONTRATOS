package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.PoBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.PoDAO;

@Repository
@Transactional
public class PoRepository {

	@Autowired
	private Jdbi jdbi;

	public PoBD inserirPo(PoBD po) {
		return jdbi.withHandle(transaction -> transaction.attach(PoDAO.class).inserirPo(po));
	}

	public PoBD alterarPo(PoBD po) {
		return jdbi.withHandle(transaction -> transaction.attach(PoDAO.class).alterarPo(po));
	}

	public void excluirPo(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(PoDAO.class).excluirPo(id));
	}

	public Optional<PoBD> recuperarPoPorId(Long id) {
		return jdbi.withHandle(transaction -> transaction.attach(PoDAO.class).recuperarPoPorId(id));
	}

	public List<PoBD> recuperarTodosPo() {
		return jdbi.withHandle(transaction -> transaction.attach(PoDAO.class).recuperarTodosPo());
	}

	public void inserirPoBatch(Collection<PoBD> pos) {
		jdbi.useHandle(transaction -> transaction.attach(PoDAO.class).inserirPoBatch(pos));
	}

	public Optional<PoBD> recuperarPoPorSubmeta(Long idSubmeta) {
		return jdbi.withHandle(transaction -> transaction.attach(PoDAO.class).recuperarPoPorSubmeta(idSubmeta));
	}

	public List<PoBD> recuperarPoPorListaSubmetas(List<Long> listaIdsSubmetas) {
		return jdbi.withHandle(transaction -> transaction.attach(PoDAO.class).recuperarPoPorListaSubmetas(listaIdsSubmetas));
	}

}
