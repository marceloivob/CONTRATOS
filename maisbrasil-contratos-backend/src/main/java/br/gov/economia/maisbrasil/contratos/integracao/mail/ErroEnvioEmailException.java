package br.gov.economia.maisbrasil.contratos.integracao.mail;

public class ErroEnvioEmailException extends RuntimeException {
	private static final long serialVersionUID = 785926310955320148L;

	public ErroEnvioEmailException(Exception e) {
		super("Não foi possível enviar o e-mail de aviso da emissão da AIO para os destinatários.");
	}

}
