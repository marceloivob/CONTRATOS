package br.gov.serpro.siconv.contratos.grpc.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.gov.serpro.contratos.grpc.ContratoDetalhado;
import lombok.Data;

@Data
public class ContratoDetalhadoDTO {
	private Long id;
	private String numero;
	private String cnpjFornecedor;
	private Boolean isTrabSocial;
	private Boolean isAcompEventos;
	private LocalDate dtInicioVigencia;
	private LocalDate dtFimVigencia;
	private LocalDate dtAssinatura;
	private String objeto;
	private BigDecimal valorTotal;
	private BigDecimal valorTipoInstrumento;
	private Long idSiconv;
	private String localidade;
	private Integer modalidade;
	private Boolean possuiInstituicaoMandataria;
	private Long numeroTipoInstrumento;
	private Long anoTipoInstrumento;
	private String objetoTipoInstrumento;
	private LocalDate dtAssinaturaTipoInstrumento;
	private String nomeProponente;

	private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public ContratoDetalhadoDTO(ContratoDetalhado contratoGRPC) {

		this.id = contratoGRPC.getId();
		this.numero = contratoGRPC.getNumero();
		this.cnpjFornecedor = contratoGRPC.getCnpjFornecedor();
		this.isTrabSocial = contratoGRPC.getIsTrabSocial();
		this.isAcompEventos = contratoGRPC.getIsAcompEventos();
		this.dtInicioVigencia = LocalDate.parse(contratoGRPC.getDtInicioVigencia(), formato);
		this.dtFimVigencia = LocalDate.parse(contratoGRPC.getDtFimVigencia(), formato);
		this.dtAssinatura = LocalDate.parse(contratoGRPC.getDtAssinatura(), formato);
		this.objeto = contratoGRPC.getObjeto();
		this.valorTotal = new BigDecimal(contratoGRPC.getValorTotal());
		this.valorTipoInstrumento = new BigDecimal(contratoGRPC.getValotTipoInstrumento());
		this.idSiconv = contratoGRPC.getIdSiconv();
		this.localidade = contratoGRPC.getLocalidade();
		this.modalidade = contratoGRPC.getModalidade();
		this.possuiInstituicaoMandataria = contratoGRPC.getPossuiInstituicaoMandataria();
		this.numeroTipoInstrumento = contratoGRPC.getNumeroTipoInstrumento();
		this.anoTipoInstrumento = contratoGRPC.getAnoTipoInstrumento();
		this.objetoTipoInstrumento = contratoGRPC.getObjetoTipoInstrumento();
		this.dtAssinaturaTipoInstrumento = LocalDate.parse(contratoGRPC.getDtAssinaturaTipoInstrumento(), formato);
		this.nomeProponente = contratoGRPC.getNomeProponente();

	}

}
