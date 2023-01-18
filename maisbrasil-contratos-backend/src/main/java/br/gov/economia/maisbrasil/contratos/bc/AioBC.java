package br.gov.economia.maisbrasil.contratos.bc;

import java.time.LocalDate;

import org.jdbi.v3.core.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;
import br.gov.economia.maisbrasil.contratos.repository.AioRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.AioDAO;

@Controller
public class AioBC {

	private final AioRepository aioRepository;

	@Autowired
	public AioBC(AioRepository aioRepository) {
		this.aioRepository = aioRepository;
	}

	public AioBD emitirAIO(Long idContratoEmissor, Long idProposta, LocalDate dataEmissaoAIO, Handle transaction) {
		return transaction.attach(AioDAO.class).emitirAIO(idProposta, idContratoEmissor, dataEmissaoAIO);
	}

	public AioBD limparContratoEmissor(Long idProposta, Handle transaction) {
		AioBD bd = transaction.attach(AioDAO.class).limparContratoEmissor(idProposta);

		return bd;
	}

	public AioBD recuperarAio(Long propostaId) {
		AioBD bd = aioRepository.recuperarAio(propostaId);

		return bd;
	}

	public void apagarRegistroAIO(Long idProposta, Handle transaction) {
		transaction.attach(AioDAO.class).apagarRegistroAIO(idProposta);

	}


}
