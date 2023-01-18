package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.DocComplementarBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.DocComplementarDAO;

@Repository
@Transactional
public class DocComplementarRepository {

	@Autowired
	private Jdbi jdbi;

	public DocComplementarBD inserirDocComplementar(DocComplementarBD docComplementarBD) {
		return jdbi.withHandle(transaction -> {
			DocComplementarBD documentoComplementarInserido = transaction.attach(DocComplementarDAO.class)
					.inserirDocComplementar(docComplementarBD);

			return documentoComplementarInserido;
		});
	}

	public DocComplementarBD alterarDocComplementar(DocComplementarBD docComplementarBD) {
		return jdbi.withHandle(transaction -> {
			DocComplementarBD documentoComplementarAlterado = transaction.attach(DocComplementarDAO.class)
					.alterarDocComplementar(docComplementarBD);

			return documentoComplementarAlterado;
		});
	}

	public void excluirDocComplementar(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(DocComplementarDAO.class).excluirDocComplementar(id));
	}

	public Optional<DocComplementarBD> recuperarDocComplementarPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<DocComplementarBD> documentoComplementarPorId = transaction.attach(DocComplementarDAO.class)
					.recuperarDocComplementarPorId(id);

			return documentoComplementarPorId;
		});
	}

	public List<DocComplementarBD> recuperarTodosDocComplementar() {
		return jdbi.withHandle(transaction -> {
			List<DocComplementarBD> todosDocumentosComplementares = transaction.attach(DocComplementarDAO.class)
					.recuperarTodosDocComplementar();

			return todosDocumentosComplementares;
		});
	}

	public void inserirDocComplementarBatch(Collection<DocComplementarBD> docComplementars) {
		jdbi.useHandle(transaction -> transaction.attach(DocComplementarDAO.class)
				.inserirDocComplementarBatch(docComplementars));
	}
}
