package br.gov.economia.maisbrasil.contratos.bc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.jdbi.v3.core.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import br.gov.economia.maisbrasil.contratos.bc.exception.AnexoNotFoundException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ArquivoAnexoSemExtensaoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ArquivoDuplicadoMesmaDescricaoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.ArquivoDuplicadoMesmoNomeException;
import br.gov.economia.maisbrasil.contratos.bc.exception.FormatoArquivoInvalidoException;
import br.gov.economia.maisbrasil.contratos.bc.exception.TamanhoMaximoArquivoInvalidoException;
import br.gov.economia.maisbrasil.contratos.core.ceph.AmazonClient;
import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.AnexoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoAnexoDTO;
import br.gov.economia.maisbrasil.contratos.repository.AnexoRepository;
import br.gov.economia.maisbrasil.contratos.repository.jdbi.AnexoDAO;
import br.gov.economia.maisbrasil.contratos.security.SecurityUtils;
import br.gov.economia.maisbrasil.contratos.security.SiconvUser;

@Controller
public class AnexoBC {

	private final Logger log = LoggerFactory.getLogger(AnexoBC.class);

	private AmazonClient amazonClient;

	private final AnexoRepository anexoRepository;

	private static final Validator beanValidator = Validation.buildDefaultValidatorFactory().getValidator();

	@Autowired
	public AnexoBC(AnexoRepository anexoRepository, AmazonClient cephClient) {
		this.anexoRepository = anexoRepository;
		this.amazonClient = cephClient;
	}

	public List<AnexoBD> recuperarTodosAnexos() {
		return anexoRepository.recuperarTodosAnexos();
	}

	public AnexoBD inserirAnexo(AnexoBD anexo) {
		Objects.requireNonNull(anexo);
		return anexoRepository.inserirAnexo(anexo);
	}

	public AnexoBD alterarAnexo(AnexoBD anexo) {
		Objects.requireNonNull(anexo);

		return anexoRepository.alterarAnexo(anexo);
	}

	public Optional<AnexoBD> recuperarAnexoPorId(Long id) {
		Objects.requireNonNull(id);

		return anexoRepository.recuperarAnexoPorId(id);
	}

	public List<AnexoDTO> recuperarAnexosPorIdContrato(Long id) {
		return anexoRepository.recuperarAnexoPorIdContrato(id).stream().map(e -> e.converterParaDTO())
				.collect(Collectors.toList());
	}

	public List<AnexoDTO> getAnexosContratoPorTipo(Long idContrato, String tipoAnexo) {
		return this.anexoRepository.getAnexosContratoPorTipo(idContrato, tipoAnexo).stream()
				.map(e -> e.converterParaDTO()).collect(Collectors.toList());
	}

	public void anexarArquivo(@NotNull Long idContrato, @NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, @NotNull InputStream arquivo, Long fileLength) {

		AnexoBD anexo = this.criarAnexoContrato(idContrato, nomeArquivo, descricao, tipoAnexo, arquivo, fileLength);
		
		anexoRepository.inserirAnexo(anexo);
	}
	
	public void anexarArquivo(@NotNull Long idContrato, @NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, @NotNull InputStream arquivo, Long fileLength, Handle transaction) {

		AnexoBD anexo = this.criarAnexoContrato(idContrato, nomeArquivo, descricao, tipoAnexo, arquivo, fileLength);
		
		anexoRepository.inserirAnexo(anexo, transaction);
	}
	
	private AnexoBD criarAnexoContrato(@NotNull Long idContrato, @NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, @NotNull InputStream arquivo, Long fileLength) {

		AnexoBD anexo = this.criarAnexoComInfosBasicas(nomeArquivo, descricao, tipoAnexo, fileLength);
		
		anexo.setContratoId(idContrato);

		this.verificarDuplicidade(anexo, anexoRepository.recuperarAnexoPorIdContrato(idContrato));
		this.validarFormatoAnexo(anexo);

		String chaveCeph = amazonClient.uploadFile(arquivo, nomeArquivo, fileLength);
		anexo.setCaminho(chaveCeph);

		beanValidator.validate(anexo);

		return anexo;
	}
	
