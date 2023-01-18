package br.gov.economia.maisbrasil.contratos.integracao.mail;

import br.gov.economia.maisbrasil.contratos.domain.EventoGeradorHistoricoEnum;
import br.gov.economia.maisbrasil.contratos.domain.ModalidadeInstrumentoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;

public class EmailTemplate {

	// Templates de Mensagens de Email
	
	private final String CABECALHO = "<p>Este e-mail foi gerado de forma automática pela Plataforma +Brasil. Por favor, não o responda.</p>\n" 
			+ "<p>Em caso de dúvida, entrar em contato com a Central de Atendimento da Plataforma +Brasil - 0800-978-9008.</p>\n\n<p>Prezado (a),</p>";
	
	private final String RODAPE = "<p>Para consulta, acesse a Proposta na Plataforma +Brasil, selecione a aba Instrumentos Contratuais, selecione a aba Checklist e observe o momento da emissão da AIO.</p>\n" 
			+ "<p>Para mais informações, acesse o manual correspondente no Portal da Plataforma +Brasil no endereço: < http://plataformamaisbrasil.gov.br></p>\n";

	private final String EMISSAO_AIO_ASSUNTO = "Emissão da AIO (Autorização de Início do Objeto) - {0} : {1}";
	private final String EMISSAO_AIO_POR_CONCLUSAO_CONTEUDO = "<p>Consta na Plataforma +Brasil a Emissão da AIO - Autorização de Início do Objeto , relacionada ao {0} de número {1}.</p>\n<p>A AIO foi emitida após a conclusão do instrumento contratual de número {2}.</p>\n";
	private final String EMISSAO_AIO_POR_VERIFICACAO_CONTEUDO = "<p>Consta na Plataforma +Brasil a Emissão da AIO - Autorização de Início do Objeto , relacionada ao {0} de número {1}.</p>\n<p>A AIO foi emitida a partir da opção de verificação da emissão da AIO.</p>\n";
	private final String EMISSAO_AIO_POR_BATCH_CONTEUDO = "<p>Consta Plataforma +Brasil a Emissão da AIO - Autorização de Início do Objeto , relacionada ao {0} de número {1}.</p>\n" 
			+ "<p>A AIO foi emitida pelo sistema, através de rotina automática.</p>\n";

	private final String PENDENCIA_LIBERACAO_VALORES_ASSUNTO = "AIO não emitida – Pendência: Recebimento de Recursos após o aceite da fase de análise do Projeto Básico - {0} : {1}";
	private final String PENDENCIA_LIBERACAO_VALORES_CONTEUDO = "<p>Consta Plataforma +Brasil que o instrumento contratual {1} de número {2] está pendente quanto a emissão da AIO (Autorização de Início do Objeto) no quesito relacionado a liberação de recursos após o aceite da fase de análise do Projeto Básico.</p>\n";
	
	private String getCorpoEmail(String mensagem) {
		StringBuilder html = new StringBuilder();
		html.append("<html>");
		html.append("<head>");
		html.append("	<meta charset='UTF-8'>");
		html.append("	<title>Plataforma +Brasil</title>");
		html.append("</head>");
		html.append(
				"<body topmargin='0' leftmargin='50' style='margin:0; padding:0; font-family: Arial, Helvetica, sans-serif; color: #444444;' link='#2C67CD' vlink='#205C90'>");
		html.append("<table width='100%' cellpadding='0' cellspacing='0'>");
		html.append("	<tr>");
		html.append(
				"		<th colspan='3' style='background: #1659BF; text-align: left; font-size: 14px; color: #ffffff; padding: 15px; padding-bottom: 0;'>");
		html.append("			Sistema de Convênios");
		html.append("		</th>");
		html.append("	</tr>");
		html.append("	<tr>");
		html.append(
				"		<th colspan='3' style='background: #1659BF; text-align: left; font-size: 50px; color: #ffffff; padding: 15px; padding-top: 5px; padding-bottom: 5px;'>");
		html.append("			Plataforma Mais Brasil");
		html.append("		</th>");
		html.append("	</tr>");
		html.append("	<tr>");
		html.append(
				"		<th colspan='3' style='background: #1659BF; text-align: left; font-size: 14px; color: #ffffff; padding: 15px; padding-top: 0;'>");
		html.append("			MINISTÉRIO DA ECONOMIA");
		html.append("		</th>");
		html.append("	</tr>");
		html.append("	<tr>");
		html.append("		<td colspan='3' style='background: #dfdfdf; height: 8px;'></td>");
		html.append("	</tr>");

		///////////////////////
		
		html.append("	<tr>");
		html.append(
				"		<td colspan='3' style='font-size: 14px; padding: 15px; padding-bottom: 30px;'><br /><br />");
		html.append(mensagem);
		html.append("<br /></td>");
		html.append("	</tr>");
		///////////////////////

		html.append("	<tr>");
		html.append("		<td colspan='3' style='background: #1659BF; color: #1659BF; height: 50px;'>&nbsp;</td>");
		html.append("	</tr>");
		html.append("</table>");
		html.append("</body>");
		html.append("</html>");

		return html.toString();
	}

