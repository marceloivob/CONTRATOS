package br.gov.economia.maisbrasil.contratos.core.database;

import static br.gov.economia.maisbrasil.contratos.core.database.DataSourceType.VRPL;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;;

@DataSourceFor(VRPL)
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VRPLDAOFactory implements DAOFactory {

    @Autowired
	@DataSourceFor(VRPL)
    private Jdbi jdbi;

    /* (non-Javadoc)
	 * @see br.gov.planejamento.siconv.mandatarias.licitacoes.application.database.DaoFactoryXXXX#get(java.lang.Class)
	 */
    @Override
	public <T> T get(Class<T> clazz) {
        return jdbi.onDemand(clazz);
    }

    @Override
	public Jdbi getJdbi() {
        return jdbi;
    }

    @Override
	public void setJdbi(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

}