	public void anexarArquivoTermoAditivo(@NotNull Long idTermoAditivo, @NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, @NotNull InputStream arquivo, Long fileLength) {

		AnexoBD anexo = this.criarAnexoTermoAditivo(idTermoAditivo, nomeArquivo, descricao, tipoAnexo, arquivo, fileLength);
		
		anexoRepository.inserirAnexo(anexo);
	}
	
	public void anexarArquivoTermoAditivo(@NotNull Long idTermoAditivo, @NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, @NotNull InputStream arquivo, Long fileLength, Handle transaction) {

		AnexoBD anexo = this.criarAnexoTermoAditivo(idTermoAditivo, nomeArquivo, descricao, tipoAnexo, arquivo, fileLength);
		
		anexoRepository.inserirAnexo(anexo, transaction);
	}
	
	private AnexoBD criarAnexoTermoAditivo(@NotNull Long idTermoAditivo, @NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, @NotNull InputStream arquivo, Long fileLength) {

		AnexoBD anexo = this.criarAnexoComInfosBasicas(nomeArquivo, descricao, tipoAnexo, fileLength);
		
		anexo.setTermoAditivoId(idTermoAditivo);
		
		this.verificarDuplicidade(anexo, anexoRepository.recuperarAnexoPorIdTermoAditivo(idTermoAditivo));
		this.validarFormatoAnexo(anexo);

		String chaveCeph = amazonClient.uploadFile(arquivo, nomeArquivo, fileLength);
		anexo.setCaminho(chaveCeph);

		beanValidator.validate(anexo);

		return anexo;
	}
	
	private AnexoBD criarAnexoComInfosBasicas(@NotNull String nomeArquivo, @NotNull String descricao,
			@NotNull String tipoAnexo, Long fileLength) {
		
		if (fileLength == null || fileLength > this.amazonClient.getMaxFileSize()) {
			throw new TamanhoMaximoArquivoInvalidoException();
		}

		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser user = (SiconvUser) usuarioAutenticado.getPrincipal();

		AnexoBD anexo = new AnexoBD();

		anexo.setNmArquivo(nomeArquivo);
		anexo.setTxDescricao(descricao);
		anexo.setInTipoAnexo(tipoAnexo);
		anexo.setIdCpfEnviadoPor(user.getCpf());
		anexo.setInPerfilUsuario(user.getProfile().toString());
		anexo.setNomeEnviadoPor(user.getUsername());
		anexo.setBucket(this.amazonClient.getBucketName());
		anexo.setDtUpload(LocalDate.now());
		
		return anexo;
	}
	

	public void anexarArquivosDeContrato(Long idContrato, List<ContratoAnexoDTO> anexos, Handle transaction) {
		for (ContratoAnexoDTO anexo : anexos) {
			byte[] fileDecoded = Base64.getDecoder().decode(anexo.getArquivo());
			InputStream arquivo = new ByteArrayInputStream(fileDecoded);

			this.anexarArquivo(idContrato, anexo.getNomeArquivo(), anexo.getDescricao(),
    			anexo.getTipoAnexo(), arquivo, anexo.getTamanhoArquivo(), transaction);
		}

	}
	
	public void anexarArquivosDeTermoAditivo(Long idTermoAditivo, List<ContratoAnexoDTO> anexos, Handle transaction) {

		for (ContratoAnexoDTO anexo : anexos) {
			byte[] fileDecoded = Base64.getDecoder().decode(anexo.getArquivo());
			InputStream arquivo = new ByteArrayInputStream(fileDecoded);

			this.anexarArquivoTermoAditivo(idTermoAditivo, anexo.getNomeArquivo(), anexo.getDescricao(),
	    			anexo.getTipoAnexo(), arquivo, anexo.getTamanhoArquivo(), transaction);
		}
	}

