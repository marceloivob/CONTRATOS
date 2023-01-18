package br.gov.economia.maisbrasil.contratos.domain.dto;

import java.time.LocalDateTime;

import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;
import lombok.Data;

@Data
public class HistoricoDTO {
    
    public String nomeResponsavel;

    public Long contratoId;

    public String eventoGerador;

    public String adtLogin;

    public String adtOperacao;

    public String situacaoContrato;

    public Long id;

    public String cpfResponsavel;

    public LocalDateTime dataRegistro;

    public Long versao;

    public Long termoAditivoId;

    
    public HistoricoBD converterParaBD() {
    	HistoricoBD bd = new HistoricoBD();
        bd.setContratoId(this.contratoId);
        bd.setCpfResponsavel(this.cpfResponsavel);
        bd.setDataRegistro(this.dataRegistro);
        bd.setEventoGerador(this.eventoGerador);
        bd.setId(this.id);
        bd.setNomeResponsavel(this.nomeResponsavel);
        bd.setSituacaoContrato(this.situacaoContrato);
        bd.setVersao(this.versao);
        bd.setTermoAditivoId(this.termoAditivoId);
        return bd;
    }
}
