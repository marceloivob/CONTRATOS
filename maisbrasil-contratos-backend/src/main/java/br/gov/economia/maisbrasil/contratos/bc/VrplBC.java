package br.gov.economia.maisbrasil.contratos.bc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.gov.economia.maisbrasil.contratos.bc.exception.PropostaNaoEncontradaException;
import br.gov.economia.maisbrasil.contratos.core.exceptions.Severity;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.LicitacaoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.MetaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.MensagemDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.ConvenioIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.FornecedorIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LicitacaoIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LoteIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.MetaIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PoIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.PropostaIntegracao;
import br.gov.economia.maisbrasil.contratos.domain.integracao.SubmetaIntegracao;
import br.gov.economia.maisbrasil.contratos.repository.SiconvaoRepository;
import br.gov.economia.maisbrasil.contratos.repository.VrplRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.ContratoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.FornecedorDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.LicitacaoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.LoteDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.MetaDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.PoDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.PropostaDAO;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.SubmetaDAO;

@Controller
public class VrplBC {

	private final Logger log = LoggerFactory.getLogger(VrplBC.class);

	private final VrplRepository vrplRepository;
	
	private final SiconvaoRepository siconvaoRepository;

	@Autowired
	private Jdbi jdbi;

	@Autowired
	public VrplBC(VrplRepository vrplRepository, SiconvaoRepository siconvaoRepository) {

		this.vrplRepository = vrplRepository;
		this.siconvaoRepository = siconvaoRepository;
	}

	public Optional<PropostaIntegracao> recuperarPropostaPorId(Long id) {

		Objects.requireNonNull(id);

		Optional<PropostaIntegracao> propostaBD = this.vrplRepository.recuperarPropostaPorId(id);

		if (propostaBD.isPresent()) {
			return propostaBD;
		} else {
			throw new PropostaNaoEncontradaException();
		}
	}

	public List<LicitacaoIntegracao> recuperarLicitacoesAceitasDaProposta(Long idProposta) {

		Objects.requireNonNull(idProposta);

		List<LicitacaoIntegracao> licitacoes = this.vrplRepository.recuperarLicitacoesAceitasDaProposta(idProposta);

		return licitacoes;
	}

	public List<MensagemDTO> importarDadosDoVRPL(Long idPropostaSiconv) {
		
		List<MensagemDTO> mensagens = new ArrayList<>();
	
		jdbi.useTransaction(transaction -> {
	
			Optional<PropostaIntegracao> propostaOptNoVRPL = this.vrplRepository.recuperarPropostaPorId(idPropostaSiconv);
	
			if (propostaOptNoVRPL.isPresent()) {
				
				PropostaIntegracao propostaVRPL = propostaOptNoVRPL.get();

				List<LicitacaoIntegracao> licitacoesAceitasNoVRPL = 
						this.vrplRepository.recuperarLicitacoesAceitasDaProposta(idPropostaSiconv); 
				
				Optional<PropostaBD> propostaOptEmContratos = transaction.attach(PropostaDAO.class)
						.recuperarPropostaPorIdSiconv(propostaVRPL.getIdSiconv());
				
				if (propostaOptEmContratos.isPresent()) {

					PropostaBD propostaContratos = propostaOptEmContratos.get();
					
					List<LicitacaoBD> licitacoesContratos = transaction.attach(LicitacaoDAO.class)
							.recuperarLicitacoesPorProposta(propostaContratos.getId());
		
					mensagens.addAll(this.atualizarLicitacoesDaBaseDeContratos(
							propostaContratos, licitacoesAceitasNoVRPL, licitacoesContratos, transaction));
					
					mensagens.addAll(this.apagarLicitacoesQueNaoExistemMaisNoVRPL(
							licitacoesAceitasNoVRPL, licitacoesContratos, transaction));
	
				} else {
					this.migrarProposta(propostaVRPL, licitacoesAceitasNoVRPL, transaction);
				}
			}
		});
	
		return mensagens;
	}
	