	public List<AnexoDTO> listarAnexos(Long idContrato) {
		List<AnexoBD> listaAnexoBD = anexoRepository.recuperarAnexoPorIdContrato(idContrato);

		List<AnexoDTO> listaAnexoDTO = toDTO(listaAnexoBD);

		return listaAnexoDTO;
	}

	public void apagarAnexo(Long identificadorDoAnexo) {
		AnexoBD anexo = this.anexoRepository.recuperarAnexoPorId(identificadorDoAnexo).get();
		this.anexoRepository.excluirAnexo(anexo.getId());
	}
	
	public void validarListaContratoAnexos(List<ContratoAnexoDTO> anexos) {
		
		for (int i = 0; i < anexos.size(); i++) {
			List<ContratoAnexoDTO> anexosSemAnexoAtual =  new ArrayList<>(anexos);
			ContratoAnexoDTO anexoAtual = anexosSemAnexoAtual.get(i);
			anexosSemAnexoAtual.remove(i);
			
			anexosSemAnexoAtual.stream().forEach(anexo -> this.verificarDuplicidade(anexo, anexoAtual));
		}
		
		anexos.stream().forEach(this::validarFormatoAnexo);
	}
	
	public void verificarDuplicidade(ContratoAnexoDTO anexo1, ContratoAnexoDTO anexo2) {
		if (anexo1.getNomeArquivo().equals(anexo2.getNomeArquivo())) {
			log.debug("..........ArquivoDuplicadoMesmoNomeException...........");
			throw new ArquivoDuplicadoMesmoNomeException();
		}

		if (anexo1.getDescricao().equals(anexo2.getDescricao())) {
			log.debug("..........jaExisteOutroArquivoComMesmaDescricao...........");
			throw new ArquivoDuplicadoMesmaDescricaoException();
		}
	}

	public void verificarDuplicidade(AnexoBD anexo, List<AnexoBD> list) {
		if (list == null) {
			return;
		}

		for (AnexoBD arquivo : list) {
			if (jaExisteOutroArquivoComMesmoNome(anexo, arquivo)) {
				log.debug("..........ArquivoDuplicadoMesmoNomeException...........");
				throw new ArquivoDuplicadoMesmoNomeException();
			}

			if (jaExisteOutroArquivoComMesmaDescricao(anexo, arquivo)) {
				log.debug("..........jaExisteOutroArquivoComMesmaDescricao...........");
				throw new ArquivoDuplicadoMesmaDescricaoException();
			}
		}
	}


	private boolean jaExisteOutroArquivoComMesmaDescricao(AnexoBD anexo, AnexoBD arquivo) {
		return arquivo.getTxDescricao().equals(anexo.getTxDescricao());
	}

	private boolean jaExisteOutroArquivoComMesmoNome(AnexoBD anexo, AnexoBD arquivo) {
		return arquivo.getNmArquivo().equals(anexo.getNmArquivo());
	}
	

	public void validarFormatoAnexo(ContratoAnexoDTO anexo) {
		this.validarFormatoAnexo(anexo.getNomeArquivo());
	}
	
	public void validarFormatoAnexo(AnexoBD anexo) {
		this.validarFormatoAnexo(anexo.getNmArquivo());
	}
	
	public void validarFormatoAnexo(String nomeArquivo) {

		List<String> extensoesPermitidas = Arrays.asList(".PDF", ".DOC", ".DOCX", ".XLS", ".XLSX", ".JPG", ".JPEG",
				".PNG", ".ODT", ".ODS", ".DWG");

		int posicaoUltimoSeparador = nomeArquivo.lastIndexOf('.');

		if (posicaoUltimoSeparador == -1) {
			throw new ArquivoAnexoSemExtensaoException();
		}

		String extensaoArquivo = nomeArquivo.substring(posicaoUltimoSeparador);

		if (!extensoesPermitidas.contains(extensaoArquivo.toUpperCase())) {
			throw new FormatoArquivoInvalidoException();
		}

	}