	public String getAssuntoEmissaoAIO(PropostaBD proposta) {
		String modalidade = ModalidadeInstrumentoEnum.fromValor(proposta.getModalidade(), proposta.getTermoCompromissoTemMandatar()).getDescricao();
		String assunto = EMISSAO_AIO_ASSUNTO.replace("{0}", modalidade).replace("{1}", proposta.getIdSiconv().toString());
		return assunto;
	}

	public String getAssuntoPendenciaLiberacaoValores(PropostaBD proposta) {
		String modalidade = ModalidadeInstrumentoEnum.fromValor(proposta.getModalidade(), proposta.getTermoCompromissoTemMandatar()).getDescricao();
		String assunto = PENDENCIA_LIBERACAO_VALORES_ASSUNTO.replace("{0}", modalidade).replace("{1}", proposta.getIdSiconv().toString());
		return assunto;
	}
	
	public String getConteudoEmissaoAIO(PropostaBD proposta, ContratoBD contrato, EventoGeradorHistoricoEnum evento) {
		String modalidade = ModalidadeInstrumentoEnum.fromValor(proposta.getModalidade(), proposta.getTermoCompromissoTemMandatar()).getDescricao();
		String conteudo = "";
		
		switch (evento) {
			case EMISSAO_AIO_CONCLUSAO:
				conteudo = EMISSAO_AIO_POR_CONCLUSAO_CONTEUDO.replace("{0}", modalidade).replace("{1}", proposta.getIdSiconv().toString()).replace("{2}", contrato.getNumero());
				break;
			case EMISSAO_AIO_VERIFICACAO:
				conteudo = EMISSAO_AIO_POR_VERIFICACAO_CONTEUDO.replace("{0}", modalidade).replace("{1}", proposta.getIdSiconv().toString());
				break;
			case EMISSAO_AIO_ROTINA_AUTOMATICA:
				conteudo = EMISSAO_AIO_POR_BATCH_CONTEUDO.replace("{0}", modalidade).replace("{1}", proposta.getIdSiconv().toString());
				break;
			default:
				throw new IllegalArgumentException("Ao enviar e-mail, não foi possível identificar a origem de emissão da AIO da proposta: " + proposta);
		}
		
		return this.formatarConteudo(conteudo);
	}
	
	public String getConteudoPendenciaLiberacaoValores(PropostaBD proposta, ContratoBD contrato) {
		String modalidade = ModalidadeInstrumentoEnum.fromValor(proposta.getModalidade(), proposta.getTermoCompromissoTemMandatar()).getDescricao();
		String conteudo = PENDENCIA_LIBERACAO_VALORES_CONTEUDO.replace("{0}", contrato.getNumero()).replace("{1}", modalidade).replace("{2}", proposta.getIdSiconv().toString());

		return this.formatarConteudo(conteudo);
	}
	
	private String formatarConteudo(String conteudo) {
		String corpoEmail = getCorpoEmail(conteudo);

		StringBuilder conteudoMensagem = new StringBuilder();
		conteudoMensagem.append(CABECALHO);
		conteudoMensagem.append(corpoEmail);
		conteudoMensagem.append(RODAPE);

		return conteudoMensagem.toString();
	}
	

	

}
