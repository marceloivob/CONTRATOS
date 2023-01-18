
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
// * A Fornecedor.
// */
//@Entity
//@Table(name = "fornecedor")
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Size(max = 150)
//    @Column(name = "razao_social", length = 150, nullable = false)
//    private String razaoSocial;
//
//    @NotNull
//    @Size(max = 4)
//    @Column(name = "tipo_identificacao", length = 4, nullable = false)
//    private String tipoIdentificacao;
//
//    @NotNull
//    @Size(max = 20)
//    @Column(name = "identificacao", length = 20, nullable = false)
//    private String identificacao;
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
//    @OneToMany(mappedBy = "fornecedor")
//    private Set<Lote> lotes = new HashSet<>();
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
//    public String getRazaoSocial() {
//        return razaoSocial;
//    }
//
//    public Fornecedor razaoSocial(String razaoSocial) {
//        this.razaoSocial = razaoSocial;
//        return this;
//    }
//
//    public void setRazaoSocial(String razaoSocial) {
//        this.razaoSocial = razaoSocial;
//    }
//
//    public String getTipoIdentificacao() {
//        return tipoIdentificacao;
//    }
//
//    public Fornecedor tipoIdentificacao(String tipoIdentificacao) {
//        this.tipoIdentificacao = tipoIdentificacao;
//        return this;
//    }
//
//    public void setTipoIdentificacao(String tipoIdentificacao) {
//        this.tipoIdentificacao = tipoIdentificacao;
//    }
//
//    public String getIdentificacao() {
//        return identificacao;
//    }
//
//    public Fornecedor identificacao(String identificacao) {
//        this.identificacao = identificacao;
//        return this;
//    }
//
//    public void setIdentificacao(String identificacao) {
//        this.identificacao = identificacao;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Fornecedor versao(BigDecimal versao) {
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
//    public Fornecedor adtLogin(String adtLogin) {
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
//    public Fornecedor adtDataHora(LocalDate adtDataHora) {
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
//    public Fornecedor adtOperacao(String adtOperacao) {
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
//    public Fornecedor lotes(Set<Lote> lotes) {
//        this.lotes = lotes;
//        return this;
//    }
//
//    public Fornecedor addLote(Lote lote) {
//        this.lotes.add(lote);
//        lote.setFornecedor(this);
//        return this;
//    }
//
//    public Fornecedor removeLote(Lote lote) {
//        this.lotes.remove(lote);
//        lote.setFornecedor(null);
//        return this;
//    }
//
//    public void setLotes(Set<Lote> lotes) {
//        this.lotes = lotes;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Fornecedor)) {
//            return false;
//        }
//        return id != null && id.equals(((Fornecedor) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Fornecedor{" +
//            "id=" + getId() +
//            ", razaoSocial='" + getRazaoSocial() + "'" +
//            ", tipoIdentificacao='" + getTipoIdentificacao() + "'" +
//            ", identificacao='" + getIdentificacao() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
