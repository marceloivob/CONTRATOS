package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.LocalDate;
import java.util.List;

import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;
import lombok.Data;

@Data
public class PropostaDTO {

	private Long pcMinContrapartida;

	private Long idSiconv;

	private String nivelContrato;

	private String nomeMandataria;

	private String apelidoEmpreendimento;

	private String nomeProponente;

	private String uf;

	private Long anoProposta;

	private Long valorContrapartida;

	private String identificacaoProponente;

	private Long id;

	private Long numeroProposta;

	private Long anoConvenio;

	private Long numeroConvenio;

	private String nomePrograma;

	private String codigoPrograma;

	private String categoria;

	private String nomeObjeto;

	private Long valorGlobal;

	private Long valorRepasse;

	private LocalDate dataAssinaturaConvenio;

	private Boolean spaHomologado;

	private Long versao;

	private Long modalidade;

	private List<FornecedorDTO> fornecedores;

	private AioBD aio;
	
	private Boolean termoCompromissoTemMandatar;
	
	private List<MensagemDTO> mensagens;

	public PropostaBD converterParaBD() {
		PropostaBD bd = new PropostaBD();
		bd.setAnoConvenio(this.anoConvenio);
		bd.setAnoProposta(this.anoProposta);
		bd.setApelidoEmpreendimento(this.apelidoEmpreendimento);
		bd.setCategoria(this.categoria);
		bd.setCodigoPrograma(this.codigoPrograma);
		bd.setDataAssinaturaConvenio(this.dataAssinaturaConvenio);
		bd.setId(this.id);
		bd.setIdSiconv(this.idSiconv);
		bd.setIdentificacaoProponente(this.identificacaoProponente);
		bd.setModalidade(this.modalidade);
		bd.setNivelContrato(this.nivelContrato);
		bd.setNomeMandataria(this.nomeMandataria);
		bd.setNomeObjeto(this.nomeObjeto);
		bd.setNomePrograma(this.nomePrograma);
		bd.setNomeProponente(this.nomeProponente);
		bd.setNumeroConvenio(this.numeroConvenio);
		bd.setNumeroProposta(this.numeroProposta);
		bd.setPcMinContrapartida(this.pcMinContrapartida);
		bd.setSpaHomologado(this.spaHomologado);
		bd.setUf(this.uf);
		bd.setValorContrapartida(this.valorContrapartida);
		bd.setValorGlobal(this.valorGlobal);
		bd.setValorRepasse(this.valorRepasse);
		bd.setVersao(this.versao);
		bd.setTermoCompromissoTemMandatar(this.termoCompromissoTemMandatar);

		return bd;
	}

}
