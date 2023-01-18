package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.ContratoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.TermoAditivoDAO;

@Repository
@Transactional
public class TermoAditivoRepository {

	@Autowired
	private Jdbi jdbi;

	public TermoAditivoBD inserirTermoAditivo(TermoAditivoBD termoAditivoBD) {
		return jdbi.withHandle(transaction -> {
			TermoAditivoBD termoAditivoInserido = transaction.attach(TermoAditivoDAO.class)
					.inserirTermoAditivo(termoAditivoBD);

			return termoAditivoInserido;
		});
	}

	public TermoAditivoBD alterarTermoAditivo(TermoAditivoBD termoAditivoBD) {
		return jdbi.withHandle(transaction -> {
			TermoAditivoBD termoAditivoAlterado = transaction.attach(TermoAditivoDAO.class)
					.alterarTermoAditivo(termoAditivoBD);

			return termoAditivoAlterado;
		});
	}

	public void excluirTermoAditivo(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(TermoAditivoDAO.class).excluirTermoAditivo(id));

	}

	public Optional<TermoAditivoBD> recuperarTermoAditivoPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<TermoAditivoBD> termoAditivoPorId = transaction.attach(TermoAditivoDAO.class)
					.recuperarTermoAditivoPorId(id);

			return termoAditivoPorId;
		});
	}
	
	public List<TermoAditivoBD> recuperarTermoAditivoPorContratoId(Long contratoId) {
		return jdbi.withHandle(transaction -> {
			List<TermoAditivoBD> termoAditivoPorContratoId = transaction.attach(TermoAditivoDAO.class)
					.recuperarTermoAditivoPorContratoId(contratoId);

			return termoAditivoPorContratoId;
		});
	}

	public List<TermoAditivoBD> recuperarTodosTermoAditivo() {
		return jdbi.withHandle(transaction -> {
			List<TermoAditivoBD> todosTermosAditivos = transaction.attach(TermoAditivoDAO.class)
					.recuperarTodosTermoAditivo();

			return todosTermosAditivos;
		});
	}

	public void inserirTermoAditivoBatch(Collection<TermoAditivoBD> termoAditivos) {
		jdbi.useHandle(
				transaction -> transaction.attach(TermoAditivoDAO.class).inserirTermoAditivoBatch(termoAditivos));
	}
	
	public boolean seExisteTermoAditivoComONumero(TermoAditivoDTO termoAditivo) {
		return jdbi.withHandle(transaction -> {
			boolean existe = transaction.attach(TermoAditivoDAO.class).seExisteTermoAditivoComONumero(termoAditivo);  

			return existe;
		});
	}
	
	public boolean seExisteTermoAditivoComONumeroDiferenteDoId(TermoAditivoBD termoAditivo) {
		return jdbi.withHandle(transaction -> {
			boolean existe = transaction.attach(TermoAditivoDAO.class).seExisteTermoAditivoComONumeroDiferenteDoId(termoAditivo);  

			return existe;
		});
	}
}
