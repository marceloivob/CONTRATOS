package br.gov.economia.maisbrasil.contratos.core.exceptions;
import static br.gov.economia.maisbrasil.contratos.core.exceptions.Severity.ERROR;

import org.springframework.http.HttpStatus;

public enum ErrorInfo {
    ERRO_PROPOSTA_NAO_ENCONTRADA(513493, "O conteúdo que você procura não está disponível ou o endereço foi digitado incorretamente.", ERROR, HttpStatus.NOT_FOUND),
	ERRO_LICITACAO_NAO_ENCONTRADA(543243, "Nenhuma licitação encontrada para a proposta {0}.", ERROR, HttpStatus.NOT_FOUND),
	ERRO_LICITACAO_NAO_ENCONTRADA_PROPOSTA(7034561, "Não existem dados referentes a esta proposta neste módulo (Instrumentos Contratuais), pois esta proposta não segue o fluxo de cadastro e aceite do processo licitatório pelo módulo da VRPL "
			+ "                                     (Verificação do Resultado do Processo Licitatório) e, consequentemente, também não segue o fluxo de cadastro do contrato e emissão da AIO por este módulo. Para esta proposta, o aceite é feito "
			+ "                                      pela funcionalidade Processo de Execução do SICONV, bem como o contrato é cadastrado por meio da funcionalidade Contratos do SICONV.", ERROR, HttpStatus.NOT_FOUND),
	ERRO_PROPOSTA_SEM_VRPL_ACEITO(540930,"Ainda não é possível acessar as informações e funcionalidades deste módulo, pois ainda não houve um aceite de processo licitatório no módulo da VRPL (Verificação do Resultado do Processo Licitatório).",
			ERROR),
//    ERRO_ACIONAMENTO_SERVICO_SICONV_LICITACOES(543245,
//            "Erro ao acionar serviço para obtenção da lista de licitações associadas à proposta.", ERROR),
//    // nao ha rng para isso
//    ERRO_ACIONAMENTO_SERVICO_SICONV_CTEF(1,
//            "Erro ao acionar serviço para obtenção do contrato de fornecimento relativo à licitação.", ERROR),
//
//    ERRO_ACESSO_RECURSO_NAO_PERMITIDO(2, "Usuário está tentando acessar recurso que não tem permissão.", ERROR,
//            FORBIDDEN),
    //ERRO_ACESSO_PERFIL_NAO_AUTORIZADO(2, "Usuário não possui perfil necessário para realizar a ação.", ERROR, FORBIDDEN),
	ERRO_ACESSO_PERFIL_NAO_AUTORIZADO(2, "Usuário não possui perfil necessário para realizar a ação.", ERROR, HttpStatus.FORBIDDEN),
    //ERRO_ACESSO_PERFIL_NAO_INFORMADO(3, "Usuário não possui nenhum perfil associado.", ERROR, FORBIDDEN),
	ERRO_ACESSO_PERFIL_NAO_INFORMADO(3, "Usuário não possui nenhum perfil associado.", ERROR, HttpStatus.FORBIDDEN),
//
//    // mensagens da regras de negocio 520637
    ERRO_CAMPO_OBRIGATORIO(5206371, "Campo obrigatório não informado.", ERROR),
//    ERRO_DATA_EMISSAO_POSTERIOR(5206373, "A data de emissão não pode ser maior que a data corrente.", ERROR),
//
//    // anexo - sem rng para essas situacoes
    ERRO_LICITACAO_DO_ANEXO_NAO_ENCONTRADA(4, "Licitação do anexo não encontrada.", ERROR),
    ERRO_PARAMETROS_DO_ANEXO_INVALIDOS(5, "Existe ao menos um parâmetro inválido.", ERROR),
    ERRO_ANEXO_SEM_EXTENSAO(6, "O anexo informado não possui formato definido.", ERROR),
     ANEXO_NAO_ENCONTRADO(7, "O anexo informado não foi encontrado.", ERROR),
//
//    // mensagens da regras de negocio 511551
    ERRO_EXISTE_ANEXO_COM_MESMA_DESCRICAO(5115511, "Já existe um anexo com esta descrição.", ERROR),
    ERRO_EXISTE_ANEXO_COM_MESMO_NOME(5115512, "Já existe um anexo com este arquivo.", ERROR),
    ERRO_FORMATO_ANEXO_NAO_PERMITIDO(5115513, "Formatos permitidos: PDF, DOC, DOCX, XLS, XLSX, JPG, JPEG, PNG, ODT, ODS e DWG.", ERROR),
    ERRO_TAMANHO_MAXIMO_ANEXO_EXCEDIDO(5115514, "O tamanho máximo do arquivo é 10MB.", ERROR),
//    ERRO_CPF_REPETIDO(5115517, "Já existe CPF cadastrado.", ERROR),
//    ATIVIDADE_INVALIDA(5115519, "Atividade ou Título Inválido.", ERROR),
//    CPF_INVALIDO(5115520, "CPF Inválido.", ERROR),
//    SUBMETA_NAO_ENCONTRADA(5115521, "Submeta Nao Encontrada.", ERROR),
//    EVENTO_NUMERO_REPETIDO(511419, "Número do Evento já cadastrado. Favor informar um novo valor.", ERROR),
//    EVENTO_NAO_ENCONTRADO(5115523, "Evento Não encontrado.", ERROR, NOT_FOUND),
//    PO_NAO_ENCONTRADA(5115524, "Planilha Orçamentária Não Encontrada.", ERROR, NOT_FOUND),
//	DATA_INICIO_OBRA_INVALIDA(511112,
//			"A data informada não pode ser menor que a data inicialmente prevista ({0}) para início de obra na fase de análise do Projeto Básico",
//			ERROR),
//    FRENTE_DE_OBRA_NUMERO_REPETIDO(511450, "Número da Frente de Obra já cadastrado. Favor informar um novo valor.", ERROR),
//    FRENTE_DE_OBRA_NAO_ENCONTRADO(511561,"Frente de Obra nao Encontrada", ERROR),
//    MACROSERVICO_NAO_ENCONTRADO(511562, "Macrosserviço não encontrado!", ERROR, NOT_FOUND),
//    SERVICO_NAO_ENCONTRADO(511563, "Serviço não encontrado!", ERROR, NOT_FOUND),
//    MACROSERVICOPARCELA_NAO_ENCONTRADO(511565, "Parcela não encontrada!", ERROR, NOT_FOUND),
//    MACROSERVICOPARCELA_NEGATIVA (511566, "Parcela não pode ser negativa!", ERROR),
//    EVENTOFRENTEOBRA_NAO_ENCONTRADO(511567, "Evento frente Obra não encontrado!", ERROR, NOT_FOUND),
//    SUBMETAS_TIPO_DIFERENTE_LICITACAO(511568, "Não é possível associar na mesma licitação lotes com submetas relacionados a Engenharia e lotes com submetas de Trabalho Social!" , ERROR),
//    PO_ACOMPANHAMENTO_DIFERENTE_LICITACAO(511569, "Não é possível associar lotes contendo submetas de Planilhas Orçamentárias acompanhadas por evento e sem acompanhamento por evento em uma mesma licitação!", ERROR),
//    SUBMETAS_REGIME_DIFERENTE_LICITACAO(511570, "Não é possível associar lotes com submetas de regimes de execução diferentes!" , ERROR),
//
//    // mensagens quadro resumo
//	PROCESSO_LICITATORIO_NAO_CONCLUIDO(570991,
//			"A licitação ({0}) não está com a situação concluída no sistema de licitações.", ERROR),
//    EXISTE_PO_SEM_EVENTOS_NA_LICITACAO(570992, "Não existe Evento cadastrado para a PO ({0}).", ERROR),
//    EXISTE_PO_SEM_FRENTE_DE_OBRA_NA_LICITACAO(570993, "Não existe Frente de Obra cadastrada para a PO ({0})", ERROR),
//    VALOR_LICITADO_EXCEDE_VALOR_APROVADO(570994, "O valor do campo 'Total Licitado' de todas as PO(s) (R$ {0}) não pode exceder o valor total do {2} (R$ {1})." , ERROR),
//    SERVICO_SEM_EVENTO_EXCEPTION(570995, "O serviço {0} da submeta {1} não possui evento associado.", ERROR),
//    SERVICO_SEM_FRENTE_DE_OBRA_EXCEPTION(570996, "Não existe frente de obra associada ao serviço {0}", ERROR),
//	EVENTO_SEM_SERVICO_ASSOCIADO(586175, "O(s) evento(s) listado(s) abaixo não possuem serviços associados: {0}",
//			ERROR),
//	FRENTE_DE_OBRA_SEM_QUANTIDADE_INFORMADA(586178,
//			"A(s) Frente(s) de Obra listada(s) abaixo não possuem quantidades informadas: {0}", ERROR),
//
//    SERVICO_SEM_QUANTIDADES_INFORMADAS_EXCEPTION(570997, "O serviço {0} não possui quantidades informadas.", ERROR),
//
//	QUANTIDADES_TOTAL_SERVICO_DIFERENTE_TOTAL_ACEITO_ANALISE_EXCEPTION(586181,
//			"A quantidade total do serviço {0} da submeta {1} difere do total aceito na análise.", ERROR),
//
//	POS_DE_UM_LOTE_COM_DATABASE_DIFERENTE(586542,
//			"Não é possível ter valores diferentes para a data base em planilhas orçamentárias em um mesmo lote (Lote {0}):\n" +
//			"\n" +
//			"Lista das submetas e os valores da data base, conforme abaixo:\n" +
//			"\n {1}", ERROR),
//
//	ITENS_PO_DE_UM_LOTE_COM_PRECO_DIFERENTE(586543,
//			"Não é possível ter preços unitários diferentes para itens de PO de um mesmo lote (Lote {0}), considerando a mesma Fonte e o mesmo Código:\n" +
//			"Lista dos itens com mesma fonte e mesmo código, mas com preços unitários diferentes, conforme abaixo:\n" +
//			"Fonte: {1} - Código : {2}\n" +
//			"      Lista dos itens com preços diferentes, conforme abaixo:\n" +
//			"{3}", ERROR),
//
//
//
//    CONSIDERACOES_OBRIGATORIO_COMPLEMENTACAO(5676671, "O campo \"Considerações\" é obrigatório ao Solicitar Complementação ao Convenente.", ERROR),
//    CONSIDERACOES_OBRIGATORIO_ACEITAR_INVIAVEL(5676672, "O campo \"Considerações\" é obrigatório ao Aceitar a Documentação Orçamentária e existe ao menos um Laudo inviável.", ERROR),
//    CONSIDERACOES_OBRIGATORIO_REJEITAR_VIAVEL(5676673, "O campo \"Considerações\" é obrigatório ao Rejeitar a Documentação Orçamentária e existe ao menos um Laudo viável.", ERROR),
//    CFF_COM_EVENTO_FALTANDO_ASSOCIACAO(570998, "O valor acumulado do Cronograma Físico-Financeiro não está compatível com o valor total da PO, favor incluir o evento {0} na tabela 'Visão Frentes de Obra por Evento.", ERROR),
//    PERIODO_CONCLUSAO_NAO_PREENCHIDO(517066, "Existe número do período de conclusão para frente de obra não preenchido no CFF por Evento. Evento: {0}.", ERROR),
//    MACROSERVICO_SEM_PERCENTUAL_PARCELA(516489,"O valor acumulado do Cronograma Físico-Financeiro não está compatível com o valor total da PO, favor incluir o macrosserviço {0} na tabela ‘Visão das Parcelas por Macrosserviço’", ERROR),
//    TODOS_PERCENTUAIS_PARCELA_ZERO(570999, "Todos os valores percentuais das parcelas estão zerados no CFF sem Evento da submeta {0}.", ERROR),
//	SOMATORIO_LICITACAO_EXCEDE_VALOR_APROVADO(571000, " O valor total {0} do QCI (R$ {1}) excede o valor {2} do {3} (R$ {4}). ", ERROR),
//    TOTAIS_SUBMETA_EXCEDE_TOTAL_LICITADO(567909, "O valor do campo \"Total Licitado\" (R$ {0}) da PO \"{1}\" deve ser igual ao somatório (R$ {2}) dos valores de \"Repasse Licitado\", \"Contrapartida Licitada\" e \"Outros Licitado\" do QCI da respectiva submeta.", ERROR),
//    SOMATORIO_POS_DIFERE_VALOR_CTEF(571003, "O somatório dos valores totais da(s) PO(s) licitada(s) deve ser igual ao valor da licitação cadastrado na funcionalidade de licitação do SICONV.", ERROR),
//    MUDANCA_ESTADO_DOCUMENTACAO_NAO_PERMITIDA(5676620, "A mudança de estado solicitada da documentação não é permitida neste contexto. ", ERROR),
//
//
//    // mensagens laudos
//    TEMPLATELAUDO_NAO_ENCONTRADO(1722393, "Template de Laudo não encontrado!", ERROR, NOT_FOUND),
//    GRUPOPERGUNTA_NAO_ENCONTRADO(1722393, "Grupo de Pergunta não encontrado!", ERROR, NOT_FOUND),
//    PERGUNTA_NAO_ENCONTRADA(1722393, "Pergunta não encontrada!", ERROR, NOT_FOUND),
//    RESPOSTA_NAO_ENCONTRADA(1722393, "Resposta não encontrada!", ERROR, NOT_FOUND),
//    PENDENCIA_NAO_ENCONTRADA(1722393, "Pendência não encontrada!", ERROR, NOT_FOUND),
//    LAUDO_NAO_ENCONTRADO(1722393, "Laudo não encontrado!", ERROR, NOT_FOUND),
//    LAUDOGRUPOPERGUNTA_NAO_ENCONTRADO(1722393, "Laudo Grupo Pergunta não encontrado!", ERROR, NOT_FOUND),
//    USUARIO_NAO_PODE_ASSINAR_PARECER(1722393, "Usuário não pode assinar este Parecer!", ERROR),
//    USUARIO_JA_ASSINOU_PARECER(1722393, "Usuário já assinou este Parecer!", ERROR),
//
//    // Validacao data base
//    SEM_CARGA_SINAPI_PARA_DATA_BASE(585574, "Não há dados SINAPI carregados para a data base {0}.", WARN),
//    DATA_BASE_DIFERENTE_NO_MESMO_LOTE(581044, "Foi informada uma data base diferente em outra Planilha Orçamentária associada a este mesmo lote (Lote {0}).", WARN),
//    SEM_CARGA_SINAPI_PARA_DATA_BASE_DIFERENTE_MESMO_LOTE(5855744, "Não há dados SINAPI carregados para a data base {0}. Foi informada uma data base diferente em outra Planilha Orçamentária associada a este mesmo lote (Lote {1}).", WARN),
//
//
//    ERRO_NAO_EXISTE_CPS_VIGENTE_PARA_PROPOSTA(554737, "Prezado(a), para esta proposta, não existe um Contrato de Prestação de Serviço (CPS) vigente. \n"+
//    		"Como não existe um CPS vigente para esta proposta será necessário que o\n" +
//    		"Gestor de Convênio da Instituição Mandatária ou Concedente verifique e regularize a situação\n" +
//    		"em que se encontra o cadastro do CPS no sistema, acessando o menu\n" +
//    		"Cadastros e em seguida o item de menu Manter CPS.", ERROR),
//
//
//
//    //Mensagens email
//    ERRO_ENVIO_EMAIL(515884, "Não foi possível enviar o e-mail para os destinatários.", ERROR),
//    EVENTO_FRENTE_OBRA_SEM_MES(515884, "A PO {0} possui evento de frente de obra sem mês de conclusão.", ERROR),
//    ERRO_CAMPO_OBRIGATORIO_LAUDO(515885,"O campo {0} deve ser preenchido!", ERROR);
	  PROPOSTA_NAO_ENCONTRADA(513493, "Não foi possível encontrar a proposta Indicada.", ERROR),
	  CONTRATO_NAO_ENCONTRADO(515886, "Contrato nao encontrado!", ERROR),
	  JA_EXISTE_CONTRATO_CONCLUIDO_COM_O_NUMERO(6067220, "Já existe um Instrumento Contratual concluído com o número ({0}). Favor verificar o número informado e tentar novamente.", ERROR),
	  DATA_EMISSAO_AIO_SUPERIOR_A_ATUAL(773104,"A data de emissão da AIO ({0}) não pode ser superior à data atual ({1}).", ERROR),
	  DATA_ASSINATURA_SUPERIOR_A_ATUAL(6067221,"A data de assinatura ({0}) não pode ser superior à data atual ({1}).", ERROR),
	  DATA_ASSINATURA_INFERIOR_A_ACEITE(6067222,"A data de assinatura ({0}) não pode ser inferior a data de aceite ({1}) da fase de análise do Projeto Básico.", ERROR),
	  DATA_PUBLICACAO_SUPERIOR_A_ATUAL(6067223,"A data de publicação ({0}) não pode ser superior à data atual ({1}).", ERROR),
	  DATA_PUBLICACAO_INFERIOR_A_ASSINATURA(6067224,"A data de publicação informada ({0}) não pode ser menor que a data de assinatura ({1}).", ERROR),
	  INICIO_VIGENCIA_INFERIOR_A_ASSINATURA(6067225,"A data de início de vigência informada ({0}) não pode ser menor que a data de assinatura ({1}).", ERROR),
	  INICIO_VIGENCIA_SUPERIOR_A_FIM_VIGENCIA(6067226,"A data de início de vigência informada ({0}) não pode ser superior à data de fim de vigência ({1}).", ERROR),
	  FIM_VIGENCIA_INFERIOR_A_ASSINATURA(6067227,"A data de fim de vigência informada ({0}) não pode ser inferior à data de assinatura ({1}).", ERROR),
	  FIM_VIGENCIA_INFERIOR_A_PUBLICACAO(6067228,"A data de fim de vigência informada ({0}) não pode ser inferior à data de publicação ({1}).", ERROR),
	  META_NAO_ENCONTRADO(515887,"Meta não encontrada!", ERROR),
	  FORNECEDOR_NAO_ENCONTRADO(515888,"Fornecedor não encontrado!", ERROR),
	  HISTORICO_NAO_ENCONTRADO(515889,"Historico não encontrado!", ERROR),
	  LICITACAO_NAO_ENCONTRADO(515890,"Licitação não encontrada!", ERROR),
	  LOTE_NAO_ENCONTRADO(515891,"Lote não encontrada!", ERROR),
	  DOCCOMPLEMENTAR_NAO_ENCONTRADO(515892,"Lote não encontrada!", ERROR),
	  SUBMETA_NAO_ENCONTRADO(515893,"Submeta não encontrada!", ERROR),
	  SUBMETADOCCOMPLEMENTAR_NAO_ENCONTRADO(515894,"Relação Submeta Documento complementar não encontrada!", ERROR),
	  TERMOADITIVO_NAO_ENCONTRADO(515894,"Termo aditivo não encontrado!", ERROR),
	  PO_NAO_ENCONTRADO(515895, "Po não encontrada!", ERROR),

