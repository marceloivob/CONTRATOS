package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.format.DateTimeFormatter;

import br.gov.economia.maisbrasil.contratos.domain.EventoGeradorHistoricoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoHistoricoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;
import lombok.Data;

@Data
public class RegistroHistoricoContratoDTO {

    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy - HH:mm:ss";
	
	public Long idHistorico;
    public String numeroContratoTA;
	public String evento;
	public String situacao;
	public String dataHora;
	public String responsavel;
	
	public static RegistroHistoricoContratoDTO from(HistoricoBD historicoBD, boolean acessoLivre) {
		
		RegistroHistoricoContratoDTO registro = new RegistroHistoricoContratoDTO();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
		
		registro.setIdHistorico(historicoBD.getId());
		registro.setEvento(EventoGeradorHistoricoEnum.fromSigla(historicoBD.getEventoGerador()).getDescricao());
		registro.setSituacao(SituacaoHistoricoContratoEnum.fromSigla(historicoBD.getSituacaoContrato()).getDescricao());
		registro.setDataHora(historicoBD.getDataRegistro().format(formatter));
		registro.setResponsavel(registro.formatarResponsavel(historicoBD, acessoLivre));
		
		return registro;
	}
	
	private String formatarResponsavel(HistoricoBD historico, boolean acessoLivre) {
		String responsavelFormatado = this.formatarCpf(historico.getCpfResponsavel(), acessoLivre) + " - " + historico.getNomeResponsavel();
		
		return responsavelFormatado;
	}
	
	private String formatarCpf(String cpfSemFormatacao, boolean acessoLivre) {
		String cpfFormatado = cpfSemFormatacao;
		
		if (!cpfSemFormatacao.contains(".") && cpfSemFormatacao.length() <= 11) {
			if (cpfSemFormatacao.length() < 11) {
				cpfFormatado = "";
				for (int i = 0; i < (11-cpfSemFormatacao.length()); i++){
					cpfFormatado = cpfFormatado.concat("0");
				}
				cpfFormatado = cpfFormatado.concat(cpfSemFormatacao);
			}
			
			cpfFormatado = cpfFormatado.substring(0, 3).concat(".")
								.concat(cpfFormatado.substring(3,6)).concat(".")
								.concat(cpfFormatado.substring(6,9)).concat("-")
								.concat(cpfFormatado.substring(9));
		}
		
		if (acessoLivre) {
			if (cpfFormatado.length() == 14) {
				cpfFormatado = "***".concat(".")
						.concat(cpfFormatado.substring(4,7)).concat(".")
						.concat(cpfFormatado.substring(8,11)).concat("-")
						.concat("**");
				
			} else {
				cpfFormatado = ""; // veio fora do padrao de CPF
			}
			
		}
		
		return cpfFormatado;
	}
}
