package br.gov.economia.maisbrasil.contratos.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelperService {

	public SiconvUser getUsuarioLogadoContexto() {
    	Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();

		return (SiconvUser) usuarioAutenticado.getPrincipal();
    }
}
