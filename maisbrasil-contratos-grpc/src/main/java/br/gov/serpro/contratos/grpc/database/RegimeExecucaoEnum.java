package br.gov.serpro.contratos.grpc.database;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RegimeExecucaoEnum {

	EPG("Empreitada por Preço Global"),

	EPU("Empreitada por Preço Unitário"),

	EPI("Empreitada Integral"),

	TRF("Tarefa"),

	RDC("RDC - Contratação Integrada"),

	RSI("RDC - Contratação Semi-integrada");

	private final String descricao;
}
