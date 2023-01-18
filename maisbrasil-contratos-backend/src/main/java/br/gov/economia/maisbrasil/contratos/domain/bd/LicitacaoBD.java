package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.LicitacaoDTO;
import br.gov.economia.maisbrasil.contratos.domain.integracao.LicitacaoIntegracao;
import lombok.Data;

@Data
public class LicitacaoBD {
    
    @NotNull
    private String numeroAno;

    @NotNull
    private String adtLogin;

    @NotNull
    private String inSituacao;

    @NotNull
    private String objeto;

    private LocalDate dataHomologacao;

    @NotNull
    private Long valorProcesso;

    @NotNull
    private Long idLicitacaoFk;

    private Long propostaId;

    @NotNull
    private String adtOperacao;

    private String processoDeExecucao;

    private Long id;

    private LocalDate dataPublicacao;

    private String regimeContratacao;

    @NotNull
    private String statusProcesso;

    @NotNull
    private Long versao;
    
    @NotNull
    private Long versaoNr;

    private String modalidade;
    
    private String numeroProcesso;

    private String situacaoAceiteProcessoExecucao;
    
    private Instant dataHoraAuditoria;

    public LicitacaoDTO converterParaDTO() {
    	LicitacaoDTO dto = new LicitacaoDTO();
        dto.setDataHomologacao(this.dataHomologacao);
        dto.setDataPublicacao(this.dataPublicacao);
        dto.setId(this.id);
        dto.setIdLicitacaoFk(this.idLicitacaoFk);
        dto.setInSituacao(this.inSituacao);
        dto.setModalidade(this.modalidade);
        dto.setNumeroAno(this.numeroAno);
        dto.setObjeto(this.objeto);
        dto.setProcessoDeExecucao(this.processoDeExecucao);
        dto.setPropostaId(this.propostaId);
        dto.setRegimeContratacao(this.regimeContratacao);
        dto.setStatusProcesso(this.statusProcesso);
        dto.setValorProcesso(this.valorProcesso);
        dto.setVersao(this.versao);
        dto.setVersaoNr(this.versaoNr);
        dto.setNumeroProcesso(this.numeroProcesso);
        dto.setSituacaoAceiteProcessoExecucao(situacaoAceiteProcessoExecucao);

        return dto;
    }

	public static LicitacaoBD from(LicitacaoIntegracao licitacaoIntegracao) {
		LicitacaoBD lic = new LicitacaoBD();
		lic.setDataHomologacao(licitacaoIntegracao.getDataHomologacao());
		lic.setDataPublicacao(licitacaoIntegracao.getDataPublicacao());
		lic.setIdLicitacaoFk(licitacaoIntegracao.getIdLicitacaoFk());
		lic.setInSituacao(licitacaoIntegracao.getInSituacao());
		lic.setModalidade(licitacaoIntegracao.getModalidade());
		lic.setNumeroAno(licitacaoIntegracao.getNumeroAno());
		lic.setObjeto(licitacaoIntegracao.getObjeto());
		lic.setProcessoDeExecucao(licitacaoIntegracao.getProcessoDeExecucao());
		lic.setRegimeContratacao(licitacaoIntegracao.getRegimeContratacao());
		lic.setStatusProcesso(licitacaoIntegracao.getStatusProcesso());
		lic.setValorProcesso(licitacaoIntegracao.getValorProcesso());
		lic.setVersao(licitacaoIntegracao.getVersao());
		lic.setVersaoNr(licitacaoIntegracao.getVersaoNr());
		// TODO numero processo e situacao aceite processo execucao
		return lic;
	}
}