	  SERVICO_MEDICAO_INDISPONIVEL(605364, "Serviço Indisponível. Erro ao tentar consultar dados do sistema de Acompanhamento - Medição. Favor tentar novamente mais tarde.", ERROR ),
	  SERVICO_SICONV_INDISPONIVEL(605343, "Serviço Indisponível. Erro ao tentar consultar dados do SICONV. Favor tentar novamente mais tarde.", ERROR ),
	  SERVICO_VRPL_INDISPONIVEL(605344, "Serviço Indisponível. Erro ao tentar consultar dados do módulo de Verificação do Resultado do Processo Licitatório. Favor tentar novamente mais tarde.", ERROR ),
	  ALTERACAO_NAO_PERMITIDA_MEDICAO_INICIADA(607395, "Não é possível alterar os dados deste instrumento contratual, pois já existem informações referentes a este instrumento contratual inseridas no módulo de Acompanhamento de Obras e Serviços de Engenharia da Plataforma +Brasil." , ERROR),
	  EXCLUSAO_NAO_PERMITIDA_MEDICAO_INICIADA(605360, "Não é possível excluir este instrumento contratual, pois já existem informações referente a este instrumento contratual que foram inseridas no módulo de Acompanhamento de Obras e Serviços de Engenharia da Plataforma +Brasil." , ERROR),

