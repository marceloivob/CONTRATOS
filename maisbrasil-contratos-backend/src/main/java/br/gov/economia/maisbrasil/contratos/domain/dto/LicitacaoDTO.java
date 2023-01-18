package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.LocalDate;

import br.gov.economia.maisbrasil.contratos.domain.bd.LicitacaoBD;
import lombok.Data;

@Data
public class LicitacaoDTO {
    
    public String numeroAno;

    public String inSituacao;

    public String objeto;

    public LocalDate dataHomologacao;

    public Long valorProcesso;

    public Long idLicitacaoFk;

    public Long propostaId;

    public String processoDeExecucao;

    public Long id;

    public LocalDate dataPublicacao;

    public String regimeContratacao;

    public String statusProcesso;

    public Long versao;
    
    public Long versaoNr;

    public String modalidade;
    
    public String numeroProcesso;
    
    private String situacaoAceiteProcessoExecucao;
    
    public LicitacaoBD converterParaBD() {
    	LicitacaoBD bd = new LicitacaoBD();
        bd.setDataHomologacao(this.dataHomologacao);
        bd.setDataPublicacao(this.dataPublicacao);
        bd.setId(this.id);
        bd.setIdLicitacaoFk(this.idLicitacaoFk);
        bd.setInSituacao(this.inSituacao);
        bd.setModalidade(this.modalidade);
        bd.setNumeroAno(this.numeroAno);
        bd.setObjeto(this.objeto);
        bd.setProcessoDeExecucao(this.processoDeExecucao);
        bd.setPropostaId(this.propostaId);
        bd.setRegimeContratacao(this.regimeContratacao);
        bd.setStatusProcesso(this.statusProcesso);
        bd.setValorProcesso(this.valorProcesso);
        bd.setVersao(this.versao);
        bd.setNumeroProcesso(this.numeroProcesso);
        bd.setSituacaoAceiteProcessoExecucao(situacaoAceiteProcessoExecucao);
        return bd;
    }
}
