package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaDocComplementarBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.SubmetaDocComplementarDAO;

@Repository
@Transactional
public class SubmetaDocComplementarRepository {

	@Autowired
	private Jdbi jdbi;

	public SubmetaDocComplementarBD inserirSubmetaDocComplementar(SubmetaDocComplementarBD submetaDocComplementarBD) {
		return jdbi.withHandle(transaction -> {
			SubmetaDocComplementarBD submetaDocComplementarInserida = transaction
					.attach(SubmetaDocComplementarDAO.class).inserirSubmetaDocComplementar(submetaDocComplementarBD);

			return submetaDocComplementarInserida;
		});
	}

	public SubmetaDocComplementarBD alterarSubmetaDocComplementar(SubmetaDocComplementarBD submetaDocComplementarBD) {
		return jdbi.withHandle(transaction -> {
			SubmetaDocComplementarBD submetaDocComplementarAlterada = transaction
					.attach(SubmetaDocComplementarDAO.class).alterarSubmetaDocComplementar(submetaDocComplementarBD);

			return submetaDocComplementarAlterada;
		});
	}

	public void excluirSubmetaDocComplementar(Long id) {
		jdbi.useHandle(
				transaction -> transaction.attach(SubmetaDocComplementarDAO.class).excluirSubmetaDocComplementar(id));
	}

	public Optional<SubmetaDocComplementarBD> recuperarSubmetaDocComplementarPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<SubmetaDocComplementarBD> submetaDocComplementarPorId = transaction
					.attach(SubmetaDocComplementarDAO.class).recuperarSubmetaDocComplementarPorId(id);

			return submetaDocComplementarPorId;
		});
	}

	public List<SubmetaDocComplementarBD> recuperarTodosSubmetaDocComplementar() {
		return jdbi.withHandle(transaction -> {
			List<SubmetaDocComplementarBD> todosSubmetaDocComplementar = transaction
					.attach(SubmetaDocComplementarDAO.class).recuperarTodosSubmetaDocComplementar();

			return todosSubmetaDocComplementar;
		});
	}

	public void inserirSubmetaDocComplementarBatch(Collection<SubmetaDocComplementarBD> submetaDocComplementars) {
		jdbi.useHandle(transaction -> transaction.attach(SubmetaDocComplementarDAO.class)
				.inserirSubmetaDocComplementarBatch(submetaDocComplementars));
	}
}