	private void migrarProposta(PropostaIntegracao propostaIntegracao, List<LicitacaoIntegracao> licitacoesIntegracao, Handle transaction) {
		
		Long idPropostaSiconv = propostaIntegracao.getIdSiconv();
		
		log.debug("MIGRACAO Proposta {} sendo migrada pela primeira vez para o modulo de Contratos",
				idPropostaSiconv);
		
		// Indo no Siconvao atrás da Data de Assinatura do Convenio
		if(propostaIntegracao.getDataAssinaturaConvenio() == null) {
			Optional<ConvenioIntegracao> convenioIntegracao = this.siconvaoRepository.recuperarDataAssinaturaConvenio(idPropostaSiconv);
			if(convenioIntegracao.isPresent()) {
				propostaIntegracao.setDataAssinaturaConvenio(convenioIntegracao.get().getDataAssinatura());
				propostaIntegracao.setNumeroConvenio(convenioIntegracao.get().getSequencial() != null ? convenioIntegracao.get().getSequencial().longValue() : null);
				propostaIntegracao.setAnoConvenio(convenioIntegracao.get().getAno() != null ? convenioIntegracao.get().getAno().longValue() : null);
			}
		}
		
		// Migrar Proposta
		PropostaBD proposta = PropostaBD.fromIntegracao(propostaIntegracao);
		proposta = transaction.attach(PropostaDAO.class).inserirProposta(proposta);

		// Migrar demais dados da proposta
		realizarMigracao(licitacoesIntegracao, proposta, transaction);
	}

	private List<MensagemDTO> atualizarLicitacoesDaBaseDeContratos(PropostaBD proposta, List<LicitacaoIntegracao> licitacoesVRPL, 
			List<LicitacaoBD> licitacoesContratos, Handle transaction ){
		
		List<MensagemDTO> mensagens = new ArrayList<>();
		
		for (LicitacaoIntegracao licitacaoVRPL : licitacoesVRPL) {
			
			LicitacaoBD licitacaoContratos = contemEmContratos(licitacaoVRPL, licitacoesContratos);
			if (licitacaoContratos == null) {

				log.debug(
						"MIGRACAO Licitacao {} nova de uma Proposta que já tinha sido migrada para o modulo de Contratos",
						licitacaoVRPL.getNumeroAno());

				List<LicitacaoIntegracao> licAMigrar = new ArrayList<>();
				licAMigrar.add(licitacaoVRPL);

				// migra a licitacao do VRPL
				realizarMigracao(licAMigrar, proposta, transaction);

			} else {
				Instant dataImportacaoDaLicitacaoNoContratos = transaction.attach(LicitacaoDAO.class)
						.recuperarDataImportacaoDaLicitacao(licitacaoContratos.getId());
				
				if (licitacaoVRPL.getDataAceite().isAfter(dataImportacaoDaLicitacaoNoContratos)) {

					List<ContratoBD> contratosDaLicitacao = transaction.attach(ContratoDAO.class)
							.recuperarContratosDaLicitacao(licitacaoContratos.getId());
					
					if (contratosDaLicitacao.isEmpty()) {

						// atualiza arvore de lic com as novas informacoes de licitacaoIntegracao
						log.debug(
								"MIGRACAO Atualizacao da Licitacao {} que foi atualizada no VRPL depois de ja ter sido migrada para Contratos",
								licitacaoContratos.getNumeroAno());

						// apaga a arvore da licitacao
						apagaArvoreDaLicitacao(licitacaoContratos, transaction);

						List<LicitacaoIntegracao> licAMigrar = new ArrayList<>();
						licAMigrar.add(licitacaoVRPL);

						// migra arvore de licitacaoIntegracao
						realizarMigracao(licAMigrar, proposta, transaction);

					} else {
						mensagens.add(new MensagemDTO(
								"ATENÇÃO: Há inconsistência de dados no módulo Instrumentos Contratuais pelo fato da Licitação "
								+licitacaoContratos.getNumeroAno()
								+ " ter sido aceita no módulo VRPL após inclusão do(s) instrumento(s) contratual(is) "
								+ this.recuperarNumerosDosContratos(contratosDaLicitacao)
								+ ", o que não é permitido. </br><p></p>"
								+"Para corrigir a inconsistência, recomenda-se fazer os passos EXATAMENTE conforme ordem abaixo:</br></br>"
								+ "1 - Excluir todos os instrumentos contratuais associados a esta licitação "
								+licitacaoContratos.getNumeroAno()+";</br></br>"
								+"2 - Após passo 1, sair da Plataforma efetuando um log out do sistema;</br></br>"
								+"3 - Entrar novamente na Plataforma com login e senha;</br></br>"
								+"4 - Acessar o módulo Instrumentos Contratuais, ao acessar o módulo, a mensagem de inconsistência desaparecerá e os dados serão atualizados corretamente.",
								Severity.WARN.toString()));
						
						log.error(
								"MIGRACAO COM DADOS INCONSISTENTES Atualizacao da Licitacao {} que foi atualizada no VRPL depois de ja ter Contratos vinculados",
								licitacaoContratos.getNumeroAno());
					}
				}
			}
		}
		
		return mensagens;
	}
	
