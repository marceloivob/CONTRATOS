package br.gov.economia.maisbrasil.contratos.repository;

import java.time.LocalDate;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.AioDAO;

/**
 * Spring Data repository for the Aio entity.
 */
@Repository
@Transactional
public class AioRepository {

	@Autowired
	private Jdbi jdbi;

	public AioBD emitirAIO(AioBD aio) {
		return jdbi.withHandle(transaction ->
			transaction.attach(AioDAO.class).emitirAIO(aio.getPropostaFk(), aio.getContratoEmissorFk(), LocalDate.now())
		);
	}

	public AioBD desemitirAIO(AioBD aio) {
		return jdbi.withHandle(transaction -> {
			AioBD aioEmitido = transaction.attach(AioDAO.class).limparContratoEmissor(aio.getPropostaFk());

			return aioEmitido;
		});
	}

	public AioBD recuperarAio(Long propostaId) {
		return jdbi.withHandle(transaction -> {
			AioBD aioEmitido = transaction.attach(AioDAO.class).findByPropostaId(propostaId);

			return aioEmitido;
		});
	}
}
