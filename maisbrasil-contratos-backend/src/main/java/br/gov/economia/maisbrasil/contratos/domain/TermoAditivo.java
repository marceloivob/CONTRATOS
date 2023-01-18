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
// * A TermoAditivo.
// */
//@Entity
//@Table(name = "termo_aditivo")
public class TermoAditivo implements Serializable {
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
//    @Column(name = "in_tipo_de_aditivo", length = 3, nullable = false)
//    private String inTipoDeAditivo;
//
//    @NotNull
//    @Column(name = "in_alteracao_vigencia", nullable = false)
//    private Boolean inAlteracaoVigencia;
//
//    @NotNull
//    @Column(name = "in_alteracao_clausula", nullable = false)
//    private Boolean inAlteracaoClausula;
//
//    @NotNull
//    @Column(name = "in_alteracao_objeto", nullable = false)
//    private Boolean inAlteracaoObjeto;
//
//    @NotNull
//    @Size(max = 20)
//    @Column(name = "numero", length = 20, nullable = false)
//    private String numero;
//
//    @NotNull
//    @Column(name = "data_assinatura", nullable = false)
//    private LocalDate dataAssinatura;
//
//    @NotNull
//    @Column(name = "data_publicacao", nullable = false)
//    private LocalDate dataPublicacao;
//
//    @NotNull
//    @Column(name = "valor_acrescimo", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorAcrescimo;
//
//    @NotNull
//    @Column(name = "valor_supressao", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorSupressao;
//
//    @NotNull
//    @Column(name = "nova_data_fim_vigencia", nullable = false)
//    private LocalDate novaDataFimVigencia;
//
//    @NotNull
//    @Size(max = 1000)
//    @Column(name = "justificativa", length = 1000, nullable = false)
//    private String justificativa;
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
//    @OneToMany(mappedBy = "termoAditivo")
//    private Set<Anexo> anexos = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("termoAditivos")
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
//    public String getInTipoDeAditivo() {
//        return inTipoDeAditivo;
//    }
//
//    public TermoAditivo inTipoDeAditivo(String inTipoDeAditivo) {
//        this.inTipoDeAditivo = inTipoDeAditivo;
//        return this;
//    }
//
//    public void setInTipoDeAditivo(String inTipoDeAditivo) {
//        this.inTipoDeAditivo = inTipoDeAditivo;
//    }
//
//    public Boolean isInAlteracaoVigencia() {
//        return inAlteracaoVigencia;
//    }
//
//    public TermoAditivo inAlteracaoVigencia(Boolean inAlteracaoVigencia) {
//        this.inAlteracaoVigencia = inAlteracaoVigencia;
//        return this;
//    }
//
//    public void setInAlteracaoVigencia(Boolean inAlteracaoVigencia) {
//        this.inAlteracaoVigencia = inAlteracaoVigencia;
//    }
//
//    public Boolean isInAlteracaoClausula() {
//        return inAlteracaoClausula;
//    }
//
//    public TermoAditivo inAlteracaoClausula(Boolean inAlteracaoClausula) {
//        this.inAlteracaoClausula = inAlteracaoClausula;
//        return this;
//    }
//
//    public void setInAlteracaoClausula(Boolean inAlteracaoClausula) {
//        this.inAlteracaoClausula = inAlteracaoClausula;
//    }
//
//    public Boolean isInAlteracaoObjeto() {
//        return inAlteracaoObjeto;
//    }
//
//    public TermoAditivo inAlteracaoObjeto(Boolean inAlteracaoObjeto) {
//        this.inAlteracaoObjeto = inAlteracaoObjeto;
//        return this;
//    }
//
//    public void setInAlteracaoObjeto(Boolean inAlteracaoObjeto) {
//        this.inAlteracaoObjeto = inAlteracaoObjeto;
//    }
//
//    public String getNumero() {
//        return numero;
//    }
//
//    public TermoAditivo numero(String numero) {
//        this.numero = numero;
//        return this;
//    }
//
//    public void setNumero(String numero) {
//        this.numero = numero;
//    }
//
//    public LocalDate getDataAssinatura() {
//        return dataAssinatura;
//    }
//
//    public TermoAditivo dataAssinatura(LocalDate dataAssinatura) {
//        this.dataAssinatura = dataAssinatura;
//        return this;
//    }
//
//    public void setDataAssinatura(LocalDate dataAssinatura) {
//        this.dataAssinatura = dataAssinatura;
//    }
//
//    public LocalDate getDataPublicacao() {
//        return dataPublicacao;
//    }
//
//    public TermoAditivo dataPublicacao(LocalDate dataPublicacao) {
//        this.dataPublicacao = dataPublicacao;
//        return this;
//    }
//
//    public void setDataPublicacao(LocalDate dataPublicacao) {
//        this.dataPublicacao = dataPublicacao;
//    }
//
//    public BigDecimal getValorAcrescimo() {
//        return valorAcrescimo;
//    }
//
//    public TermoAditivo valorAcrescimo(BigDecimal valorAcrescimo) {
//        this.valorAcrescimo = valorAcrescimo;
//        return this;
//    }
//
//    public void setValorAcrescimo(BigDecimal valorAcrescimo) {
//        this.valorAcrescimo = valorAcrescimo;
//    }
//
//    public BigDecimal getValorSupressao() {
//        return valorSupressao;
//    }
//
//    public TermoAditivo valorSupressao(BigDecimal valorSupressao) {
//        this.valorSupressao = valorSupressao;
//        return this;
//    }
//
//    public void setValorSupressao(BigDecimal valorSupressao) {
//        this.valorSupressao = valorSupressao;
//    }
//
//    public LocalDate getNovaDataFimVigencia() {
//        return novaDataFimVigencia;
//    }
//
//    public TermoAditivo novaDataFimVigencia(LocalDate novaDataFimVigencia) {
//        this.novaDataFimVigencia = novaDataFimVigencia;
//        return this;
//    }
//
//    public void setNovaDataFimVigencia(LocalDate novaDataFimVigencia) {
//        this.novaDataFimVigencia = novaDataFimVigencia;
//    }
//
//    public String getJustificativa() {
//        return justificativa;
//    }
//
//    public TermoAditivo justificativa(String justificativa) {
//        this.justificativa = justificativa;
//        return this;
//    }
//
//    public void setJustificativa(String justificativa) {
//        this.justificativa = justificativa;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public TermoAditivo versao(BigDecimal versao) {
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
//    public TermoAditivo adtLogin(String adtLogin) {
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
//    public TermoAditivo adtDataHora(LocalDate adtDataHora) {
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
//    public TermoAditivo adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Set<Anexo> getAnexos() {
//        return anexos;
//    }
//
//    public TermoAditivo anexos(Set<Anexo> anexos) {
//        this.anexos = anexos;
//        return this;
//    }
//
//    public TermoAditivo addAnexo(Anexo anexo) {
//        this.anexos.add(anexo);
//        anexo.setTermoAditivo(this);
//        return this;
//    }
//
//    public TermoAditivo removeAnexo(Anexo anexo) {
//        this.anexos.remove(anexo);
//        anexo.setTermoAditivo(null);
//        return this;
//    }
//
//    public void setAnexos(Set<Anexo> anexos) {
//        this.anexos = anexos;
//    }
//
//    public Contrato getContrato() {
//        return contrato;
//    }
//
//    public TermoAditivo contrato(Contrato contrato) {
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
//        if (!(o instanceof TermoAditivo)) {
//            return false;
//        }
//        return id != null && id.equals(((TermoAditivo) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "TermoAditivo{" +
//            "id=" + getId() +
//            ", inTipoDeAditivo='" + getInTipoDeAditivo() + "'" +
//            ", inAlteracaoVigencia='" + isInAlteracaoVigencia() + "'" +
//            ", inAlteracaoClausula='" + isInAlteracaoClausula() + "'" +
//            ", inAlteracaoObjeto='" + isInAlteracaoObjeto() + "'" +
//            ", numero='" + getNumero() + "'" +
//            ", dataAssinatura='" + getDataAssinatura() + "'" +
//            ", dataPublicacao='" + getDataPublicacao() + "'" +
//            ", valorAcrescimo=" + getValorAcrescimo() +
//            ", valorSupressao=" + getValorSupressao() +
//            ", novaDataFimVigencia='" + getNovaDataFimVigencia() + "'" +
//            ", justificativa='" + getJustificativa() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