	private List<MensagemDTO> apagarLicitacoesQueNaoExistemMaisNoVRPL(List<LicitacaoIntegracao> licitacoesVRPL, 
			List<LicitacaoBD> licitacoesContratos, Handle transaction ){
		List<MensagemDTO> mensagens = new ArrayList<>();
		
		for (LicitacaoBD licitacaoContratos : licitacoesContratos) {
			LicitacaoIntegracao licitacaoVRPL = contemEmVrpl(licitacaoContratos, licitacoesVRPL);
			if (licitacaoVRPL == null) {

				List<ContratoBD> contratosDaLicitacao = transaction.attach(ContratoDAO.class)
						.recuperarContratosDaLicitacao(licitacaoContratos.getId());

				if (contratosDaLicitacao.isEmpty()) {
					// se licitacao nao tem contrato vinculado, apaga arvore de licitacao
					log.debug(
							"MIGRACAO Apagando a Licitacao {} que foi atualizada no VRPL depois de ja ter sido migrada para Contratos",
							licitacaoContratos.getNumeroAno());

					// apaga a arvore da licitacao
					apagaArvoreDaLicitacao(licitacaoContratos, transaction);

				} else {
					log.error(
							"MIGRACAO COM DADOS INCONSISTENTES Atualizacao da Licitacao {} que foi excluida no VRPL depois de ja ter Contratos vinculados",
							licitacaoContratos.getNumeroAno());
					
					mensagens.add(new MensagemDTO("ATENÇÃO: Há inconsistência de dados no módulo Instrumentos Contratuais pelo fato da licitação "
							+licitacaoContratos.getNumeroAno()
							+ " não constar mais como aceita no módulo VRPL porém continua vinculada ao(s) instrumento(s) contratual(is) "
							+ this.recuperarNumerosDosContratos(contratosDaLicitacao)
							+ " cadastrado(s), o que não é permitido. Para mais informações de como proceder, "
							+ "favor entre em contato com a Central de Atendimento da Plataforma mais Brasil."
							+ "", Severity.WARN.toString()));
				}
			}
		}
		
		return mensagens;
	}
	
	private String recuperarNumerosDosContratos(List<ContratoBD> contratosDaLicitacao) {
		
		StringBuilder bld = new StringBuilder();
		
		for (ContratoBD contrato : contratosDaLicitacao) {
			bld.append(contrato.getNumero() + ", ");
		}
		
		String retorno = bld.toString();
		
		return retorno.substring(0, (retorno.length()-2));
	}

