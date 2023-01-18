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
// * A Lote.
// */
//@Entity
//@Table(name = "lote")
public class Lote implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Column(name = "numero", nullable = false)
//    private Integer numero;
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
//    @OneToMany(mappedBy = "lote")
//    private Set<Submeta> submetas = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("lotes")
//    private Contrato contrato;
//
//    @ManyToOne
//    @JsonIgnoreProperties("lotes")
//    private Licitacao licitacao;
//
//    @ManyToOne
//    @JsonIgnoreProperties("lotes")
//    private Fornecedor fornecedor;
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
//    public Integer getNumero() {
//        return numero;
//    }
//
//    public Lote numero(Integer numero) {
//        this.numero = numero;
//        return this;
//    }
//
//    public void setNumero(Integer numero) {
//        this.numero = numero;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Lote versao(BigDecimal versao) {
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
//    public Lote adtLogin(String adtLogin) {
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
//    public Lote adtDataHora(LocalDate adtDataHora) {
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
//    public Lote adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Set<Submeta> getSubmetas() {
//        return submetas;
//    }
//
//    public Lote submetas(Set<Submeta> submetas) {
//        this.submetas = submetas;
//        return this;
//    }
//
//    public Lote addSubmeta(Submeta submeta) {
//        this.submetas.add(submeta);
//        submeta.setLote(this);
//        return this;
//    }
//
//    public Lote removeSubmeta(Submeta submeta) {
//        this.submetas.remove(submeta);
//        submeta.setLote(null);
//        return this;
//    }
//
//    public void setSubmetas(Set<Submeta> submetas) {
//        this.submetas = submetas;
//    }
//
//    public Contrato getContrato() {
//        return contrato;
//    }
//
//    public Lote contrato(Contrato contrato) {
//        this.contrato = contrato;
//        return this;
//    }
//
//    public void setContrato(Contrato contrato) {
//        this.contrato = contrato;
//    }
//
//    public Licitacao getLicitacao() {
//        return licitacao;
//    }
//
//    public Lote licitacao(Licitacao licitacao) {
//        this.licitacao = licitacao;
//        return this;
//    }
//
//    public void setLicitacao(Licitacao licitacao) {
//        this.licitacao = licitacao;
//    }
//
//    public Fornecedor getFornecedor() {
//        return fornecedor;
//    }
//
//    public Lote fornecedor(Fornecedor fornecedor) {
//        this.fornecedor = fornecedor;
//        return this;
//    }
//
//    public void setFornecedor(Fornecedor fornecedor) {
//        this.fornecedor = fornecedor;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Lote)) {
//            return false;
//        }
//        return id != null && id.equals(((Lote) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Lote{" +
//            "id=" + getId() +
//            ", numero=" + getNumero() +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
