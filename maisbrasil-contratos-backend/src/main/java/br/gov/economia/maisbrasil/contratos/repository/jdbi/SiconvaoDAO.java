package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

import br.gov.economia.maisbrasil.contratos.domain.integracao.ConvenioIntegracao;
import br.gov.economia.maisbrasil.contratos.security.Perfil;


public interface SiconvaoDAO {

	@SqlQuery("SELECT c.data_assinatura, c.sequencial, c.ano FROM siconv.convenio c, siconv.prop p WHERE p.id = :idProp and p.convenio_fk = c.id")
	@RegisterFieldMapper(ConvenioIntegracao.class)
	Optional<ConvenioIntegracao> recuperarDataAssinaturaConvenio(@Bind("idProp") Long idProp);
	
	@SqlQuery
	@UseStringTemplateSqlLocator
	List<String> recuperarEmailsPerfilProponente(@Bind("propostaFk") Long propostaFk,
			@BindList("perfis") Collection<Perfil> perfis);

	@SqlQuery
	@UseStringTemplateSqlLocator
	List<String> recuperarEmailsPerfilConcedente(@Bind("propostaFk") Long propostaFk,
			@BindList("perfis") Collection<Perfil> perfis);

	@SqlQuery
	@UseStringTemplateSqlLocator
	List<String> recuperarEmailsPerfilMandataria(@Bind("propostaFk") Long propostaFk,
			@BindList("perfis") Collection<Perfil> perfis);

}
