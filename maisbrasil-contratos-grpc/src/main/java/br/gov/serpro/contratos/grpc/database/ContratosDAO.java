package br.gov.serpro.contratos.grpc.database;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import br.gov.serpro.contratos.grpc.ContratoDetalhado;
import br.gov.serpro.contratos.grpc.FornecedorContratos;
import br.gov.serpro.contratos.grpc.NumeroContratoLicitacao;
import br.gov.serpro.contratos.grpc.Submeta;

public interface ContratosDAO {
 
	/*
	 * Consultar contratos por licitação
	 * 
	 * @VBR - 24/11/2020 
	 */	
   @SqlQuery("  Select distinct contrato.numero	                 								 \r\n" + 
   		     "  From contratos.contrato contrato												 \r\n" + 
   		 	 "  inner join contratos.proposta proposta on (contrato.proposta_id = proposta.id)	 \r\n" +
   		 	 "  inner join contratos.lote lote on (contrato.id = lote.contrato_id)	             \r\n" +
   		 	 "  inner join contratos.licitacao licitacao on (licitacao.id = lote.licitacao_id)	 \r\n" + 
   		     " Where proposta.id_siconv = :idSiconv\r\n" + 
   		     "   and licitacao.numero_ano = :numeroAnoLicitacao")
	@RegisterRowMapper(NumeroContratoLicitacaoMapper.class)
	List<NumeroContratoLicitacao> listaContratosLicitacao(@Bind("idSiconv") long idSiconv, @Bind("numeroAnoLicitacao") String numeroAnoLicitacao);		
	
	/*
	 * Consultar quantidade de contratos aptos a iniciar por fornecedor
	 * 
	 * @VBR - 21/11/2019
	 */
	@SqlQuery("select fornecedor.identificacao as cnpj, count(distinct lote.contrato_id) as quantidade	\n"
			+ "from contratos.fornecedor fornecedor														\n"
			+ "join contratos.lote lote on (fornecedor.id = lote.fornecedor_id)							\n"
			+ "join contratos.submeta submeta on (lote.id = submeta.lote_id)							\n"
			+ "join contratos.po po on (submeta.id = po.submeta_id)										\n"
			+ "join contratos.contrato contrato on (lote.contrato_id = contrato.id)						\n"
			+ "join contratos.aio aio on (contrato.proposta_id = aio.proposta_fk) 						\n"			
			+ "where																					\n"
			+ "contrato.in_situacao = 'CON'																\n"
			+ "and fornecedor.identificacao in (<listaCNPJ>)											\n"
			+ "group by 1")
	@RegisterRowMapper(FornecedorContratosMapper.class)
	List<FornecedorContratos> consultarQTDContratos(@BindList("listaCNPJ") List<String> listaCNPJ);

	@SqlQuery("select distinct contrato.id, contrato.numero, fornecedor.identificacao, 		\n"
			+ "		meta.in_social,															\n"
			+ "		po.in_acompanhamento_eventos,											\n"
			+ "		contrato.inicio_vigencia,												\n"
			+ "		contrato.fim_vigencia,													\n"
			+ "		contrato.data_assinatura,												\n"
			+ "		contrato.objeto,														\n"
			+ "		contrato.valor_total,													\n"
			+ "		SUM(submeta.vl_total_licitado),											\n"
			+ "		proposta.id_siconv,														\n"
			+ "		proposta.uf,															\n"
			+ "		proposta.modalidade,													\n"
			+ "		proposta.numero_convenio,												\n"
			+ "		proposta.ano_convenio,													\n"
			+ "		proposta.nome_objeto,													\n"
			+ "		proposta.data_assinatura_convenio,										\n"
			+ "     proposta.termo_compromisso_tem_mandatar,									\n"
			+ "     proposta.nome_proponente												\n"
			+ "from contratos.contrato contrato												\n"
			+ "join contratos.lote lote on (contrato.id = lote.contrato_id)					\n"
			+ "join contratos.fornecedor fornecedor on (lote.fornecedor_id = fornecedor.id)	\n"
			+ "join contratos.proposta proposta on (contrato.proposta_id = proposta.id)		\n"
			+ "join contratos.submeta submeta on (lote.id = submeta.lote_id)				\n"
			+ "join contratos.po po on (submeta.id = po.submeta_id)							\n"
			+ "join contratos.meta meta on (submeta.meta_id = meta.id)						\n"
			+ "join contratos.aio aio on (proposta.id = aio.proposta_fk)					\n"
			+ "where 																		\n"
			+ "contrato.in_situacao = 'CON'													\n"
			+ "and fornecedor.identificacao = :fornecedor									\n"
			+ "group by 1, 3, 4, 5, 12, 13, 14, 15, 16, 17, 18, 19, 20")
	@RegisterRowMapper(ContratoMapper.class)
	List<ContratoDetalhado> consultarContratosPorFornecedor(@Bind("fornecedor") String fornecedor);

