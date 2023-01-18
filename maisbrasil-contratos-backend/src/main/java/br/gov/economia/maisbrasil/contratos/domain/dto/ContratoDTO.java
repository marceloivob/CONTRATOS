package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.gov.economia.maisbrasil.contratos.domain.PermissaoAlteracaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.SituacaoContratoEnum;
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import lombok.Data;

@Data
public class ContratoDTO {
    
    public String numero;

    public String inSituacao;
    
    public String inSituacaoDescricao;

    public String inSituacaoExibicao;
    
    public String inSituacaoExibicaoDescricao;
    
    public String objeto;

    public BigDecimal valorTotal;
    
    public BigDecimal valorReferenteModalidade;

    public Long propostaId;
    
    public Long propostaIdSiconv;

    public Long id;
    
    public String dataAssinatura;
    
    public String dataPublicacao;

    public String inicioVigencia;
    
    public String fimVigencia;
    
    public String fimVigenciaOriginal;

    public Long versao;
    
    public PermissaoAlteracaoContratoEnum permissaoAlteracao;
    
    public List<LoteDTO> lotes = new ArrayList<>();
    
    public List<MensagemDTO> mensagens = new ArrayList<>();
    
    public List<ContratoAnexoDTO> contratoAnexos = new ArrayList<>();
    
    public ContratoBD converterParaBD() {

    	int endIndex = 10;
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ContratoBD contrato = new ContratoBD();
		contrato.setDataAssinatura(LocalDate.parse(this.dataAssinatura.subSequence(0, endIndex), formatter));
		contrato.setDataPublicacao(LocalDate.parse(this.dataPublicacao.subSequence(0, endIndex), formatter));
		contrato.setFimVigencia(LocalDate.parse(this.fimVigencia.subSequence(0, endIndex), formatter));
		contrato.setInicioVigencia(LocalDate.parse(this.inicioVigencia.subSequence(0, endIndex), formatter));
		
		contrato.setId(this.id);
		contrato.setInSituacao(this.inSituacao);
		contrato.setNumero(this.numero);
		contrato.setObjeto(this.objeto);
		contrato.setPropostaId(this.propostaId);
		contrato.setValorTotal(this.valorTotal);
		contrato.setVersao(this.versao);
		contrato.setPropostaIdSiconv(this.propostaIdSiconv);
		contrato.setDtFimVigenciaOriginal(LocalDate.parse(this.fimVigenciaOriginal.subSequence(0, endIndex), formatter));
		
		return contrato;
    }
    
  
    public boolean possuiMesmosLotes(ContratoDTO outro) {
   		return (this.getLotes().containsAll(outro.getLotes()) && 
   				outro.getLotes().containsAll(this.getLotes()));
    }


    public boolean possuiLotesDoMesmoFornecedorELicitacao(ContratoDTO outro) {
    	
    	if (this.getLotes() == null || this.getLotes().isEmpty()) {
    		return false;
    	}
    	if (outro.getLotes() == null || outro.getLotes().isEmpty()) {
    		return false;
    	} 
    	
    	return this.getLotes().get(0).possuiMesmoFornecedorELicitacao(outro.getLotes().get(0));
    }

	@JsonIgnore
	public boolean estaConcluido() {
		return getInSituacao().equals(SituacaoContratoEnum.CONCLUIDO.getSigla());
	}

}
