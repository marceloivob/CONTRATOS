package br.gov.economia.maisbrasil.contratos.repository;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.ContratoDAO;

@Repository
@Transactional
public class ContratoRepository {

	@Autowired
	private Jdbi jdbi;

	public ContratoBD inserirContrato(ContratoBD contrato) {
		return jdbi.withHandle(transaction -> {
			ContratoBD contratoInserido = transaction.attach(ContratoDAO.class).inserirContrato(contrato);

			return contratoInserido;
		});
	}

	public ContratoBD alterarContrato(ContratoBD contrato) {
		return jdbi.withHandle(transaction -> {
			ContratoBD contratoAlterado = transaction.attach(ContratoDAO.class).alterarContrato(contrato);

			return contratoAlterado;
		});
	}

	public void excluirContrato(Long id) {
		jdbi.useHandle(transaction -> {
			transaction.attach(ContratoDAO.class).excluirContrato(id);
		});
	}

	public Optional<ContratoBD> recuperarContratoPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<ContratoBD> contratoPorId = transaction.attach(ContratoDAO.class).recuperarContratoPorId(id);

			return contratoPorId;
		});
	}

	public List<ContratoBD> recuperarTodosContrato() {
		return jdbi.withHandle(transaction -> {
			List<ContratoBD> contratoPorId = transaction.attach(ContratoDAO.class).recuperarTodosContrato();

			return contratoPorId;
		});
	}

	public List<ContratoBD> loadContratosPorIdSiconvProposta(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<ContratoBD> contratoPorProposta = transaction.attach(ContratoDAO.class)
					.loadContratosPorIdSiconvProposta(idProposta);

			return contratoPorProposta;
		});
	}

	public List<ContratoBD> loadContratosPorIdProposta(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<ContratoBD> contratoPorProposta = transaction.attach(ContratoDAO.class)
					.loadContratosPorIdProposta(idProposta);

			return contratoPorProposta;
		});
	}

	
	public List<ContratoBD> recuperarContratosDaLicitacao(Long idLicitacao) {
		return jdbi.withHandle(transaction -> {
			List<ContratoBD> contratoPorProposta = transaction.attach(ContratoDAO.class)
					.recuperarContratosDaLicitacao(idLicitacao);

			return contratoPorProposta;
		});
	}

	public boolean seExisteContratoConcluidoComONumero(ContratoDTO contrato) {
		return jdbi.withHandle(transaction -> {
			boolean existe = transaction.attach(ContratoDAO.class).seExisteContratoConcluidoComONumero(contrato);  

			return existe;
		});
	}

	public boolean seExisteContratoConcluidoComONumeroDiferenteDoId(ContratoBD contrato) {
		return jdbi.withHandle(transaction -> {
			boolean existe = transaction.attach(ContratoDAO.class)
					.seExisteContratoConcluidoComONumeroDiferenteDoId(contrato);

			return existe;
		});
	}

}
