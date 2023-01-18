package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;

public interface PropostaSemAutenticacaoDAO {

	@SqlQuery("SELECT * FROM contratos.proposta WHERE id = :id")
	@RegisterFieldMapper(PropostaBD.class)
	Optional<PropostaBD> recuperarPropostaPorId(@Bind("id") Long id);

}
