package br.gov.serpro.contratos.grpc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import br.gov.serpro.contratos.grpc.Contrato;
import br.gov.serpro.contratos.grpc.ContratoDetalhado;
import br.gov.serpro.contratos.grpc.ContratoResponse;
import br.gov.serpro.contratos.grpc.ContratosGrpc.ContratosImplBase;
import br.gov.serpro.contratos.grpc.FornecedorContratos;
import br.gov.serpro.contratos.grpc.FornecedorContratosResponse;
import br.gov.serpro.contratos.grpc.FornecedorContratosResponse.Builder;
import br.gov.serpro.contratos.grpc.FornecedorRequest;
import br.gov.serpro.contratos.grpc.IdContratoRequest;
import br.gov.serpro.contratos.grpc.IdPropostaRequest;
import br.gov.serpro.contratos.grpc.LicitacaoRequest;
import br.gov.serpro.contratos.grpc.LicitacaoResponse;
import br.gov.serpro.contratos.grpc.ListaContratosResponse;
import br.gov.serpro.contratos.grpc.ListaFornecedoresRequest;
import br.gov.serpro.contratos.grpc.ListaSubmetasResponse;
import br.gov.serpro.contratos.grpc.Lote;
import br.gov.serpro.contratos.grpc.NumeroContratoLicitacao;
import br.gov.serpro.contratos.grpc.Proposta;
import br.gov.serpro.contratos.grpc.PropostaResponse;
import br.gov.serpro.contratos.grpc.Submeta;
import br.gov.serpro.contratos.grpc.application.JDBIProducer;
import br.gov.serpro.contratos.grpc.database.ContratoBD;
import br.gov.serpro.contratos.grpc.database.ContratosDAO;
import br.gov.serpro.contratos.grpc.database.LoteBD;
import br.gov.serpro.contratos.grpc.database.PropostaBD;
import br.gov.serpro.contratos.grpc.database.SituacaoSubmetaEnum;
import br.gov.serpro.contratos.grpc.database.SubmetaBD;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class ContratosService extends ContratosImplBase {

	private Jdbi jdbi;

	public ContratosService() {
		this.jdbi = JDBIProducer.getJdbi();
	}
	 
	private List<Lote> converteLotes(List<LoteBD> listaLotesBD) {
 
		List<Lote> listaLotes = new ArrayList<Lote>();

		for (LoteBD loteBD : listaLotesBD) {

			List<Submeta> listaSubmetas = new ArrayList<Submeta>();

			if ((!loteBD.getSubmetas().isEmpty()) || (loteBD.getSubmetas() != null)) {

				for (SubmetaBD submetaBD : loteBD.getSubmetas()) {
					Submeta submeta = Submeta.newBuilder().setId(submetaBD.getId()).setNumero(submetaBD.getNumero())
							.setDescricao(submetaBD.getDescricao()).setValor(submetaBD.getValorTotal())
							.setSituacao(submetaBD.getSituacao()).build();
					listaSubmetas.add(submeta);
				}
			}

			Lote lote = Lote.newBuilder().setId(loteBD.getId()).setNumero(loteBD.getNumero())
					.addAllSubmetas(listaSubmetas).build();
			listaLotes.add(lote);
		}

		return listaLotes;
	}

	private Proposta converteProposta(PropostaBD propostaBD) {

		List<Lote> listaLoteSemContrato = new ArrayList<Lote>();
		List<Contrato> listaContratos = new ArrayList<Contrato>();

		if ((!propostaBD.getContratos().isEmpty()) || (propostaBD.getContratos() != null)) {

			for (ContratoBD contratoBD : propostaBD.getContratos()) {

				List<Lote> listaLotes = new ArrayList<Lote>();

				if ((!contratoBD.getLotes().isEmpty()) || (contratoBD.getLotes() != null)) {

					for (LoteBD loteBD : contratoBD.getLotes()) {

						List<Submeta> listaSubmetas = new ArrayList<Submeta>();

						if ((!loteBD.getSubmetas().isEmpty()) || (loteBD.getSubmetas() != null)) {

							for (SubmetaBD submetaBD : loteBD.getSubmetas()) {
								Submeta submeta = Submeta.newBuilder().setId(submetaBD.getId())
										.setNumero(submetaBD.getNumero()).setDescricao(submetaBD.getDescricao())
										.setValor(submetaBD.getValorTotal()).setSituacao(SituacaoSubmetaEnum.fromSigla(submetaBD.getSituacao()).getDescricao())
										.setRegimeExecucao(submetaBD.getRegimeExecucao().getDescricao())
										.build();

								if (submetaBD.getIdVRPL() != null) {
									submeta = Submeta.newBuilder(submeta).setIdVRPL(submetaBD.getIdVRPL()).build();
								}

								listaSubmetas.add(submeta);
							}
						}

						Lote lote = Lote.newBuilder().setId(loteBD.getId()).setNumero(loteBD.getNumero())
								.addAllSubmetas(listaSubmetas).build();
						listaLotes.add(lote);
					}
				}

				Contrato contrato = Contrato.newBuilder()
						.setId(contratoBD.getId())
						.setCnpj(contratoBD.getCnpj())
						.setNumero(contratoBD.getNumero())
						.setAptIniciar(contratoBD.isAptIniciar())
						.setIsAcompEventos(contratoBD.isAcompEventos())
						.addAllLotes(listaLotes)
						.build();

				listaContratos.add(contrato);

			}
		}

		// Convertendo os lotes sem contrato
//		if ((!listaLotesBD.isEmpty()) || (listaLotesBD != null)) {
//			listaLoteSemContrato = converteLotes(listaLotesBD);
//		}

		return Proposta.newBuilder().setId(propostaBD.getId()).setNumero(propostaBD.getNumero())
				.setAno(propostaBD.getAno()).setObjeto(propostaBD.getObjeto()).setUf(propostaBD.getUf())
				.setIdSiconv(propostaBD.getIdSiconv()).setModalidade(propostaBD.getModalidade()).setNomeProponente(propostaBD.getNomeProponente())
				.addAllContratos(listaContratos).build();
	}

	@Override
	public void consultarPropostaPorId(IdPropostaRequest idProposta,
			StreamObserver<PropostaResponse> responseObserver) {
		if (idProposta == null) {
			throw new IllegalArgumentException("Parâmetro 'id da Proposta' inválida.");
		}

		try (Handle handle = jdbi.open()) {

			PropostaBD propostaBD = handle.attach(ContratosDAO.class)
					.consultarPropostaPorId(idProposta.getIdProposta());

			if ((propostaBD == null)) {
				responseObserver.onError(
						new StatusRuntimeException(Status.NOT_FOUND.withDescription("Proposta não encontrada.")));
				return;
			}
			
//			List<LoteBD> listaLoteSemContratosBD = new ArrayList<LoteBD>();

			// Recuperando os lotes não associados
//			listaLoteSemContratosBD = handle.attach(ContratosDAO.class)
//					.consultarLotesSemContratoPorIdProposta(idProposta.getIdProposta());

			Proposta proposta = null;
			
			proposta = converteProposta(propostaBD);
			
			br.gov.serpro.contratos.grpc.PropostaResponse.Builder builderResponse = PropostaResponse.newBuilder();

			builderResponse.setProposta(proposta);

			responseObserver.onNext(builderResponse.build());
		}

		responseObserver.onCompleted();
	}

	@Override
	public void consultarContratoPorId(IdContratoRequest idContrato,
			StreamObserver<ContratoResponse> responseObserver) {
		if (idContrato == null) {
			throw new IllegalArgumentException("Parâmetro 'id do Contrato' inválido.");
		}

		try (Handle handle = jdbi.open()) {

			ContratoDetalhado contrato = handle.attach(ContratosDAO.class)
					.consultarContratoPorId(idContrato.getIdContrato());
			
			if ((contrato == null)) {
				responseObserver.onError(
						new StatusRuntimeException(Status.NOT_FOUND.withDescription("Contrato não encontrado.")));
				return;
			}

			br.gov.serpro.contratos.grpc.ContratoResponse.Builder builderResponse = ContratoResponse.newBuilder();

			builderResponse.setContrato(contrato);

			responseObserver.onNext(builderResponse.build());
		}

		responseObserver.onCompleted();

	}

	@Override
	public void consultarSubmetasPorIdContrato(IdContratoRequest idContrato,
			StreamObserver<ListaSubmetasResponse> responseObserver) {
		if (idContrato == null) {
			throw new IllegalArgumentException("Parâmetro 'id do Contrato' inválido.");
		}

		try (Handle handle = jdbi.open()) {

			List<Submeta> listaSubmetas = handle.attach(ContratosDAO.class)
					.consultarSubmetasPorIdContrato(idContrato.getIdContrato());

			if ((listaSubmetas == null) || (listaSubmetas.isEmpty())) {
								
				responseObserver.onError(
						new StatusRuntimeException(Status.NOT_FOUND.withDescription("Não existem submetas para o contrato informado!")));
				return;
				
			} else {
				
				br.gov.serpro.contratos.grpc.ListaSubmetasResponse.Builder builderResponse = ListaSubmetasResponse
						.newBuilder();

				builderResponse.addAllSubmeta(listaSubmetas);

				responseObserver.onNext(builderResponse.build());
			}
		}

		responseObserver.onCompleted();

	}

	@Override
	public void consultarContratosPorFornecedor(FornecedorRequest fornecedor,
			StreamObserver<ListaContratosResponse> responseObserver) {

		if ((fornecedor == null) || (fornecedor.getCnpj() == "")) {
			throw new IllegalArgumentException("Parâmetro 'CNPJ do Fornecedor' inválido.");
		}

		try (Handle handle = jdbi.open()) {

			List<ContratoDetalhado> listaContratos = handle.attach(ContratosDAO.class)
					.consultarContratosPorFornecedor(fornecedor.getCnpj());

			if((listaContratos == null) || (listaContratos.isEmpty())) {

				responseObserver.onError(
						new StatusRuntimeException(Status.NOT_FOUND.withDescription("Não existem contratos para o fornecedor informado!")));
				return;
				
			} else {
				
				br.gov.serpro.contratos.grpc.ListaContratosResponse.Builder builderResponse = ListaContratosResponse
						.newBuilder();

				builderResponse.addAllContratos(listaContratos);

				responseObserver.onNext(builderResponse.build());
				
			}

		}

		responseObserver.onCompleted();

	}

	@Override
	public void consultarQTDContratos(ListaFornecedoresRequest listaFornecedores,
			StreamObserver<FornecedorContratosResponse> responseObserver) {

		if ((listaFornecedores == null) || (listaFornecedores.getCnpjList().size() <= 0)) {
			throw new IllegalArgumentException("Parâmetro 'CNPJ do Fornecedor' inválido.");
		}

		try (Handle handle = jdbi.open()) {

			List<FornecedorContratos> listaFornecedoresContratos = handle.attach(ContratosDAO.class)
					.consultarQTDContratos(listaFornecedores.getCnpjList());

			Builder builderResponse = FornecedorContratosResponse.newBuilder();

			for (FornecedorContratos fornecedor : listaFornecedoresContratos) {
				builderResponse.addMap(fornecedor);
			}

			responseObserver.onNext(builderResponse.build());
		}

		responseObserver.onCompleted();
	}
 
	@Override
	public void listaContratosLicitacao(LicitacaoRequest licitacaoRequest,
			StreamObserver<LicitacaoResponse> responseObserver) {

		if ((licitacaoRequest == null) || (Objects.isNull(licitacaoRequest.getIdSiconv()))) {
			throw new IllegalArgumentException("Parâmetro 'idSiconv' informado é inválido.");
		}else {
			if((licitacaoRequest == null)||(Objects.isNull(licitacaoRequest.getNumeroAnoLicitacao()))) {
				throw new IllegalArgumentException("Parâmetro 'numeroAnoLicitacao' informado é inválido.");
			}
		}

		try (Handle handle = jdbi.open()) {

			List<NumeroContratoLicitacao> listaNumerosContratos = handle.attach(ContratosDAO.class).listaContratosLicitacao(licitacaoRequest.getIdSiconv(), licitacaoRequest.getNumeroAnoLicitacao());
						
			if((listaNumerosContratos == null) || (listaNumerosContratos.isEmpty())) {

				responseObserver.onError(
						new StatusRuntimeException(Status.NOT_FOUND.withDescription("Não existem contratos para a licitação informada!")));
				return;
				
			} else {
				
				br.gov.serpro.contratos.grpc.LicitacaoResponse.Builder builderResponse = LicitacaoResponse.newBuilder();

				builderResponse.addAllNumeroContratoLicitacao(listaNumerosContratos);

				responseObserver.onNext(builderResponse.build());
				
			}

		}

		responseObserver.onCompleted();

	}
	
	
}
