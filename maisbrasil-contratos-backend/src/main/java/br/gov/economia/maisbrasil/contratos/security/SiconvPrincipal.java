package br.gov.economia.maisbrasil.contratos.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;

public interface SiconvPrincipal {

    Long getId();

    String getCpf();

    String getCodigoEnte();

    Long getIdProposta();

    List<Profile> getProfiles();

	Collection<? extends GrantedAuthority> getRoles();

    boolean isPropostaDoUsuario(Long idProposta);

    default boolean isMandataria() {
        return getProfiles().contains(Profile.MANDATARIA);
    }

    default boolean isProponente() {
        return getProfiles().contains(Profile.PROPONENTE);
    }

    default boolean isConcedente() {
        return getProfiles().contains(Profile.CONCEDENTE);
    }

    default boolean isGuest() {
        return getProfiles().contains(Profile.GUEST);
    }

    default boolean isApenasGuest() {
        return getProfiles().contains(Profile.GUEST) && getProfiles().size() == 1;
    }

    default boolean hasProfile(Profile... profiles) {
        return Arrays.stream(profiles).anyMatch(perfil -> getProfiles().contains(perfil));
    }

    default boolean hasRole(Role... roles) {
        return Arrays.stream(roles).anyMatch(role -> getRoles().contains(role.name()));
    }

    default Profile getProfile() {
        // Como todo usuário tem o perfil de Guest, ignora esse perfil,
        Optional<Profile> perfil = getProfiles().stream()
                .filter( profile -> !profile.equals(Profile.GUEST) )
                .findAny();

        if (perfil.isPresent()) {
            return perfil.get();
        } else {
            throw new IllegalStateException(
                    "O usuário possui APENAS perfil de GUEST. É esperado que também possua algum dos seguintes perfis: Mandatárias, Proponente ou Concedente");
        }
    }

}
