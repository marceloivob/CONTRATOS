package br.gov.economia.maisbrasil.contratos.domain.bd;

import java.time.Instant;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import br.gov.economia.maisbrasil.contratos.domain.dto.HistoricoDTO;
import lombok.Data;

@Data
public class HistoricoBD {
    
    @NotNull
    private String nomeResponsavel;

    private Long contratoId;

    @NotNull
    private String eventoGerador;

    @NotNull
    private String adtLogin;

    @NotNull
    private String adtOperacao;

    @NotNull
    private String situacaoContrato;

    private Long id;

    @NotNull
    private String cpfResponsavel;

    @NotNull
    private LocalDateTime dataRegistro;

    @NotNull
    private Long versao;
    
    private Long termoAditivoId;


    
    private Instant dataHoraAuditoria;

    public HistoricoDTO converterParaDTO() {
    	HistoricoDTO dto = new HistoricoDTO();
        dto.setContratoId(this.contratoId);
        dto.setCpfResponsavel(this.cpfResponsavel);
        dto.setDataRegistro(this.dataRegistro);
        dto.setEventoGerador(this.eventoGerador);
        dto.setId(this.id);
        dto.setNomeResponsavel(this.nomeResponsavel);
        dto.setSituacaoContrato(this.situacaoContrato);
        dto.setVersao(this.versao);
        dto.setTermoAditivoId(this.termoAditivoId);
        return dto;
    }
}
