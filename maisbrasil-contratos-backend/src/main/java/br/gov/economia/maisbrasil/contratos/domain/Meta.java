package br.gov.economia.maisbrasil.contratos.domain;
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
// * A Meta.
// */
//@Entity
//@Table(name = "meta")
public class Meta implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Column(name = "id_meta_vrpl", nullable = false)
//    private Integer idMetaVRPL;
//
//    @NotNull
//    @Size(max = 100)
//    @Column(name = "tx_descricao", length = 100, nullable = false)
//    private String txDescricao;
//
//    @NotNull
//    @Column(name = "numero", nullable = false)
//    private Integer numero;
//
//    @NotNull
//    @Column(name = "qt_itens", precision = 21, scale = 2, nullable = false)
//    private BigDecimal qtItens;
//
//    @NotNull
//    @Column(name = "in_social", nullable = false)
//    private Boolean inSocial;
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
//    @OneToMany(mappedBy = "meta")
//    private Set<Submeta> submetas = new HashSet<>();
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
//    public Integer getIdMetaVRPL() {
//        return idMetaVRPL;
//    }
//
//    public Meta idMetaVRPL(Integer idMetaVRPL) {
//        this.idMetaVRPL = idMetaVRPL;
//        return this;
//    }
//
//    public void setIdMetaVRPL(Integer idMetaVRPL) {
//        this.idMetaVRPL = idMetaVRPL;
//    }
//
//    public String getTxDescricao() {
//        return txDescricao;
//    }
//
//    public Meta txDescricao(String txDescricao) {
//        this.txDescricao = txDescricao;
//        return this;
//    }
//
//    public void setTxDescricao(String txDescricao) {
//        this.txDescricao = txDescricao;
//    }
//
//    public Integer getNumero() {
//        return numero;
//    }
//
//    public Meta numero(Integer numero) {
//        this.numero = numero;
//        return this;
//    }
//
//    public void setNumero(Integer numero) {
//        this.numero = numero;
//    }
//
//    public BigDecimal getQtItens() {
//        return qtItens;
//    }
//
//    public Meta qtItens(BigDecimal qtItens) {
//        this.qtItens = qtItens;
//        return this;
//    }
//
//    public void setQtItens(BigDecimal qtItens) {
//        this.qtItens = qtItens;
//    }
//
//    public Boolean isInSocial() {
//        return inSocial;
//    }
//
//    public Meta inSocial(Boolean inSocial) {
//        this.inSocial = inSocial;
//        return this;
//    }
//
//    public void setInSocial(Boolean inSocial) {
//        this.inSocial = inSocial;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Meta versao(BigDecimal versao) {
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
//    public Meta adtLogin(String adtLogin) {
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
//    public Meta adtDataHora(LocalDate adtDataHora) {
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
//    public Meta adtOperacao(String adtOperacao) {
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
//    public Meta submetas(Set<Submeta> submetas) {
//        this.submetas = submetas;
//        return this;
//    }
//
//    public Meta addSubmeta(Submeta submeta) {
//        this.submetas.add(submeta);
//        submeta.setMeta(this);
//        return this;
//    }
//
//    public Meta removeSubmeta(Submeta submeta) {
//        this.submetas.remove(submeta);
//        submeta.setMeta(null);
//        return this;
//    }
//
//    public void setSubmetas(Set<Submeta> submetas) {
//        this.submetas = submetas;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Meta)) {
//            return false;
//        }
//        return id != null && id.equals(((Meta) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Meta{" +
//            "id=" + getId() +
//            ", idMetaVRPL=" + getIdMetaVRPL() +
//            ", txDescricao='" + getTxDescricao() + "'" +
//            ", numero=" + getNumero() +
//            ", qtItens=" + getQtItens() +
//            ", inSocial='" + isInSocial() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
