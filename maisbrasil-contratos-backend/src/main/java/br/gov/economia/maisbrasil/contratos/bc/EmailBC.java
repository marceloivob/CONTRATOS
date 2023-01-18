package br.gov.economia.maisbrasil.contratos.bc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.domain.EventoGeradorHistoricoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import br.gov.economia.maisbrasil.contratos.integracao.mail.EmailTemplate;
import br.gov.economia.maisbrasil.contratos.integracao.mail.ErroEnvioEmailException;
import br.gov.economia.maisbrasil.contratos.integracao.mail.SenderEmail;
import br.gov.economia.maisbrasil.contratos.integracao.mail.SenderEmailProducer;
import br.gov.economia.maisbrasil.contratos.repository.SiconvaoRepository;
import br.gov.economia.maisbrasil.contratos.security.Perfil;
import br.gov.economia.maisbrasil.contratos.security.Profile;

@Controller
public class EmailBC {
	
	private SenderEmail email;

	private EmailTemplate emailTemplate = new EmailTemplate();
	
	private final SiconvaoRepository siconvaoRepository;
	
	@Autowired
	public EmailBC(SiconvaoRepository siconvaoRepository) {
		this.siconvaoRepository = siconvaoRepository;
	}
	
	
	public void enviarEmailEmissaoAIO(PropostaBD proposta, ContratoBD contrato, EventoGeradorHistoricoEnum evento) {
		// Enviar Para:
		// Gestor de Convênio Convenente,
		// Fiscal do Convenente, e
		// Fiscal do Concedente.

		Set<Address> emails = buscarEmails(proposta, Profile.CONCEDENTE);
		emails.addAll(buscarEmails(proposta, Profile.PROPONENTE));

		if (emails.isEmpty()) {
			throw new IllegalArgumentException("Não foram encontrados os emails para a Proposta:" + proposta
					+ " e Perfis: " + Profile.CONCEDENTE + ", " + Profile.PROPONENTE);
		}
		
		enviarEmail(emails, emailTemplate.getAssuntoEmissaoAIO(proposta), 
				emailTemplate.getConteudoEmissaoAIO(proposta, contrato, evento));
	}
	
	// enviar email por verificacao

	public void enviarEmailPendenciaLiberacaoValores(PropostaBD proposta, ContratoBD contrato) {
		// Enviar Para:
		// Gestor de Convênio Concedente, e
		// Gestor de Convênio da Instituição Mandatária

		Set<Address> emails = buscarEmails(proposta, Profile.CONCEDENTE);
		emails.addAll(buscarEmails(proposta, Profile.MANDATARIA));

		if (emails.isEmpty()) {
			throw new IllegalArgumentException("Não foram encontrados os emails para a Proposta:" + proposta
					+ " e Perfis: " + Profile.CONCEDENTE + ", " + Profile.MANDATARIA);
		}

		enviarEmail(emails, emailTemplate.getAssuntoPendenciaLiberacaoValores(proposta),
				emailTemplate.getConteudoPendenciaLiberacaoValores(proposta, contrato));
	}
	
	

	private Set<Address> buscarEmails(PropostaBD proposta, Profile profile) {
		List<String> emails = new ArrayList<>();

		if (profile.equals(Profile.PROPONENTE)) {
			emails = siconvaoRepository.recuperarEmailsPerfilProponente(proposta.getIdSiconv(), Perfil.proponentes);
		} else if (profile.equals(Profile.CONCEDENTE)) {
			emails = siconvaoRepository.recuperarEmailsPerfilConcedente(proposta.getIdSiconv(), Perfil.concedentes);
		} else if (profile.equals(Profile.MANDATARIA)) {
			emails = siconvaoRepository.recuperarEmailsPerfilMandataria(proposta.getIdSiconv(), Perfil.mandatarias);
		} else {
			throw new IllegalArgumentException("O perfil " + profile + " não pode enviar emails.");
		}

		Set<Address> emailsSet = new HashSet<>();
		for (String email : emails) {
			try {
				emailsSet.add(new InternetAddress(email));
			} catch (AddressException e) {
				throw new ErroEnvioEmailException(e);
			}
		}

		return emailsSet;
	}

	private void enviarEmail(Set<Address> emails, String assunto, String corpo) {
		if (email == null) {
			this.email = new SenderEmailProducer().create();
		}

		email.send(emails, assunto, corpo);
	}

}