	private void apagaArvoreDaLicitacao(LicitacaoBD licitacao, Handle transaction) {
		List<LoteBD> lotes = transaction.attach(LoteDAO.class).recuperarLotesDaLicitacao(licitacao.getId());
		List<SubmetaBD> submetas = new LinkedList<>();
		List<MetaBD> metas = new LinkedList<>();
		for (LoteBD lot : lotes) {
			submetas.addAll(transaction.attach(SubmetaDAO.class).recuperarSubmetasDoLote(lot.getId()));
		}

		for (SubmetaBD sub : submetas) {
			
			if (transaction.attach(MetaDAO.class).seMetaNaoEstaAssociadaAOutraLicitacao(sub.getMetaId(), licitacao.getId())) {
				metas.add(transaction.attach(MetaDAO.class).recuperarMetaPorId(sub.getMetaId()).get());	
			}
			// Apagando POs
			PoBD po = transaction.attach(PoDAO.class).recuperarPoPorSubmeta(sub.getId()).get();
			transaction.attach(PoDAO.class).excluirPo(po.getId());

			// Apagando submetas
			transaction.attach(SubmetaDAO.class).excluirSubmeta(sub.getId());
		}

		// Apagando metas
		for (MetaBD met : metas) {
			transaction.attach(MetaDAO.class).excluirMeta(met.getId());
		}

		// Apagando lotes
		for (LoteBD lot : lotes) {
			transaction.attach(LoteDAO.class).excluirLote(lot.getId());
		}

		// Apagando a licitacao corrente
		transaction.attach(LicitacaoDAO.class).excluirLicitacao(licitacao.getId());
	}


	private void realizarMigracao(List<LicitacaoIntegracao> licitacoesIntegracao, PropostaBD proposta, Handle transaction) {
		// Migrar Licitacoes
		migrarLicitacoes(licitacoesIntegracao, proposta, transaction);

		// Migrar Fornecedores
		migrarFornecedores(licitacoesIntegracao, transaction);

		// Migrar Lotes, Metas e Submetas
		migrarLotes(licitacoesIntegracao, proposta, transaction);
	}
	
	
	private void migrarLotes(List<LicitacaoIntegracao> licitacoesIntegracao, PropostaBD proposta, Handle transaction) {

		for (LicitacaoIntegracao licitacaoIntegracao : licitacoesIntegracao) {
			List<LoteIntegracao> lotesIntegracao = this.vrplRepository
					.recuperarLotesDaLicitacaoLote(licitacaoIntegracao.getId());
			for (LoteIntegracao loteIntegracao : lotesIntegracao) {
				LoteBD lote = LoteBD.from(loteIntegracao);

				Optional<LicitacaoBD> lic = transaction.attach(LicitacaoDAO.class)
						.recuperarLicitacaoPorFK(licitacaoIntegracao.getIdLicitacaoFk());
				lote.setLicitacaoId(lic.get().getId());

				FornecedorIntegracao fornecedorIntegracao = this.vrplRepository
						.recuperarFornecedorPorId(lote.getFornecedorVrplFk());
				if (fornecedorIntegracao != null) {
					List<FornecedorBD> forn = transaction.attach(FornecedorDAO.class)
							.recuperarFornecedorPorIdentificacao(fornecedorIntegracao.getIdentificacao());
					lote.setFornecedorId(forn.get(0).getId());
				}

				// inserir lote aqui pra pegar o id
				lote = transaction.attach(LoteDAO.class).inserirLote(lote);

				this.migrarMetasSubmetasDoLote(lote, loteIntegracao, proposta, transaction);			}
		}
	}
	
