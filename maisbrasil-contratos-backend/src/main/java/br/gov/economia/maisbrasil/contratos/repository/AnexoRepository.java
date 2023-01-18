package br.gov.economia.maisbrasil.contratos.repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.AnexoDAO;

/**
 * Spring Data repository for the Anexo entity.
 */
@Repository
@Transactional
public class AnexoRepository {

	@Autowired
	private Jdbi jdbi;

	public AnexoBD inserirAnexo(AnexoBD anexo) {
		return jdbi.withHandle(transaction -> {
			AnexoBD anexoInserido = transaction.attach(AnexoDAO.class).inserirAnexo(anexo);

			return anexoInserido;
		});
	}
	
	public AnexoBD inserirAnexo(AnexoBD anexo, Handle transaction) {
		AnexoBD anexoInserido = transaction.attach(AnexoDAO.class).inserirAnexo(anexo);
		return anexoInserido;
	}

	public AnexoBD alterarAnexo(AnexoBD anexo) {
		return jdbi.withHandle(transaction -> {
			AnexoBD anexoAlterado = transaction.attach(AnexoDAO.class).alterarAnexo(anexo);

			return anexoAlterado;
		});
	}

	public void excluirAnexo(Long id) {
		jdbi.useHandle(transaction -> {
			transaction.attach(AnexoDAO.class).excluirAnexo(id);
		});
	}

	public Optional<AnexoBD> recuperarAnexoPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<AnexoBD> anexoRecuperado = transaction.attach(AnexoDAO.class).recuperarAnexoPorId(id);

			return anexoRecuperado;
		});
	}

	public List<AnexoBD> recuperarTodosAnexos() {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> todosAnexos = transaction.attach(AnexoDAO.class).recuperarTodosAnexos();

			return todosAnexos;
		});
	}

	public List<AnexoBD> recuperarAnexoPorIdContrato(Long id) {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> anexoPorIdContrato = transaction.attach(AnexoDAO.class).recuperarAnexoPorIdContrato(id);

			return anexoPorIdContrato;
		});
	}
	
	public List<AnexoBD> recuperarAnexoPorIdTermoAditivo(Long id) {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> anexo = transaction.attach(AnexoDAO.class).recuperarAnexoPorIdTermoAditivo(id);

			return anexo;
		});
	}


	public List<AnexoBD> getAnexosContratoPorTipo(Long idContrato, String tipoAnexo) {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> anexosDoContratoPorTipo = transaction.attach(AnexoDAO.class)
					.getAnexosContratoPorTipo(idContrato, tipoAnexo);

			return anexosDoContratoPorTipo;
		});
	}

	public List<AnexoBD> recuperarOutrosAnexosDaLicitacao(Long contratoId, Long id) {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> outrosAnexosDaLicitacao = transaction.attach(AnexoDAO.class)
					.recuperarOutrosAnexosDaLicitacao(contratoId, id);

			return outrosAnexosDaLicitacao;
		});
	}

	public void excluirAnexosPorIdContrato(Long idContrato) {
		jdbi.useHandle(transaction -> {
			transaction.attach(AnexoDAO.class).excluirAnexosPorIdContrato(idContrato);
		});
	}

	public List<AnexoBD> getAnexosContratoPorConjuntoTipos(Long idContrato, List<String> tiposAnexos) {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> anexosContratoPorConjuntoTipos = transaction.attach(AnexoDAO.class)
					.getAnexosContratoPorConjuntoTipos(idContrato, tiposAnexos);

			return anexosContratoPorConjuntoTipos;
		});
	}
	
	public List<AnexoBD> getAnexosTermoAditivoPorConjuntoTipos(Long idTermoAditivo, List<String> tiposAnexos) {
		return jdbi.withHandle(transaction -> {
			List<AnexoBD> anexosTermoAditivoPorConjuntoTipos = transaction.attach(AnexoDAO.class)
					.getAnexosTermoAditivoPorConjuntoTipos(idTermoAditivo, tiposAnexos);

			return anexosTermoAditivoPorConjuntoTipos;
		});
	}


}
