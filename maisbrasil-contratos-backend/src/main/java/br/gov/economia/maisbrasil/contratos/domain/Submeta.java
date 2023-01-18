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
// * A Submeta.
// */
//@Entity
//@Table(name = "submeta")
public class Submeta implements Serializable {
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
//    @Column(name = "vl_repasse", precision = 21, scale = 2, nullable = false)
//    private BigDecimal vlRepasse;
//
//    @NotNull
//    @Column(name = "vl_outros", precision = 21, scale = 2, nullable = false)
//    private BigDecimal vlOutros;
//
//    @NotNull
//    @Size(max = 3)
//    @Column(name = "in_regime_execucao", length = 3, nullable = false)
//    private String inRegimeExecucao;
//
//    @NotNull
//    @Column(name = "vl_contrapartida", precision = 21, scale = 2, nullable = false)
//    private BigDecimal vlContrapartida;
//
//    @NotNull
//    @Column(name = "vl_total_licitado", precision = 21, scale = 2, nullable = false)
//    private BigDecimal vlTotalLicitado;
//
//    @NotNull
//    @Size(max = 3)
//    @Column(name = "in_situacao", length = 3, nullable = false)
//    private String inSituacao;
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
//    @OneToMany(mappedBy = "submeta")
//    private Set<SubmetaDocComplementar> submetaDocComplementars = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("submetas")
//    private Meta meta;
//
//    @ManyToOne
//    @JsonIgnoreProperties("submetas")
//    private Proposta proposta;
//
//    @ManyToOne
//    @JsonIgnoreProperties("submetas")
//    private Lote lote;
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
//    public Submeta numero(Integer numero) {
//        this.numero = numero;
//        return this;
//    }
//
//    public void setNumero(Integer numero) {
//        this.numero = numero;
//    }
//
//    public BigDecimal getVlRepasse() {
//        return vlRepasse;
//    }
//
//    public Submeta vlRepasse(BigDecimal vlRepasse) {
//        this.vlRepasse = vlRepasse;
//        return this;
//    }
//
//    public void setVlRepasse(BigDecimal vlRepasse) {
//        this.vlRepasse = vlRepasse;
//    }
//
//    public BigDecimal getVlOutros() {
//        return vlOutros;
//    }
//
//    public Submeta vlOutros(BigDecimal vlOutros) {
//        this.vlOutros = vlOutros;
//        return this;
//    }
//
//    public void setVlOutros(BigDecimal vlOutros) {
//        this.vlOutros = vlOutros;
//    }
//
//    public String getInRegimeExecucao() {
//        return inRegimeExecucao;
//    }
//
//    public Submeta inRegimeExecucao(String inRegimeExecucao) {
//        this.inRegimeExecucao = inRegimeExecucao;
//        return this;
//    }
//
//    public void setInRegimeExecucao(String inRegimeExecucao) {
//        this.inRegimeExecucao = inRegimeExecucao;
//    }
//
//    public BigDecimal getVlContrapartida() {
//        return vlContrapartida;
//    }
//
//    public Submeta vlContrapartida(BigDecimal vlContrapartida) {
//        this.vlContrapartida = vlContrapartida;
//        return this;
//    }
//
//    public void setVlContrapartida(BigDecimal vlContrapartida) {
//        this.vlContrapartida = vlContrapartida;
//    }
//
//    public BigDecimal getVlTotalLicitado() {
//        return vlTotalLicitado;
//    }
//
//    public Submeta vlTotalLicitado(BigDecimal vlTotalLicitado) {
//        this.vlTotalLicitado = vlTotalLicitado;
//        return this;
//    }
//
//    public void setVlTotalLicitado(BigDecimal vlTotalLicitado) {
//        this.vlTotalLicitado = vlTotalLicitado;
//    }
//
//    public String getInSituacao() {
//        return inSituacao;
//    }
//
//    public Submeta inSituacao(String inSituacao) {
//        this.inSituacao = inSituacao;
//        return this;
//    }
//
//    public void setInSituacao(String inSituacao) {
//        this.inSituacao = inSituacao;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Submeta versao(BigDecimal versao) {
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
//    public Submeta adtLogin(String adtLogin) {
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
//    public Submeta adtDataHora(LocalDate adtDataHora) {
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
//    public Submeta adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Set<SubmetaDocComplementar> getSubmetaDocComplementars() {
//        return submetaDocComplementars;
//    }
//
//    public Submeta submetaDocComplementars(Set<SubmetaDocComplementar> submetaDocComplementars) {
//        this.submetaDocComplementars = submetaDocComplementars;
//        return this;
//    }
//
//    public Submeta addSubmetaDocComplementar(SubmetaDocComplementar submetaDocComplementar) {
//        this.submetaDocComplementars.add(submetaDocComplementar);
//        submetaDocComplementar.setSubmeta(this);
//        return this;
//    }
//
//    public Submeta removeSubmetaDocComplementar(SubmetaDocComplementar submetaDocComplementar) {
//        this.submetaDocComplementars.remove(submetaDocComplementar);
//        submetaDocComplementar.setSubmeta(null);
//        return this;
//    }
//
//    public void setSubmetaDocComplementars(Set<SubmetaDocComplementar> submetaDocComplementars) {
//        this.submetaDocComplementars = submetaDocComplementars;
//    }
//
//    public Meta getMeta() {
//        return meta;
//    }
//
//    public Submeta meta(Meta meta) {
//        this.meta = meta;
//        return this;
//    }
//
//    public void setMeta(Meta meta) {
//        this.meta = meta;
//    }
//
//    public Proposta getProposta() {
//        return proposta;
//    }
//
//    public Submeta proposta(Proposta proposta) {
//        this.proposta = proposta;
//        return this;
//    }
//
//    public void setProposta(Proposta proposta) {
//        this.proposta = proposta;
//    }
//
//    public Lote getLote() {
//        return lote;
//    }
//
//    public Submeta lote(Lote lote) {
//        this.lote = lote;
//        return this;
//    }
//
//    public void setLote(Lote lote) {
//        this.lote = lote;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Submeta)) {
//            return false;
//        }
//        return id != null && id.equals(((Submeta) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Submeta{" +
//            "id=" + getId() +
//            ", numero=" + getNumero() +
//            ", vlRepasse=" + getVlRepasse() +
//            ", vlOutros=" + getVlOutros() +
//            ", inRegimeExecucao='" + getInRegimeExecucao() + "'" +
//            ", vlContrapartida=" + getVlContrapartida() +
//            ", vlTotalLicitado=" + getVlTotalLicitado() +
//            ", inSituacao='" + getInSituacao() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
