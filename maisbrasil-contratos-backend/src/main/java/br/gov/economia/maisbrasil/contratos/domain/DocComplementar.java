package br.gov.economia.maisbrasil.contratos.domain;

import java.io.Serializable;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import javax.validation.constraints.*;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * A DocComplementar.
// */
//@Entity
//@Table(name = "doc_complementar")
public class DocComplementar implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Size(max = 40)
//    @Column(name = "numero_documento", length = 40, nullable = false)
//    private String numeroDocumento;
//
//    @NotNull
//    @Size(max = 100)
//    @Column(name = "orgao_emissor", length = 100, nullable = false)
//    private String orgaoEmissor;
//
//    @NotNull
//    @Column(name = "data_emissao", nullable = false)
//    private LocalDate dataEmissao;
//
//    @NotNull
//    @Column(name = "data_validade", nullable = false)
//    private LocalDate dataValidade;
//
//    @NotNull
//    @Size(max = 50)
//    @Column(name = "tipo", length = 50, nullable = false)
//    private String tipo;
//
//    @Size(max = 50)
//    @Column(name = "tipo_manifesto_ambiental", length = 50)
//    private String tipoManifestoAmbiental;
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
//    @OneToMany(mappedBy = "docComplementar")
//    private Set<SubmetaDocComplementar> submetaDocComplementars = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("docComplementars")
//    private Anexo anexo;
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
//    public String getNumeroDocumento() {
//        return numeroDocumento;
//    }
//
//    public DocComplementar numeroDocumento(String numeroDocumento) {
//        this.numeroDocumento = numeroDocumento;
//        return this;
//    }
//
//    public void setNumeroDocumento(String numeroDocumento) {
//        this.numeroDocumento = numeroDocumento;
//    }
//
//    public String getOrgaoEmissor() {
//        return orgaoEmissor;
//    }
//
//    public DocComplementar orgaoEmissor(String orgaoEmissor) {
//        this.orgaoEmissor = orgaoEmissor;
//        return this;
//    }
//
//    public void setOrgaoEmissor(String orgaoEmissor) {
//        this.orgaoEmissor = orgaoEmissor;
//    }
//
//    public LocalDate getDataEmissao() {
//        return dataEmissao;
//    }
//
//    public DocComplementar dataEmissao(LocalDate dataEmissao) {
//        this.dataEmissao = dataEmissao;
//        return this;
//    }
//
//    public void setDataEmissao(LocalDate dataEmissao) {
//        this.dataEmissao = dataEmissao;
//    }
//
//    public LocalDate getDataValidade() {
//        return dataValidade;
//    }
//
//    public DocComplementar dataValidade(LocalDate dataValidade) {
//        this.dataValidade = dataValidade;
//        return this;
//    }
//
//    public void setDataValidade(LocalDate dataValidade) {
//        this.dataValidade = dataValidade;
//    }
//
//    public String getTipo() {
//        return tipo;
//    }
//
//    public DocComplementar tipo(String tipo) {
//        this.tipo = tipo;
//        return this;
//    }
//
//    public void setTipo(String tipo) {
//        this.tipo = tipo;
//    }
//
//    public String getTipoManifestoAmbiental() {
//        return tipoManifestoAmbiental;
//    }
//
//    public DocComplementar tipoManifestoAmbiental(String tipoManifestoAmbiental) {
//        this.tipoManifestoAmbiental = tipoManifestoAmbiental;
//        return this;
//    }
//
//    public void setTipoManifestoAmbiental(String tipoManifestoAmbiental) {
//        this.tipoManifestoAmbiental = tipoManifestoAmbiental;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public DocComplementar versao(BigDecimal versao) {
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
//    public DocComplementar adtLogin(String adtLogin) {
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
//    public DocComplementar adtDataHora(LocalDate adtDataHora) {
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
//    public DocComplementar adtOperacao(String adtOperacao) {
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
//    public DocComplementar submetaDocComplementars(Set<SubmetaDocComplementar> submetaDocComplementars) {
//        this.submetaDocComplementars = submetaDocComplementars;
//        return this;
//    }
//
//    public DocComplementar addSubmetaDocComplementar(SubmetaDocComplementar submetaDocComplementar) {
//        this.submetaDocComplementars.add(submetaDocComplementar);
//        submetaDocComplementar.setDocComplementar(this);
//        return this;
//    }
//
//    public DocComplementar removeSubmetaDocComplementar(SubmetaDocComplementar submetaDocComplementar) {
//        this.submetaDocComplementars.remove(submetaDocComplementar);
//        submetaDocComplementar.setDocComplementar(null);
//        return this;
//    }
//
//    public void setSubmetaDocComplementars(Set<SubmetaDocComplementar> submetaDocComplementars) {
//        this.submetaDocComplementars = submetaDocComplementars;
//    }
//
//    public Anexo getAnexo() {
//        return anexo;
//    }
//
//    public DocComplementar anexo(Anexo anexo) {
//        this.anexo = anexo;
//        return this;
//    }
//
//    public void setAnexo(Anexo anexo) {
//        this.anexo = anexo;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof DocComplementar)) {
//            return false;
//        }
//        return id != null && id.equals(((DocComplementar) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "DocComplementar{" +
//            "id=" + getId() +
//            ", numeroDocumento='" + getNumeroDocumento() + "'" +
//            ", orgaoEmissor='" + getOrgaoEmissor() + "'" +
//            ", dataEmissao='" + getDataEmissao() + "'" +
//            ", dataValidade='" + getDataValidade() + "'" +
//            ", tipo='" + getTipo() + "'" +
//            ", tipoManifestoAmbiental='" + getTipoManifestoAmbiental() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