	public void atualizarAnexo(@NotNull Long identificadorDoAnexo, @NotNull String nomeArquivo,
			@NotNull String descricao, @NotNull String tipoAnexo, @NotNull Long versao, InputStream arquivo,
			Long fileLength) {

		Authentication usuarioAutenticado = SecurityUtils.getCurrentUser();
		SiconvUser user = (SiconvUser) usuarioAutenticado.getPrincipal();

		AnexoBD anexo = anexoRepository.recuperarAnexoPorId(identificadorDoAnexo).get();

		if (anexo == null) {
			throw new AnexoNotFoundException(identificadorDoAnexo, nomeArquivo, descricao, tipoAnexo, versao);
		}

		validarFormatoAnexo(anexo);

		List<AnexoBD> anexosBD = this.anexoRepository.recuperarOutrosAnexosDaLicitacao(anexo.getContratoId(),
				anexo.getId());

		anexo.setNmArquivo(nomeArquivo);
		anexo.setTxDescricao(descricao);
		anexo.setInTipoAnexo(tipoAnexo);
		anexo.setIdCpfEnviadoPor(user.getCpf());
		anexo.setInPerfilUsuario(user.getProfile().toString());
		anexo.setBucket(this.amazonClient.getBucketName());
		anexo.setVersao(versao);

		verificarDuplicidade(anexo, anexosBD);

		if (arquivo != null) {
			if (fileLength == null || fileLength > this.amazonClient.getMaxFileSize()) {
				throw new TamanhoMaximoArquivoInvalidoException();
			}

			String chaveCeph = amazonClient.uploadFile(arquivo, nomeArquivo, fileLength);

			anexo.setCaminho(chaveCeph);
		}

		beanValidator.validate(anexo);

		anexoRepository.alterarAnexo(anexo);

	}

	/**
	 *
	 * @param idContrato
	 * @param tipoAnexo
	 * @return
	 */
	public List<AnexoDTO> listarAnexosPorTipo(@NotNull Long idContrato, @NotNull String tipoAnexo) {
		List<AnexoBD> listaAnexoBD = anexoRepository.getAnexosContratoPorTipo(idContrato, tipoAnexo);

		List<AnexoDTO> listaAnexoDTO = toDTO(listaAnexoBD);

		return listaAnexoDTO;
	}

	public void excluirAnexosPorIdContrato(Long idContrato, Handle transaction) {
		transaction.attach(AnexoDAO.class).excluirAnexosPorIdContrato(idContrato);
	}

	/**
	 *
	 * @param idContrato
	 * @param tiposAnexos
	 * @return
	 */
	public List<AnexoDTO> getAnexosContratoPorConjuntoTipos(Long idContrato, List<String> tiposAnexos) {
		List<AnexoBD> listaAnexoBD = anexoRepository.getAnexosContratoPorConjuntoTipos(idContrato, tiposAnexos);
		List<AnexoDTO> listaAnexoDTO = toDTO(listaAnexoBD);

		return listaAnexoDTO;

	}

	/**
	 *
	 * @param idTermoAditivo
	 * @param tiposAnexos
	 * @return
	 */
	public List<AnexoDTO> getAnexosTermoAditivoPorConjuntoTipos(Long idTermoAditivo, List<String> tiposAnexos) {
		List<AnexoBD> listaAnexoBD = anexoRepository.getAnexosTermoAditivoPorConjuntoTipos(idTermoAditivo, tiposAnexos);
		List<AnexoDTO> listaAnexoDTO = toDTO(listaAnexoBD);

		return listaAnexoDTO;

	}

	private List<AnexoDTO> toDTO(List<AnexoBD> listaAnexoBD) {
		List<AnexoDTO> listaAnexoDTO = listaAnexoBD.stream().map(anexo -> anexo.converterParaDTO()).map(dto -> {

			GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(this.amazonClient.getBucketName(),
					dto.getCaminho());
			URL link = amazonClient.getS3client().generatePresignedUrl(request);

			dto.setLinkToDownload(link.toString());
			return dto;
		}).collect(Collectors.toList());
		return listaAnexoDTO;
	}

}
