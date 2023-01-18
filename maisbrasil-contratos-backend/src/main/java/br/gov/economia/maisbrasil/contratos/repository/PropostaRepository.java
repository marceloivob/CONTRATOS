package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.PropostaDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.PropostaSemAutenticacaoDAO;

@Repository
@Transactional
public class PropostaRepository {

	@Autowired
	private Jdbi jdbi;

	public PropostaBD inserirProposta(PropostaBD proposta) {
		return jdbi.withHandle(transaction -> {
			PropostaBD propostaInserida = transaction.attach(PropostaDAO.class).inserirProposta(proposta);

			return propostaInserida;
		});
	}

	public PropostaBD alterarProposta(PropostaBD proposta) {
		return jdbi.withHandle(transaction -> {
			PropostaBD propostaAlterada = transaction.attach(PropostaDAO.class).alterarProposta(proposta);

			return propostaAlterada;
		});
	}

	public void excluirProposta(Long id) {
		jdbi.useHandle(transaction -> {
			transaction.attach(PropostaDAO.class).excluirProposta(id);
		});
	}

	public Optional<PropostaBD> recuperarPropostaPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<PropostaBD> proposta = transaction.attach(PropostaSemAutenticacaoDAO.class).recuperarPropostaPorId(id);

			return proposta;
		});
	}

	public Optional<PropostaBD> recuperarPropostaPorIdSiconv(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<PropostaBD> proposta = transaction.attach(PropostaDAO.class).recuperarPropostaPorIdSiconv(id);

			return proposta;
		});

	}

}