	private void migrarMetasSubmetasDoLote(LoteBD lote, LoteIntegracao loteIntegracao, PropostaBD proposta, Handle transaction) {
		
		List<SubmetaIntegracao> submetasIntegracao = this.vrplRepository.recuperarSubmetasDoLote(loteIntegracao.getId());

		for (SubmetaIntegracao submetaIntegracao : submetasIntegracao) {

			SubmetaBD submeta = SubmetaBD.from(submetaIntegracao);
			submeta.setLoteId(lote.getId());
			submeta.setPropostaId(proposta.getId());

			// Migrar Metas
			MetaIntegracao metaIntegracao = this.vrplRepository.recuperarMetaDaSubmeta(submetaIntegracao.getId());
			Optional<MetaBD> metaOpt = transaction.attach(MetaDAO.class).recuperarMetaPorIdVrpl(metaIntegracao.getId());
			if (!metaOpt.isPresent()) {
				MetaBD meta = MetaBD.from(metaIntegracao);
				meta = transaction.attach(MetaDAO.class).inserirMeta(meta);
				submeta.setMetaId(meta.getId());
			} else {
				submeta.setMetaId(metaOpt.get().getId());
			}

			// Inserir Submeta
			submeta = transaction.attach(SubmetaDAO.class).inserirSubmeta(submeta);

			// Migrar POs
			PoIntegracao poIntegracao = this.vrplRepository.recuperarPoPorSubmeta(submetaIntegracao.getId());
			if (poIntegracao != null) {
				PoBD po = PoBD.from(poIntegracao);
				po.setSubmetaId(submeta.getId());

				transaction.attach(PoDAO.class).inserirPo(po);
			}
		}
	}

	private void migrarFornecedores(List<LicitacaoIntegracao> licitacoesIntegracao, Handle transaction) {
		List<FornecedorBD> fornecedores = new ArrayList<>();
		for (LicitacaoIntegracao licitacaoIntegracao : licitacoesIntegracao) {
			List<FornecedorIntegracao> fornecedoresIntegracao = this.vrplRepository
					.recuperarFornecedoresDaLicitacao(licitacaoIntegracao.getId());
			for (FornecedorIntegracao fornecedorIntegracao : fornecedoresIntegracao) {
				FornecedorBD fornecedor = FornecedorBD.from(fornecedorIntegracao);
				fornecedores.add(fornecedor);
			}
		}
		transaction.attach(FornecedorDAO.class).inserirFornecedorBatch(fornecedores);
	}

	private void migrarLicitacoes(List<LicitacaoIntegracao> licitacoesIntegracao, PropostaBD proposta, Handle transaction) {
		List<LicitacaoBD> licitacoes = new ArrayList<>();
		for (LicitacaoIntegracao licitacaoIntegracao : licitacoesIntegracao) {
			LicitacaoBD lic = LicitacaoBD.from(licitacaoIntegracao);
			lic.setPropostaId(proposta.getId());

			Optional<String> numeroProcesso = this.vrplRepository
					.recuperarNumeroProcesso(licitacaoIntegracao.getIdLicitacaoFk());
			if (numeroProcesso.isPresent()) {
				lic.setNumeroProcesso(numeroProcesso.get());
			}
			
			Optional<String> situacaoAceiteProcessoExecucao = this.vrplRepository
					.recuperarSituacaoAceiteProcessoExecucao(licitacaoIntegracao.getIdLicitacaoFk());
			if (situacaoAceiteProcessoExecucao.isPresent()) {
				lic.setSituacaoAceiteProcessoExecucao(situacaoAceiteProcessoExecucao.get());
			}

			licitacoes.add(lic);
		}
		transaction.attach(LicitacaoDAO.class).inserirLicitacaoBatch(licitacoes);
	}

	private LicitacaoBD contemEmContratos(LicitacaoIntegracao lic, List<LicitacaoBD> licitacoesContratos) {
		for (LicitacaoBD licitacao : licitacoesContratos) {
			if (licitacao.getIdLicitacaoFk().equals(lic.getIdLicitacaoFk())) {
				return licitacao;
			}
		}
		return null;
	}

	private LicitacaoIntegracao contemEmVrpl(LicitacaoBD lic, List<LicitacaoIntegracao> licitacoesVrpl) {
		for (LicitacaoIntegracao licitacao : licitacoesVrpl) {
			if (licitacao.getIdLicitacaoFk().equals(lic.getIdLicitacaoFk())) {
				return licitacao;
			}
		}
		return null;
	}

}
