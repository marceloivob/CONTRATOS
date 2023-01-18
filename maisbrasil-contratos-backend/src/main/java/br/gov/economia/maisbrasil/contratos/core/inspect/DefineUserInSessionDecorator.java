package br.gov.economia.maisbrasil.contratos.core.inspect;

import java.lang.reflect.Method;
import java.util.Objects;

import org.jdbi.v3.sqlobject.Handler;
import org.jdbi.v3.sqlobject.HandlerDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import br.gov.economia.maisbrasil.contratos.core.cache.dao.CacheDAO;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

public class DefineUserInSessionDecorator implements HandlerDecorator {

	private static final Logger logger = LoggerFactory.getLogger(DefineUserInSessionDecorator.class);

	@Override
	public Handler decorateHandler(Handler base, Class<?> sqlObjectType, Method method) {

		return (target, args, handleSupplier) -> handleSupplier.getHandle().inTransaction(transaction -> {
			Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
			Objects.requireNonNull(usuarioAutenticado);
			SiconvUser usuarioLogado = (SiconvUser) usuarioAutenticado.getPrincipal();
			Objects.requireNonNull(usuarioLogado.getCpf());

			logger.debug("Executando operação '{}.{}' com o usuario: '{}'", sqlObjectType.getSimpleName(),
					method.getName(), usuarioLogado.getCpf());

			transaction.attach(CacheDAO.class).definirUsuarioLogado(usuarioLogado.getCpf());

			return base.invoke(target, args, handleSupplier);
		});

	}

}