	@SqlQuery("select distinct contrato.id, contrato.numero, fornecedor.identificacao, 		\n"
			+ "		meta.in_social,															\n"
			+ "		po.in_acompanhamento_eventos,											\n"
			+ "		contrato.inicio_vigencia,												\n"
			+ "		contrato.fim_vigencia,													\n"
			+ "		contrato.data_assinatura,												\n"
			+ "		contrato.objeto,														\n"
			+ "		contrato.valor_total,													\n"
			+ "		SUM(submeta.vl_total_licitado),											\n"
			+ "		proposta.id_siconv,														\n"
			+ "		proposta.uf,															\n"
			+ "		proposta.modalidade,													\n"
			+ "		proposta.numero_convenio,												\n"
			+ "		proposta.ano_convenio,													\n"
			+ "		proposta.nome_objeto,													\n"
			+ "		proposta.data_assinatura_convenio,										\n"
			+ "     proposta.termo_compromisso_tem_mandatar,								\n"
			+ "     proposta.nome_proponente												\n"
			+ "from contratos.contrato contrato												\n"
			+ "join contratos.lote lote on (contrato.id = lote.contrato_id)					\n"
			+ "join contratos.fornecedor fornecedor on (lote.fornecedor_id = fornecedor.id)	\n"
			+ "join contratos.proposta proposta on (contrato.proposta_id = proposta.id)		\n"
			+ "join contratos.submeta submeta on (lote.id = submeta.lote_id)				\n"
			+ "join contratos.po po on (po.submeta_id = submeta.id)							\n"
			+ "join contratos.meta meta on (submeta.meta_id = meta.id)						\n"
			+ "join contratos.aio aio on (proposta.id = aio.proposta_fk)					\n"	
			+ "where 																		\n"
			+ "contrato.in_situacao = 'CON'													\n"
			+ "and contrato.id = :idContrato                                                \n"
			+ "group by 1, 3, 4, 5, 12, 13, 14, 15, 16, 17, 18, 19, 20")
	@RegisterRowMapper(ContratoMapper.class)
	ContratoDetalhado consultarContratoPorId(@Bind("idContrato") long idContrato);

