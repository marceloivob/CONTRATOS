package br.gov.economia.maisbrasil.contratos.integracao.mail;

import java.util.Set;

import javax.mail.Address;

public interface SenderEmail {
	
	void send(Set<Address> destinatarios, String assunto, String textoMensagem);

}
