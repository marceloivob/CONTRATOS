package br.gov.economia.maisbrasil.contratos.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;

public class SiconvUser extends User implements SiconvPrincipal {

	private static final long serialVersionUID = -7519540497027145290L;
	private Long id;
	private Long idProposta;
	private String cpf;
	private String codigoEnte;
	private List<Profile> perfis = new ArrayList<>();
	private Collection<? extends GrantedAuthority> papeis;

	public SiconvUser(Claims claims, Collection<? extends GrantedAuthority> authorities) {
		super(claims.get("nome").toString(), "", authorities);

		this.id = Long.valueOf(claims.get("id").toString());
		this.idProposta = Long.valueOf(claims.get("idProposta").toString());
		this.cpf = claims.get("cpf").toString();

		if (claims.get("ente") != null) {
			this.codigoEnte = claims.get("ente").toString();
		}

		this.papeis = authorities;

		if (this.papeis == null) {
			this.papeis = new ArrayList<>();
		}

		this.perfis.add(Profile.GUEST);

		if (claims.get("tipoEnte") != null) {
			String tipoEnte = claims.get("tipoEnte").toString();
			if (tipoEnte != null) {
				this.perfis.add(Profile.valueOf(tipoEnte));
			}
		}
	}

	public boolean isAdmin() {
    	return this.isConcedente() &&
    			(this.hasRole(Role.ADMINISTRADOR_SISTEMA) || this.hasRole(Role.ADMINISTRADOR_SISTEMA_ORGAO_EXTERNO));
    }

    public boolean isConcedenteGCC() {
    	return this.isConcedente() && this.hasRole(Role.GESTOR_CONVENIO_CONCEDENTE);
    }

    public boolean isMandatariaGCIM() {
    	return this.isMandataria() && this.hasRole(Role.GESTOR_CONVENIO_INSTITUICAO_MANDATARIA);
    }

    public boolean isProponenteEditorContrato() {
    	return this.isProponente() &&
    			(this.hasRole(Role.GESTOR_FINANCEIRO_CONVENENTE) ||
    			 this.hasRole(Role.GESTOR_CONVENIO_CONVENENTE) ||
    			 this.hasRole(Role.OPERADOR_FINANCEIRO_CONVENENTE) ||
    			 this.hasRole(Role.FISCAL_CONVENENTE));
    }

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getCpf() {
		return cpf;
	}

	@Override
	public String getCodigoEnte() {
		return codigoEnte;
	}

	@Override
	public Long getIdProposta() {
		return idProposta;
	}

	@Override
	public List<Profile> getProfiles() {
		return this.perfis;
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles() {
		return this.papeis;
	}

	@Override
	public boolean isPropostaDoUsuario(Long idProposta) {
		return idProposta != null && this.getIdProposta().equals(idProposta);
	}

	@Override
	public boolean equals(Object rhs) {
		return super.equals(rhs);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("id: ").append(this.id).append("; ");
		sb.append("idProposta: ").append(this.idProposta).append("; ");
		sb.append("cpf: ").append(this.cpf).append("; ");
		sb.append("codigoEnte: ").append(this.codigoEnte).append("; ");
		sb.append("Perfis: ").append(this.perfis).append("; ");
		sb.append("papeis: ").append(this.papeis).append("; ");

		return sb.toString();
	}

	public boolean hasRole(Role role) {
		List<Role> roles = new ArrayList<>();
		roles.add(role);

		return this.hasAnyRoleOfList(roles);
	}

	public boolean hasAnyRoleOfList(List<Role> roles) {
		return this.getRoles().stream().anyMatch(papel -> roles.stream().anyMatch(role -> role.name().equals(papel.getAuthority())));
	}

}
