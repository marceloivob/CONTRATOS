package br.gov.economia.maisbrasil.contratos.core.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.gov.economia.maisbrasil.contratos.security.Profile;
import br.gov.economia.maisbrasil.contratos.security.Role;
import br.gov.economia.maisbrasil.contratos.security.SiconvPrincipal;


public class SecurityAccessException extends BusinessException {

	private static final long serialVersionUID = 3923767723004978503L;

	private static final Logger logger = LoggerFactory.getLogger(SecurityAccessException.class);

	public SecurityAccessException(SiconvPrincipal usuarioLogado, Profile[] allowedProfiles) {
		super(ErrorInfo.ERRO_ACESSO_PERFIL_NAO_AUTORIZADO);

		logger.warn("{}, CPF: {}, IdProposta: {}, Perfis do Usuário: {}, Perfil necessário pelo recurso : {}",
				ErrorInfo.ERRO_ACESSO_PERFIL_NAO_AUTORIZADO, usuarioLogado.getId(), usuarioLogado.getIdProposta(),
				usuarioLogado.getProfiles(), allowedProfiles);
	}

	public SecurityAccessException(SiconvPrincipal usuarioLogado, Role[] allowedRoles) {
		super(ErrorInfo.ERRO_ACESSO_PERFIL_NAO_AUTORIZADO, usuarioLogado.getId(), usuarioLogado.getIdProposta(),
				allowedRoles);

		logger.warn("{}, CPF: {}, IdProposta: {}, Roles do Usuário: {}, Role exigido pelo recurso: {}",
				ErrorInfo.ERRO_ACESSO_PERFIL_NAO_AUTORIZADO, usuarioLogado.getId(), usuarioLogado.getIdProposta(),
				usuarioLogado.getRoles(), allowedRoles);
	}

//	public SecurityAccessException(SiconvPrincipal usuarioLogado, TabelasDoVRPLEnum tabela, Long idRegistro) {
//		super(ErrorInfo.ERRO_ACESSO_RECURSO_NAO_PERMITIDO, usuarioLogado.getId(), usuarioLogado.getIdProposta(), tabela,
//				idRegistro);
//
//		logger.warn("{}, CPF: {}, IdProposta: {}, Tabela: {}, Id do Registro: {}",
//				ErrorInfo.ERRO_ACESSO_RECURSO_NAO_PERMITIDO, usuarioLogado.getId(), usuarioLogado.getIdProposta(),
//				tabela, idRegistro);
//	}
}
