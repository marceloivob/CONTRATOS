package br.gov.economia.maisbrasil.contratos.domain;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import javax.validation.constraints.*;
//
import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * A Licitacao.
// */
//@Entity
//@Table(name = "licitacao")
public class Licitacao implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Size(max = 1024)
//    @Column(name = "numero_ano", length = 1024, nullable = false)
//    private String numeroAno;
//
//    @NotNull
//    @Size(max = 1024)
//    @Column(name = "objeto", length = 1024, nullable = false)
//    private String objeto;
//
//    @NotNull
//    @Column(name = "valor_processo", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorProcesso;
//
//    @NotNull
//    @Size(max = 20)
//    @Column(name = "status_processo", length = 20, nullable = false)
//    private String statusProcesso;
//
//    @NotNull
//    @Column(name = "id_licitacao_fk", nullable = false)
//    private Integer idLicitacaoFk;
//
//    @NotNull
//    @Size(max = 3)
//    @Column(name = "in_situacao", length = 3, nullable = false)
//    private String inSituacao;
//
//    @Size(max = 50)
//    @Column(name = "modalidade", length = 50)
//    private String modalidade;
//
//    @Size(max = 1024)
//    @Column(name = "regime_contratacao", length = 1024)
//    private String regimeContratacao;
//
//    @Column(name = "data_publicacao")
//    private LocalDate dataPublicacao;
//
//    @Column(name = "data_homologacao")
//    private LocalDate dataHomologacao;
//
//    @Size(max = 50)
//    @Column(name = "processo_de_execucao", length = 50)
//    private String processoDeExecucao;
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
//    @OneToMany(mappedBy = "licitacao")
//    private Set<Lote> lotes = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("licitacaos")
//    private Proposta proposta;
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
//    public String getNumeroAno() {
//        return numeroAno;
//    }
//
//    public Licitacao numeroAno(String numeroAno) {
//        this.numeroAno = numeroAno;
//        return this;
//    }
//
//    public void setNumeroAno(String numeroAno) {
//        this.numeroAno = numeroAno;
//    }
//
//    public String getObjeto() {
//        return objeto;
//    }
//
//    public Licitacao objeto(String objeto) {
//        this.objeto = objeto;
//        return this;
//    }
//
//    public void setObjeto(String objeto) {
//        this.objeto = objeto;
//    }
//
//    public BigDecimal getValorProcesso() {
//        return valorProcesso;
//    }
//
//    public Licitacao valorProcesso(BigDecimal valorProcesso) {
//        this.valorProcesso = valorProcesso;
//        return this;
//    }
//
//    public void setValorProcesso(BigDecimal valorProcesso) {
//        this.valorProcesso = valorProcesso;
//    }
//
//    public String getStatusProcesso() {
//        return statusProcesso;
//    }
//
//    public Licitacao statusProcesso(String statusProcesso) {
//        this.statusProcesso = statusProcesso;
//        return this;
//    }
//
//    public void setStatusProcesso(String statusProcesso) {
//        this.statusProcesso = statusProcesso;
//    }
//
//    public Integer getIdLicitacaoFk() {
//        return idLicitacaoFk;
//    }
//
//    public Licitacao idLicitacaoFk(Integer idLicitacaoFk) {
//        this.idLicitacaoFk = idLicitacaoFk;
//        return this;
//    }
//
//    public void setIdLicitacaoFk(Integer idLicitacaoFk) {
//        this.idLicitacaoFk = idLicitacaoFk;
//    }
//
//    public String getInSituacao() {
//        return inSituacao;
//    }
//
//    public Licitacao inSituacao(String inSituacao) {
//        this.inSituacao = inSituacao;
//        return this;
//    }
//
//    public void setInSituacao(String inSituacao) {
//        this.inSituacao = inSituacao;
//    }
//
//    public String getModalidade() {
//        return modalidade;
//    }
//
//    public Licitacao modalidade(String modalidade) {
//        this.modalidade = modalidade;
//        return this;
//    }
//
//    public void setModalidade(String modalidade) {
//        this.modalidade = modalidade;
//    }
//
//    public String getRegimeContratacao() {
//        return regimeContratacao;
//    }
//
//    public Licitacao regimeContratacao(String regimeContratacao) {
//        this.regimeContratacao = regimeContratacao;
//        return this;
//    }
//
//    public void setRegimeContratacao(String regimeContratacao) {
//        this.regimeContratacao = regimeContratacao;
//    }
//
//    public LocalDate getDataPublicacao() {
//        return dataPublicacao;
//    }
//
//    public Licitacao dataPublicacao(LocalDate dataPublicacao) {
//        this.dataPublicacao = dataPublicacao;
//        return this;
//    }
//
//    public void setDataPublicacao(LocalDate dataPublicacao) {
//        this.dataPublicacao = dataPublicacao;
//    }
//
//    public LocalDate getDataHomologacao() {
//        return dataHomologacao;
//    }
//
//    public Licitacao dataHomologacao(LocalDate dataHomologacao) {
//        this.dataHomologacao = dataHomologacao;
//        return this;
//    }
//
//    public void setDataHomologacao(LocalDate dataHomologacao) {
//        this.dataHomologacao = dataHomologacao;
//    }
//
//    public String getProcessoDeExecucao() {
//        return processoDeExecucao;
//    }
//
//    public Licitacao processoDeExecucao(String processoDeExecucao) {
//        this.processoDeExecucao = processoDeExecucao;
//        return this;
//    }
//
//    public void setProcessoDeExecucao(String processoDeExecucao) {
//        this.processoDeExecucao = processoDeExecucao;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Licitacao versao(BigDecimal versao) {
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
//    public Licitacao adtLogin(String adtLogin) {
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
//    public Licitacao adtDataHora(LocalDate adtDataHora) {
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
//    public Licitacao adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Set<Lote> getLotes() {
//        return lotes;
//    }
//
//    public Licitacao lotes(Set<Lote> lotes) {
//        this.lotes = lotes;
//        return this;
//    }
//
//    public Licitacao addLote(Lote lote) {
//        this.lotes.add(lote);
//        lote.setLicitacao(this);
//        return this;
//    }
//
//    public Licitacao removeLote(Lote lote) {
//        this.lotes.remove(lote);
//        lote.setLicitacao(null);
//        return this;
//    }
//
//    public void setLotes(Set<Lote> lotes) {
//        this.lotes = lotes;
//    }
//
//    public Proposta getProposta() {
//        return proposta;
//    }
//
//    public Licitacao proposta(Proposta proposta) {
//        this.proposta = proposta;
//        return this;
//    }
//
//    public void setProposta(Proposta proposta) {
//        this.proposta = proposta;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Licitacao)) {
//            return false;
//        }
//        return id != null && id.equals(((Licitacao) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Licitacao{" +
//            "id=" + getId() +
//            ", numeroAno='" + getNumeroAno() + "'" +
//            ", objeto='" + getObjeto() + "'" +
//            ", valorProcesso=" + getValorProcesso() +
//            ", statusProcesso='" + getStatusProcesso() + "'" +
//            ", idLicitacaoFk=" + getIdLicitacaoFk() +
//            ", inSituacao='" + getInSituacao() + "'" +
//            ", modalidade='" + getModalidade() + "'" +
//            ", regimeContratacao='" + getRegimeContratacao() + "'" +
//            ", dataPublicacao='" + getDataPublicacao() + "'" +
//            ", dataHomologacao='" + getDataHomologacao() + "'" +
//            ", processoDeExecucao='" + getProcessoDeExecucao() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
