package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.integracao.ConvenioIntegracao;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.SiconvaoDAO;
import br.gov.economia.maisbrasil.contratos.security.Perfil;

@Repository
@Transactional
public class SiconvaoRepository {

	@Autowired
	@Qualifier("jdbiForVRPL")
	private Jdbi jdbi;

	public Optional<ConvenioIntegracao> recuperarDataAssinaturaConvenio(Long idProp) {
		return jdbi.withHandle(transaction -> {
			Optional<ConvenioIntegracao> dataAssinaturaConvenio = transaction.attach(SiconvaoDAO.class).recuperarDataAssinaturaConvenio(idProp);

			return dataAssinaturaConvenio;
		});
	}
	
	
	public List<String> recuperarEmailsPerfilProponente(Long propostaFk, Collection<Perfil> perfis) {
		return jdbi.withHandle(transaction -> {
			List<String> emails = transaction.attach(SiconvaoDAO.class).recuperarEmailsPerfilProponente(propostaFk, perfis);
			return emails;
		});
	}

	public List<String> recuperarEmailsPerfilConcedente(Long propostaFk, Collection<Perfil> perfis) {
		return jdbi.withHandle(transaction -> {
			List<String> emails = transaction.attach(SiconvaoDAO.class).recuperarEmailsPerfilConcedente(propostaFk, perfis);
			return emails;
		});
	}

	public List<String> recuperarEmailsPerfilMandataria(Long propostaFk, Collection<Perfil> perfis) {
		return jdbi.withHandle(transaction -> {
			List<String> emails = transaction.attach(SiconvaoDAO.class).recuperarEmailsPerfilMandataria(propostaFk, perfis);
			return emails;
		});
	}

}
