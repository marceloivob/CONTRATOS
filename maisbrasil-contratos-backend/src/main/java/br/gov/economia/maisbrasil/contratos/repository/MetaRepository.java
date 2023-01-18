package br.gov.economia.maisbrasil.contratos.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.economia.maisbrasil.contratos.domain.bd.MetaBD;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.MetaDAO;

@Repository
@Transactional
public class MetaRepository {

	@Autowired
	private Jdbi jdbi;

	public MetaBD inserirMeta(MetaBD metaBD) {
		return jdbi.withHandle(transaction -> transaction.attach(MetaDAO.class).inserirMeta(metaBD));
	}

	public MetaBD alterarMeta(MetaBD metaBD) {
		return jdbi.withHandle(transaction -> transaction.attach(MetaDAO.class).alterarMeta(metaBD));
	}

	public void excluirMeta(Long id) {
		jdbi.useHandle(transaction -> transaction.attach(MetaDAO.class).excluirMeta(id));
	}

	public Optional<MetaBD> recuperarMetaPorId(Long id) {
		return jdbi.withHandle(transaction -> transaction.attach(MetaDAO.class).recuperarMetaPorId(id));
	}

	public List<MetaBD> recuperarTodosMeta() {
		return jdbi.withHandle(transaction -> transaction.attach(MetaDAO.class).recuperarTodosMeta());
	}

	public void inserirMetaBatch(Collection<MetaBD> metas) {
		jdbi.useHandle(transaction -> transaction.attach(MetaDAO.class).inserirMetaBatch(metas));
	}

	public Optional<MetaBD> recuperarMetaPorIdVrpl(Long idMetaVrpl) {
		return jdbi.withHandle(transaction -> transaction.attach(MetaDAO.class).recuperarMetaPorIdVrpl(idMetaVrpl));
	}

	public List<MetaBD> recuperarMetaPorListaIds(List<Long> listaIdsMetas) {
		return jdbi.withHandle(transaction -> transaction.attach(MetaDAO.class).recuperarMetaPorListaIds(listaIdsMetas));
	}
}
