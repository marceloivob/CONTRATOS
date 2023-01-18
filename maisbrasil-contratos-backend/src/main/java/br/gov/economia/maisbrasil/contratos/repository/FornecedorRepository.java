package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.FornecedorDAO;

@Repository
@Transactional
public class FornecedorRepository {

	@Autowired
	private Jdbi jdbi;

	public FornecedorBD inserirFornecedor(FornecedorBD fornecedorBD) {
		return jdbi.withHandle(transaction -> {
			FornecedorBD fornecedorInserido = transaction.attach(FornecedorDAO.class).inserirFornecedor(fornecedorBD);

			return fornecedorInserido;
		});
	}

	public FornecedorBD alterarFornecedor(FornecedorBD fornecedorBD) {
		return jdbi.withHandle(transaction -> {
			FornecedorBD fornecedorAlterado = transaction.attach(FornecedorDAO.class).alterarFornecedor(fornecedorBD);

			return fornecedorAlterado;
		});
	}

	public void excluirFornecedor(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(FornecedorDAO.class).excluirFornecedor(id));
	}

	public Optional<FornecedorBD> recuperarFornecedorPorId(Long id) {
		return jdbi.withHandle(transaction -> {
			Optional<FornecedorBD> fornecedorPorId = transaction.attach(FornecedorDAO.class)
					.recuperarFornecedorPorId(id);

			return fornecedorPorId;
		});
	}

	public List<FornecedorBD> recuperarTodosFornecedor() {
		return jdbi.withHandle(transaction -> {
			List<FornecedorBD> todosFornecedores = transaction.attach(FornecedorDAO.class).recuperarTodosFornecedor();

			return todosFornecedores;
		});
	}

	public List<FornecedorBD> recuperarFornecedoresPorProposta(Long idProposta) {
		return jdbi.withHandle(transaction -> {
			List<FornecedorBD> fornecedoresPorProposta = transaction.attach(FornecedorDAO.class)
					.recuperarFornecedoresPorProposta(idProposta);

			return fornecedoresPorProposta;
		});
	}

	public void inserirFornecedorBatch(Collection<FornecedorBD> fornecedors) {
		jdbi.useHandle(transaction -> transaction.attach(FornecedorDAO.class).inserirFornecedorBatch(fornecedors));
	}

	public List<FornecedorBD> recuperarFornecedorPorIdentificacao(String identificacao) {
		return jdbi.withHandle(transaction -> {
			List<FornecedorBD> fornecedoresPorIdentificacao = transaction.attach(FornecedorDAO.class)
					.recuperarFornecedorPorIdentificacao(identificacao);

			return fornecedoresPorIdentificacao;
		});

	}
}