	  CHECKLIST_NAO_OK(515896, "Não é possível realizar a conclusão do instrumento contratual, pois o(s) item\\itens exibido(s) como pendente(s) na tabela de verificação para a conclusão de instrumento contratual abaixo não foi\\foram atendido(s).", ERROR),

	  CONTRATO_NAO_CONCLUIDO(5158960, "O presente Instrumento Contratual não encontra-se concluído.", ERROR),

	  ERRO_AO_ACIONAR_SERVICO_SICONV(616273, "Erro ao tentar registrar evento de emissão da AIO no histórico SICONV.", ERROR),

	  ALTERACAO_NAO_PERMITIDA(616759, "Não é possível realizar a alteração, pois o(s) seguinte(s) item/itens do checklist referente(s) à conclusão do instrumento contratual não foi/foram atendido(s): {0}", ERROR),
	  EMPRESA_NAO_CADASTRADA_MAIS_BRASIL(607213, "Não existe na Plataforma +Brasil, o cadastro da empresa {0} - {1}. Para concluir este instrumento contratual, será necessário que a empresa seja cadastrada na Plataforma +Brasil. Favor solicitar à empresa executora que providencie o respectivo cadastro na Plataforma +Brasil.", ERROR),
	  ALTERACAO_PARCIAL_MEDICAO_INICIADA(617277, "Não é possível alterar às seguintes informações deste instrumento contratual: Número do instrumento contratual, a empresa fornecedora, o número do processo de execução e os lotes de licitação associados. As alterações destas informações do instrumento contratual são sensíveis e já foram vinculadas às demais fases da execução da obra como, por exemplo, o módulo de Acompanhamento de Obras e Serviços da Plataforma +Brasil.", ERROR),
	  EMISSAO_AIO_INATIVADA_PERIODO_ELEITORAL(626079, "Não é possível emitir AIO devido ao período eleitoral", ERROR),
	  EXCLUSAO_NAO_PERMITIDA_EXISTE_AIO(752505, "Não é possível excluir este instrumento contratual pois existe AIO (Autorização de Início de Obra) emitida. Caso seja necessário cancelar a emissão da AIO, será preciso solicitar ao Concedente ou Mandatária, o cancelamento da emissão da AIO.", ERROR),
	  INCOERENCIA_AO_EXCLUIR_CONTRATO(999999, "Incoerência detectada ao excluir contrato - o único contrato concluído restante é diferente do esperado.", ERROR),
	  TERMO_ADITIVO_UNICO_EM_RASCUNHO(780954, "Só pode haver um termo aditivo “Em Rascunho” por instrumento contratual.", ERROR),
	  ERRO_INTERNO_TICKET(999998, "Erro Interno. Ticket gerado: {0}", ERROR),
	  CONTRATO_NAO_PODE_SER_ADITIVADO(605192, "O instrumento contratual está dentro da vigência e “Em Rascunho”, portanto, não pode ser aditivado.", ERROR),
	  