	@SqlQuery("select submeta.id, 										\n"
			+ "submeta.id_submeta_vrpl, 								\n"
			+ "concat(meta.numero, '.',submeta.numero) as numero, 		\n"
			+ "submeta.descricao, 										\n"
			+ "submeta.vl_total_licitado								\n" 
			+ "from contratos.submeta submeta							\n" 
			+ "join contratos.meta meta on (submeta.meta_id = meta.id)	\n" 
			+ "join contratos.lote lote on (submeta.lote_id = lote.id)	\n" 
			+ "where lote.contrato_id = :idContrato")
	@RegisterRowMapper(SubmetaMapper.class)
	List<Submeta> consultarSubmetasPorIdContrato(@Bind("idContrato") long idContrato);

	
	@SqlQuery("select 	proposta.id as prop_id, 							\n"
			+ "		proposta.numero_convenio as prop_numero, 				\n"
			+ "		proposta.ano_convenio as prop_ano, 						\n"
			+ "		proposta.nome_objeto as prop_objeto, 					\n"
			+ "		proposta.uf as prop_uf, 								\n"
			+ "		proposta.id_siconv as prop_IdSiconv, 					\n"
			+ "		proposta.modalidade as prop_modalidade,					\n"
			+ "     proposta.termo_compromisso_tem_mandatar as prop_possuiInstituicaoMandataria,				\n"
			+ "		proposta.nome_proponente as prop_nomeProponente,		\n"
			+ "		contrato.id as contrato_id, 							\n"
			+ "		contrato.numero as contrato_numero, 					\n"
			+ "		fornecedor.identificacao as contrato_cnpjFornecedor,	\n"
			+ "		CASE 													\n"  
			+ "			when ((contrato.in_situacao = 'CON') and (aio.proposta_fk is not null)) then TRUE 	\n" 
			+ "			else FALSE											\n"  
			+ "		END as contrato_aptIniciar,								\n"
			+ "		po.in_acompanhamento_eventos as contrato_porEventos,	\n"
			+ "																\n"
			+ "		lote.id as lote_id, 									\n"
			+ "		lote.numero as lote_numero,								\n"
			+ "																\n"
			+ "      submeta.id as submeta_id, 								\n"
			+ "      submeta.id_submeta_vrpl as submeta_idVRPL,				\n"		
			+ "      concat(meta.numero, '.',submeta.numero) as submeta_numero, \n"
			+ "      submeta.descricao as submeta_descricao, 				\n"
			+ "      submeta.vl_total_licitado as submeta_valorTotal, 		\n"
			+ "      submeta.in_situacao as submeta_situacao,				\n"
			+ "      submeta.in_regime_execucao as submeta_regimeExecucao	\n"
			+ "																\n"
			+ "from contratos.proposta proposta								\n"
			+ "left join contratos.contrato contrato on (proposta.id = contrato.proposta_id)		\n"
			+ "left join contratos.lote lote on (contrato.id = lote.contrato_id) 					\n"
			+ "left join contratos.fornecedor fornecedor on (lote.fornecedor_id = fornecedor.id)	\n"
			+ "left join contratos.submeta submeta on (lote.id = submeta.lote_id)					\n"
			+ "left join contratos.po po on (submeta.id = po.submeta_id)							\n"
			+ "left join contratos.meta meta on (submeta.meta_id = meta.id)   						\n"
			+ "left join contratos.aio aio on (proposta.id = aio.proposta_fk)						\n"
			+ "where proposta.id_siconv = :idProposta 												\n"
			+ "and contrato.in_situacao = 'CON'")			
	@UseRowReducer(PropostaReducer.class)
	@RegisterFieldMapper(value = PropostaBD.class, prefix = "prop")
	@RegisterFieldMapper(value = ContratoBD.class, prefix = "contrato")
	@RegisterFieldMapper(value = LoteBD.class, prefix = "lote")
	@RegisterFieldMapper(value = SubmetaBD.class, prefix = "submeta")
	PropostaBD consultarPropostaPorId(@Bind("idProposta") long idProposta);

	@SqlQuery("select 	lote.id as lote_id, 											\n"
			+ "		lote.numero as lote_numero,											\n"
			+ "																			\n"
			+ "        submeta.id as submeta_id,  										\n"
			+ "		   submeta.id_submeta_vrpl as submeta_idVRPL,						\n"
			+ "        concat(meta.numero, '.',submeta.numero) as submeta_numero, 		\n"
			+ "        submeta.descricao as submeta_descricao, 							\n"
			+ "        submeta.vl_total_licitado as submeta_valorTotal,					\n"
			+ "        submeta.in_situacao as submeta_situacao							\n"
			+ "																			\n"
			+ "from contratos.lote lote  												\n"
			+ "join contratos.submeta submeta on (lote.id = submeta.lote_id)			\n"
			+ "join contratos.proposta proposta on (submeta.proposta_id = proposta.id)	\n"			
			+ "join contratos.meta meta on (submeta.meta_id = meta.id)  				\n"
			+ "where proposta.id_siconv = :idProposta									\n"
			+ "and lote.contrato_id is null")
	@UseRowReducer(LoteReducer.class)
	@RegisterFieldMapper(value = LoteBD.class, prefix = "lote")
	@RegisterFieldMapper(value = SubmetaBD.class, prefix = "submeta")
	List<LoteBD> consultarLotesSemContratoPorIdProposta(@Bind("idProposta") long idProposta);

}
