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
// * A Contrato.
// */
//@Entity
//@Table(name = "contrato")
public class Contrato implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Size(max = 20)
//    @Column(name = "numero", length = 20, nullable = false)
//    private String numero;
//
//    @NotNull
//    @Size(max = 5000)
//    @Column(name = "objeto", length = 5000, nullable = false)
//    private String objeto;
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
//    @Column(name = "inicio_vigencia", nullable = false)
//    private LocalDate inicioVigencia;
//
//    @NotNull
//    @Column(name = "fim_vigencia", nullable = false)
//    private LocalDate fimVigencia;
//
//    @NotNull
//    @Column(name = "valor_total", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorTotal;
//
//    @NotNull
//    @Column(name = "in_situacao", nullable = false)
//    private String inSituacao;
//
//    @NotNull
//    @Column(name = "apto_a_iniciar", nullable = false)
//    private Boolean aptoAIniciar;
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
//    @OneToMany(mappedBy = "contrato")
//    private Set<Anexo> anexos = new HashSet<>();
//
//    @OneToMany(mappedBy = "contrato")
//    private Set<Historico> historicos = new HashSet<>();
//
//    @OneToMany(mappedBy = "contrato")
//    private Set<TermoAditivo> termoAditivos = new HashSet<>();
//
//    @OneToMany(mappedBy = "contrato")
//    private Set<Lote> lotes = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("contratos")
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
//    public String getNumero() {
//        return numero;
//    }
//
//    public Contrato numero(String numero) {
//        this.numero = numero;
//        return this;
//    }
//
//    public void setNumero(String numero) {
//        this.numero = numero;
//    }
//
//    public String getObjeto() {
//        return objeto;
//    }
//
//    public Contrato objeto(String objeto) {
//        this.objeto = objeto;
//        return this;
//    }
//
//    public void setObjeto(String objeto) {
//        this.objeto = objeto;
//    }
//
//    public LocalDate getDataAssinatura() {
//        return dataAssinatura;
//    }
//
//    public Contrato dataAssinatura(LocalDate dataAssinatura) {
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
//    public Contrato dataPublicacao(LocalDate dataPublicacao) {
//        this.dataPublicacao = dataPublicacao;
//        return this;
//    }
//
//    public void setDataPublicacao(LocalDate dataPublicacao) {
//        this.dataPublicacao = dataPublicacao;
//    }
//
//    public LocalDate getInicioVigencia() {
//        return inicioVigencia;
//    }
//
//    public Contrato inicioVigencia(LocalDate inicioVigencia) {
//        this.inicioVigencia = inicioVigencia;
//        return this;
//    }
//
//    public void setInicioVigencia(LocalDate inicioVigencia) {
//        this.inicioVigencia = inicioVigencia;
//    }
//
//    public LocalDate getFimVigencia() {
//        return fimVigencia;
//    }
//
//    public Contrato fimVigencia(LocalDate fimVigencia) {
//        this.fimVigencia = fimVigencia;
//        return this;
//    }
//
//    public void setFimVigencia(LocalDate fimVigencia) {
//        this.fimVigencia = fimVigencia;
//    }
//
//    public BigDecimal getValorTotal() {
//        return valorTotal;
//    }
//
//    public Contrato valorTotal(BigDecimal valorTotal) {
//        this.valorTotal = valorTotal;
//        return this;
//    }
//
//    public void setValorTotal(BigDecimal valorTotal) {
//        this.valorTotal = valorTotal;
//    }
//
//    public String getInSituacao() {
//        return inSituacao;
//    }
//
//    public Contrato inSituacao(String inSituacao) {
//        this.inSituacao = inSituacao;
//        return this;
//    }
//
//    public void setInSituacao(String inSituacao) {
//        this.inSituacao = inSituacao;
//    }
//
//    public Boolean isAptoAIniciar() {
//        return aptoAIniciar;
//    }
//
//    public Contrato aptoAIniciar(Boolean aptoAIniciar) {
//        this.aptoAIniciar = aptoAIniciar;
//        return this;
//    }
//
//    public void setAptoAIniciar(Boolean aptoAIniciar) {
//        this.aptoAIniciar = aptoAIniciar;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Contrato versao(BigDecimal versao) {
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
//    public Contrato adtLogin(String adtLogin) {
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
//    public Contrato adtDataHora(LocalDate adtDataHora) {
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
//    public Contrato adtOperacao(String adtOperacao) {
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
//    public Contrato anexos(Set<Anexo> anexos) {
//        this.anexos = anexos;
//        return this;
//    }
//
//    public Contrato addAnexo(Anexo anexo) {
//        this.anexos.add(anexo);
//        anexo.setContrato(this);
//        return this;
//    }
//
//    public Contrato removeAnexo(Anexo anexo) {
//        this.anexos.remove(anexo);
//        anexo.setContrato(null);
//        return this;
//    }
//
//    public void setAnexos(Set<Anexo> anexos) {
//        this.anexos = anexos;
//    }
//
//    public Set<Historico> getHistoricos() {
//        return historicos;
//    }
//
//    public Contrato historicos(Set<Historico> historicos) {
//        this.historicos = historicos;
//        return this;
//    }
//
//    public Contrato addHistorico(Historico historico) {
//        this.historicos.add(historico);
//        historico.setContrato(this);
//        return this;
//    }
//
//    public Contrato removeHistorico(Historico historico) {
//        this.historicos.remove(historico);
//        historico.setContrato(null);
//        return this;
//    }
//
//    public void setHistoricos(Set<Historico> historicos) {
//        this.historicos = historicos;
//    }
//
//    public Set<TermoAditivo> getTermoAditivos() {
//        return termoAditivos;
//    }
//
//    public Contrato termoAditivos(Set<TermoAditivo> termoAditivos) {
//        this.termoAditivos = termoAditivos;
//        return this;
//    }
//
//    public Contrato addTermoAditivo(TermoAditivo termoAditivo) {
//        this.termoAditivos.add(termoAditivo);
//        termoAditivo.setContrato(this);
//        return this;
//    }
//
//    public Contrato removeTermoAditivo(TermoAditivo termoAditivo) {
//        this.termoAditivos.remove(termoAditivo);
//        termoAditivo.setContrato(null);
//        return this;
//    }
//
//    public void setTermoAditivos(Set<TermoAditivo> termoAditivos) {
//        this.termoAditivos = termoAditivos;
//    }
//
//    public Set<Lote> getLotes() {
//        return lotes;
//    }
//
//    public Contrato lotes(Set<Lote> lotes) {
//        this.lotes = lotes;
//        return this;
//    }
//
//    public Contrato addLote(Lote lote) {
//        this.lotes.add(lote);
//        lote.setContrato(this);
//        return this;
//    }
//
//    public Contrato removeLote(Lote lote) {
//        this.lotes.remove(lote);
//        lote.setContrato(null);
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
//    public Contrato proposta(Proposta proposta) {
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
//        if (!(o instanceof Contrato)) {
//            return false;
//        }
//        return id != null && id.equals(((Contrato) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Contrato{" +
//            "id=" + getId() +
//            ", numero='" + getNumero() + "'" +
//            ", objeto='" + getObjeto() + "'" +
//            ", dataAssinatura='" + getDataAssinatura() + "'" +
//            ", dataPublicacao='" + getDataPublicacao() + "'" +
//            ", inicioVigencia='" + getInicioVigencia() + "'" +
//            ", fimVigencia='" + getFimVigencia() + "'" +
//            ", valorTotal=" + getValorTotal() +
//            ", inSituacao='" + getInSituacao() + "'" +
//            ", aptoAIniciar='" + isAptoAIniciar() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