	  NOVO_FIM_VIGENCIA_INFERIOR_A_INICIO_VIGENCIA(7782002,"A data de fim de vigência informada  ({0}) não pode ser inferior à data de início de vigência ({1}).", ERROR),
	  NOVO_FIM_VIGENCIA_INFERIOR_A_ASSINATURA(7782003,"A data de fim de vigência informada  ({0}) não pode ser inferior à data de assinatura ({1}).", ERROR),
	  NOVO_FIM_VIGENCIA_INFERIOR_A_PUBLICACAO(7782004,"A data de fim de vigência informada ({0}) não pode ser inferior à data de publicação ({1}).", ERROR),
	  NOVO_FIM_VIGENCIA_INFERIOR_A_FIM_VIGENCIA_ATUAL(7782005,"A data de fim de vigência informada ({0}) não pode ser inferior à data de fim de vigência atual do instrumento contratual ({1}).", ERROR),
	  
	  JA_EXISTE_TERMO_ADITIVO_COM_O_NUMERO(7782001, "Já existe termo aditivo com este número.", ERROR),
	  
	  CONCLUSAO_TERMO_ADITIVO_SEM_ANEXO_TERMO_ADITIVO(7784560, "Não foi identificado o anexo do tipo \"Termo Aditivo\" nesta solicitação.", ERROR),
	  CONCLUSAO_TERMO_ADITIVO_SEM_ANEXO_PUBLICACAO_TERMO_ADITIVO(7784561, "Não foi identificado o anexo do tipo \"Publicação de Termo Aditivo\" nesta solicitação.", ERROR);
	

	private HttpStatus httpStatus;
    private final Integer codigoRN;
    private final String mensagem;
    private final Severity severity;

    ErrorInfo(final Integer codigoRN, final String mensagem, final Severity severity) {
        this.codigoRN = codigoRN;
        this.mensagem = mensagem;
        this.severity = severity;
    }

    ErrorInfo(final Integer codigoRN, final String mensagem, final Severity severity, final HttpStatus codigoHttp) {
        this(codigoRN, mensagem, severity);
        this.httpStatus = codigoHttp;
    }

    public Integer getCodigo() {
        return codigoRN;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Severity getSeverity() {
        return severity;
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

//    public Status getCodigoHttp() {
//        return codigoHttp;
//    }


}
