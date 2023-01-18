package br.gov.economia.maisbrasil.contratos.domain.integracao;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.PropostaDTO;
import lombok.Data;

@Data
public class PropostaIntegracao {

	private Long pcMinContrapartida;

	@NotNull
	private Long idSiconv;

	private String nivelContrato;

	private String nomeMandataria;

	@NotNull
	private String apelidoEmpreendimento;

	private String nomeProponente;

	@NotNull
	private String uf;

	@NotNull
	private Long anoProposta;

	@NotNull
	private Long valorContrapartida;

	private String identificacaoProponente;

	private Long id;

	@NotNull
	private Long numeroProposta;

	private Long anoConvenio;

	private Long numeroConvenio;

	@NotNull
	private String nomePrograma;

	@NotNull
	private String codigoPrograma;

	@NotNull
	private String categoria;

	private String nomeObjeto;

	@NotNull
	private Long valorGlobal;

	@NotNull
	private Long valorRepasse;

	private LocalDate dataAssinaturaConvenio;

	@NotNull
	private Boolean spaHomologado;

	@NotNull
	private Long versao;

	@NotNull
	private Long modalidade;
	
	private Boolean termoCompromissoTemMandatar;

	public PropostaDTO converterParaDTO() {
		PropostaDTO propostaDTO = new PropostaDTO();
		propostaDTO.setAnoConvenio(this.anoConvenio);
		propostaDTO.setAnoProposta(this.anoProposta);
		propostaDTO.setApelidoEmpreendimento(this.apelidoEmpreendimento);
		propostaDTO.setCategoria(this.categoria);
		propostaDTO.setCodigoPrograma(this.codigoPrograma);
		propostaDTO.setDataAssinaturaConvenio(this.dataAssinaturaConvenio);
		propostaDTO.setId(this.id);
		propostaDTO.setIdentificacaoProponente(this.identificacaoProponente);
		propostaDTO.setIdSiconv(this.idSiconv);
		propostaDTO.setModalidade(this.modalidade);
		propostaDTO.setNivelContrato(this.nivelContrato);
		propostaDTO.setNomeMandataria(this.nomeMandataria);
		propostaDTO.setNomeObjeto(this.nomeObjeto);
		propostaDTO.setNomePrograma(this.nomePrograma);
		propostaDTO.setNomeProponente(this.nomeProponente);
		propostaDTO.setNumeroConvenio(this.numeroConvenio);
		propostaDTO.setNumeroProposta(this.numeroProposta);
		propostaDTO.setPcMinContrapartida(this.pcMinContrapartida);
		propostaDTO.setSpaHomologado(this.spaHomologado);
		propostaDTO.setUf(this.uf);
		propostaDTO.setValorContrapartida(this.valorContrapartida);
		propostaDTO.setValorGlobal(this.valorGlobal);
		propostaDTO.setValorRepasse(this.valorRepasse);
		propostaDTO.setVersao(this.versao);
		propostaDTO.setTermoCompromissoTemMandatar(this.termoCompromissoTemMandatar);

		return propostaDTO;
	}
}
