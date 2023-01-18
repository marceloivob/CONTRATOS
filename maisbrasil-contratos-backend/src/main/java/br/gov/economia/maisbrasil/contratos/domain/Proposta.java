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
// * A Proposta.
// */
//@Entity
//@Table(name = "proposta")
public class Proposta implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Column(name = "id_siconv", nullable = false)
//    private Integer idSiconv;
//
//    @NotNull
//    @Column(name = "numero_proposta", nullable = false)
//    private Integer numeroProposta;
//
//    @NotNull
//    @Column(name = "ano_proposta", nullable = false)
//    private Integer anoProposta;
//
//    @NotNull
//    @Column(name = "valor_global", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorGlobal;
//
//    @NotNull
//    @Column(name = "valor_repasse", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorRepasse;
//
//    @NotNull
//    @Column(name = "valor_contrapartida", precision = 21, scale = 2, nullable = false)
//    private BigDecimal valorContrapartida;
//
//    @NotNull
//    @Column(name = "modalidade", nullable = false)
//    private Integer modalidade;
//
//    @Size(max = 5000)
//    @Column(name = "nome_objeto", length = 5000)
//    private String nomeObjeto;
//
//    @Column(name = "numero_convenio")
//    private Integer numeroConvenio;
//
//    @Column(name = "ano_convenio")
//    private Integer anoConvenio;
//
//    @Column(name = "data_assinatura_convenio")
//    private LocalDate dataAssinaturaConvenio;
//
//    @NotNull
//    @Size(max = 13)
//    @Column(name = "codigo_programa", length = 13, nullable = false)
//    private String codigoPrograma;
//
//    @NotNull
//    @Size(max = 255)
//    @Column(name = "nome_programa", length = 255, nullable = false)
//    private String nomePrograma;
//
//    @Size(max = 1024)
//    @Column(name = "identificacao_proponente", length = 1024)
//    private String identificacaoProponente;
//
//    @Size(max = 1024)
//    @Column(name = "nome_proponente", length = 1024)
//    private String nomeProponente;
//
//    @NotNull
//    @Size(max = 2)
//    @Column(name = "uf", length = 2, nullable = false)
//    private String uf;
//
//    @Column(name = "pc_min_contrapartida", precision = 21, scale = 2)
//    private BigDecimal pcMinContrapartida;
//
//    @Size(max = 1024)
//    @Column(name = "nome_mandataria", length = 1024)
//    private String nomeMandataria;
//
//    @NotNull
//    @Size(max = 50)
//    @Column(name = "categoria", length = 50, nullable = false)
//    private String categoria;
//
//    @Size(max = 20)
//    @Column(name = "nivel_contrato", length = 20)
//    private String nivelContrato;
//
//    @NotNull
//    @Column(name = "spa_homologado", nullable = false)
//    private Boolean spaHomologado;
//
//    @NotNull
//    @Size(max = 50)
//    @Column(name = "apelido_empreendimento", length = 50, nullable = false)
//    private String apelidoEmpreendimento;
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
//    @OneToMany(mappedBy = "proposta")
//    private Set<Contrato> contratos = new HashSet<>();
//
//    @OneToMany(mappedBy = "proposta")
//    private Set<Licitacao> licitacaos = new HashSet<>();
//
//    @OneToMany(mappedBy = "proposta")
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
//    public Integer getIdSiconv() {
//        return idSiconv;
//    }
//
//    public Proposta idSiconv(Integer idSiconv) {
//        this.idSiconv = idSiconv;
//        return this;
//    }
//
//    public void setIdSiconv(Integer idSiconv) {
//        this.idSiconv = idSiconv;
//    }
//
//    public Integer getNumeroProposta() {
//        return numeroProposta;
//    }
//
//    public Proposta numeroProposta(Integer numeroProposta) {
//        this.numeroProposta = numeroProposta;
//        return this;
//    }
//
//    public void setNumeroProposta(Integer numeroProposta) {
//        this.numeroProposta = numeroProposta;
//    }
//
//    public Integer getAnoProposta() {
//        return anoProposta;
//    }
//
//    public Proposta anoProposta(Integer anoProposta) {
//        this.anoProposta = anoProposta;
//        return this;
//    }
//
//    public void setAnoProposta(Integer anoProposta) {
//        this.anoProposta = anoProposta;
//    }
//
//    public BigDecimal getValorGlobal() {
//        return valorGlobal;
//    }
//
//    public Proposta valorGlobal(BigDecimal valorGlobal) {
//        this.valorGlobal = valorGlobal;
//        return this;
//    }
//
//    public void setValorGlobal(BigDecimal valorGlobal) {
//        this.valorGlobal = valorGlobal;
//    }
//
//    public BigDecimal getValorRepasse() {
//        return valorRepasse;
//    }
//
//    public Proposta valorRepasse(BigDecimal valorRepasse) {
//        this.valorRepasse = valorRepasse;
//        return this;
//    }
//
//    public void setValorRepasse(BigDecimal valorRepasse) {
//        this.valorRepasse = valorRepasse;
//    }
//
//    public BigDecimal getValorContrapartida() {
//        return valorContrapartida;
//    }
//
//    public Proposta valorContrapartida(BigDecimal valorContrapartida) {
//        this.valorContrapartida = valorContrapartida;
//        return this;
//    }
//
//    public void setValorContrapartida(BigDecimal valorContrapartida) {
//        this.valorContrapartida = valorContrapartida;
//    }
//
//    public Integer getModalidade() {
//        return modalidade;
//    }
//
//    public Proposta modalidade(Integer modalidade) {
//        this.modalidade = modalidade;
//        return this;
//    }
//
//    public void setModalidade(Integer modalidade) {
//        this.modalidade = modalidade;
//    }
//
//    public String getNomeObjeto() {
//        return nomeObjeto;
//    }
//
//    public Proposta nomeObjeto(String nomeObjeto) {
//        this.nomeObjeto = nomeObjeto;
//        return this;
//    }
//
//    public void setNomeObjeto(String nomeObjeto) {
//        this.nomeObjeto = nomeObjeto;
//    }
//
//    public Integer getNumeroConvenio() {
//        return numeroConvenio;
//    }
//
//    public Proposta numeroConvenio(Integer numeroConvenio) {
//        this.numeroConvenio = numeroConvenio;
//        return this;
//    }
//
//    public void setNumeroConvenio(Integer numeroConvenio) {
//        this.numeroConvenio = numeroConvenio;
//    }
//
//    public Integer getAnoConvenio() {
//        return anoConvenio;
//    }
//
//    public Proposta anoConvenio(Integer anoConvenio) {
//        this.anoConvenio = anoConvenio;
//        return this;
//    }
//
//    public void setAnoConvenio(Integer anoConvenio) {
//        this.anoConvenio = anoConvenio;
//    }
//
//    public LocalDate getDataAssinaturaConvenio() {
//        return dataAssinaturaConvenio;
//    }
//
//    public Proposta dataAssinaturaConvenio(LocalDate dataAssinaturaConvenio) {
//        this.dataAssinaturaConvenio = dataAssinaturaConvenio;
//        return this;
//    }
//
//    public void setDataAssinaturaConvenio(LocalDate dataAssinaturaConvenio) {
//        this.dataAssinaturaConvenio = dataAssinaturaConvenio;
//    }
//
//    public String getCodigoPrograma() {
//        return codigoPrograma;
//    }
//
//    public Proposta codigoPrograma(String codigoPrograma) {
//        this.codigoPrograma = codigoPrograma;
//        return this;
//    }
//
//    public void setCodigoPrograma(String codigoPrograma) {
//        this.codigoPrograma = codigoPrograma;
//    }
//
//    public String getNomePrograma() {
//        return nomePrograma;
//    }
//
//    public Proposta nomePrograma(String nomePrograma) {
//        this.nomePrograma = nomePrograma;
//        return this;
//    }
//
//    public void setNomePrograma(String nomePrograma) {
//        this.nomePrograma = nomePrograma;
//    }
//
//    public String getIdentificacaoProponente() {
//        return identificacaoProponente;
//    }
//
//    public Proposta identificacaoProponente(String identificacaoProponente) {
//        this.identificacaoProponente = identificacaoProponente;
//        return this;
//    }
//
//    public void setIdentificacaoProponente(String identificacaoProponente) {
//        this.identificacaoProponente = identificacaoProponente;
//    }
//
//    public String getNomeProponente() {
//        return nomeProponente;
//    }
//
//    public Proposta nomeProponente(String nomeProponente) {
//        this.nomeProponente = nomeProponente;
//        return this;
//    }
//
//    public void setNomeProponente(String nomeProponente) {
//        this.nomeProponente = nomeProponente;
//    }
//
//    public String getUf() {
//        return uf;
//    }
//
//    public Proposta uf(String uf) {
//        this.uf = uf;
//        return this;
//    }
//
//    public void setUf(String uf) {
//        this.uf = uf;
//    }
//
//    public BigDecimal getPcMinContrapartida() {
//        return pcMinContrapartida;
//    }
//
//    public Proposta pcMinContrapartida(BigDecimal pcMinContrapartida) {
//        this.pcMinContrapartida = pcMinContrapartida;
//        return this;
//    }
//
//    public void setPcMinContrapartida(BigDecimal pcMinContrapartida) {
//        this.pcMinContrapartida = pcMinContrapartida;
//    }
//
//    public String getNomeMandataria() {
//        return nomeMandataria;
//    }
//
//    public Proposta nomeMandataria(String nomeMandataria) {
//        this.nomeMandataria = nomeMandataria;
//        return this;
//    }
//
//    public void setNomeMandataria(String nomeMandataria) {
//        this.nomeMandataria = nomeMandataria;
//    }
//
//    public String getCategoria() {
//        return categoria;
//    }
//
//    public Proposta categoria(String categoria) {
//        this.categoria = categoria;
//        return this;
//    }
//
//    public void setCategoria(String categoria) {
//        this.categoria = categoria;
//    }
//
//    public String getNivelContrato() {
//        return nivelContrato;
//    }
//
//    public Proposta nivelContrato(String nivelContrato) {
//        this.nivelContrato = nivelContrato;
//        return this;
//    }
//
//    public void setNivelContrato(String nivelContrato) {
//        this.nivelContrato = nivelContrato;
//    }
//
//    public Boolean isSpaHomologado() {
//        return spaHomologado;
//    }
//
//    public Proposta spaHomologado(Boolean spaHomologado) {
//        this.spaHomologado = spaHomologado;
//        return this;
//    }
//
//    public void setSpaHomologado(Boolean spaHomologado) {
//        this.spaHomologado = spaHomologado;
//    }
//
//    public String getApelidoEmpreendimento() {
//        return apelidoEmpreendimento;
//    }
//
//    public Proposta apelidoEmpreendimento(String apelidoEmpreendimento) {
//        this.apelidoEmpreendimento = apelidoEmpreendimento;
//        return this;
//    }
//
//    public void setApelidoEmpreendimento(String apelidoEmpreendimento) {
//        this.apelidoEmpreendimento = apelidoEmpreendimento;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Proposta versao(BigDecimal versao) {
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
//    public Proposta adtLogin(String adtLogin) {
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
//    public Proposta adtDataHora(LocalDate adtDataHora) {
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
//    public Proposta adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Set<Contrato> getContratos() {
//        return contratos;
//    }
//
//    public Proposta contratos(Set<Contrato> contratos) {
//        this.contratos = contratos;
//        return this;
//    }
//
//    public Proposta addContrato(Contrato contrato) {
//        this.contratos.add(contrato);
//        contrato.setProposta(this);
//        return this;
//    }
//
//    public Proposta removeContrato(Contrato contrato) {
//        this.contratos.remove(contrato);
//        contrato.setProposta(null);
//        return this;
//    }
//
//    public void setContratos(Set<Contrato> contratos) {
//        this.contratos = contratos;
//    }
//
//    public Set<Licitacao> getLicitacaos() {
//        return licitacaos;
//    }
//
//    public Proposta licitacaos(Set<Licitacao> licitacaos) {
//        this.licitacaos = licitacaos;
//        return this;
//    }
//
//    public Proposta addLicitacao(Licitacao licitacao) {
//        this.licitacaos.add(licitacao);
//        licitacao.setProposta(this);
//        return this;
//    }
//
//    public Proposta removeLicitacao(Licitacao licitacao) {
//        this.licitacaos.remove(licitacao);
//        licitacao.setProposta(null);
//        return this;
//    }
//
//    public void setLicitacaos(Set<Licitacao> licitacaos) {
//        this.licitacaos = licitacaos;
//    }
//
//    public Set<Submeta> getSubmetas() {
//        return submetas;
//    }
//
//    public Proposta submetas(Set<Submeta> submetas) {
//        this.submetas = submetas;
//        return this;
//    }
//
//    public Proposta addSubmeta(Submeta submeta) {
//        this.submetas.add(submeta);
//        submeta.setProposta(this);
//        return this;
//    }
//
//    public Proposta removeSubmeta(Submeta submeta) {
//        this.submetas.remove(submeta);
//        submeta.setProposta(null);
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
//        if (!(o instanceof Proposta)) {
//            return false;
//        }
//        return id != null && id.equals(((Proposta) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Proposta{" +
//            "id=" + getId() +
//            ", idSiconv=" + getIdSiconv() +
//            ", numeroProposta=" + getNumeroProposta() +
//            ", anoProposta=" + getAnoProposta() +
//            ", valorGlobal=" + getValorGlobal() +
//            ", valorRepasse=" + getValorRepasse() +
//            ", valorContrapartida=" + getValorContrapartida() +
//            ", modalidade=" + getModalidade() +
//            ", nomeObjeto='" + getNomeObjeto() + "'" +
//            ", numeroConvenio=" + getNumeroConvenio() +
//            ", anoConvenio=" + getAnoConvenio() +
//            ", dataAssinaturaConvenio='" + getDataAssinaturaConvenio() + "'" +
//            ", codigoPrograma='" + getCodigoPrograma() + "'" +
//            ", nomePrograma='" + getNomePrograma() + "'" +
//            ", identificacaoProponente='" + getIdentificacaoProponente() + "'" +
//            ", nomeProponente='" + getNomeProponente() + "'" +
//            ", uf='" + getUf() + "'" +
//            ", pcMinContrapartida=" + getPcMinContrapartida() +
//            ", nomeMandataria='" + getNomeMandataria() + "'" +
//            ", categoria='" + getCategoria() + "'" +
//            ", nivelContrato='" + getNivelContrato() + "'" +
//            ", spaHomologado='" + isSpaHomologado() + "'" +
//            ", apelidoEmpreendimento='" + getApelidoEmpreendimento() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
}
