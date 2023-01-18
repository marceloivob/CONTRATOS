package br.gov.economia.maisbrasil.contratos.bc.exception.handler;

import java.io.Serializable;

public class MensagemErroDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer code;
	private String value;
	
	public Integer getCode() {
		return code;
	}
	public void setCodigo(Integer code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

