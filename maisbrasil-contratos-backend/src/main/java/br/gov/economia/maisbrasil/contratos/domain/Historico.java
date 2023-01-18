package br.gov.economia.maisbrasil.contratos.domain;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import javax.validation.constraints.*;
//
import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
///**
// * A Historico.
// */
//@Entity
//@Table(name = "historico")
public class Historico implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Size(max = 3)
//    @Column(name = "situacao_contrato", length = 3, nullable = false)
//    private String situacaoContrato;
//
//    @NotNull
//    @Size(max = 3)
//    @Column(name = "evento_gerador", length = 3, nullable = false)
//    private String eventoGerador;
//
//    @NotNull
//    @Size(max = 60)
//    @Column(name = "nome_responsavel", length = 60, nullable = false)
//    private String nomeResponsavel;
//
//    @NotNull
//    @Size(max = 11)
//    @Column(name = "cpf_responsavel", length = 11, nullable = false)
//    private String cpfResponsavel;
//
//    @NotNull
//    @Column(name = "data_registro", nullable = false)
//    private LocalDate dataRegistro;
//
//    @NotNull
//    @Column(name = "versao", precision = 21, scale = 2, nullable = false)
//    private BigDecimal versao;
//
//    @NotNull
//    @Size(max = 60)
//    @Column(name = "adt_login", length = 60, nullable = false)
//    private String adtLogin;
//
//    @NotNull
//    @Column(name = "adt_data_hora", nullable = false)
//    private LocalDate adtDataHora;
//
//    @NotNull
//    @Size(max = 6)
//    @Column(name = "adt_operacao", length = 6, nullable = false)
//    private String adtOperacao;
//
//    @ManyToOne
//    @JsonIgnoreProperties("historicos")
//    private Contrato contrato;
//
//    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getSituacaoContrato() {
//        return situacaoContrato;
//    }
//
//    public Historico situacaoContrato(String situacaoContrato) {
//        this.situacaoContrato = situacaoContrato;
//        return this;
//    }
//
//    public void setSituacaoContrato(String situacaoContrato) {
//        this.situacaoContrato = situacaoContrato;
//    }
//
//    public String getEventoGerador() {
//        return eventoGerador;
//    }
//
//    public Historico eventoGerador(String eventoGerador) {
//        this.eventoGerador = eventoGerador;
//        return this;
//    }
//
//    public void setEventoGerador(String eventoGerador) {
//        this.eventoGerador = eventoGerador;
//    }
//
//    public String getNomeResponsavel() {
//        return nomeResponsavel;
//    }
//
//    public Historico nomeResponsavel(String nomeResponsavel) {
//        this.nomeResponsavel = nomeResponsavel;
//        return this;
//    }
//
//    public void setNomeResponsavel(String nomeResponsavel) {
//        this.nomeResponsavel = nomeResponsavel;
//    }
//
//    public String getCpfResponsavel() {
//        return cpfResponsavel;
//    }
//
//    public Historico cpfResponsavel(String cpfResponsavel) {
//        this.cpfResponsavel = cpfResponsavel;
//        return this;
//    }
//
//    public void setCpfResponsavel(String cpfResponsavel) {
//        this.cpfResponsavel = cpfResponsavel;
//    }
//
//    public LocalDate getDataRegistro() {
//        return dataRegistro;
//    }
//
//    public Historico dataRegistro(LocalDate dataRegistro) {
//        this.dataRegistro = dataRegistro;
//        return this;
//    }
//
//    public void setDataRegistro(LocalDate dataRegistro) {
//        this.dataRegistro = dataRegistro;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Historico versao(BigDecimal versao) {
//        this.versao = versao;
//        return this;
//    }
//
//    public void setVersao(BigDecimal versao) {
//        this.versao = versao;
//    }
//
//    public String getAdtLogin() {
//        return adtLogin;
//    }
//
//    public Historico adtLogin(String adtLogin) {
//        this.adtLogin = adtLogin;
//        return this;
//    }
//
//    public void setAdtLogin(String adtLogin) {
//        this.adtLogin = adtLogin;
//    }
//
//    public LocalDate getAdtDataHora() {
//        return adtDataHora;
//    }
//
//    public Historico adtDataHora(LocalDate adtDataHora) {
//        this.adtDataHora = adtDataHora;
//        return this;
//    }
//
//    public void setAdtDataHora(LocalDate adtDataHora) {
//        this.adtDataHora = adtDataHora;
//    }
//
//    public String getAdtOperacao() {
//        return adtOperacao;
//    }
//
//    public Historico adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Contrato getContrato() {
//        return contrato;
//    }
//
//    public Historico contrato(Contrato contrato) {
//        this.contrato = contrato;
//        return this;
//    }
//
//    public void setContrato(Contrato contrato) {
//        this.contrato = contrato;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Historico)) {
//            return false;
//        }
//        return id != null && id.equals(((Historico) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Historico{" +
//            "id=" + getId() +
//            ", situacaoContrato='" + getSituacaoContrato() + "'" +
//            ", eventoGerador='" + getEventoGerador() + "'" +
//            ", nomeResponsavel='" + getNomeResponsavel() + "'" +
//            ", cpfResponsavel='" + getCpfResponsavel() + "'" +
//            ", dataRegistro='" + getDataRegistro() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
