package br.gov.economia.maisbrasil.contratos.domain;

import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//import org.springframework.data.annotation.Id;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
///**
// * A Anexo.
// */
//@Entity
//@Table(name = "anexo")
public class Anexo implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @NotNull
//    @Size(max = 30)
//    @Column(name = "tx_descricao", length = 30, nullable = false)
//    private String txDescricao;
//
//    @NotNull
//    @Size(max = 30)
//    @Column(name = "in_tipo_anexo", length = 30, nullable = false)
//    private String inTipoAnexo;
//
//    @NotNull
//    @Column(name = "dt_upload", nullable = false)
//    private LocalDate dtUpload;
//
//    @NotNull
//    @Size(max = 11)
//    @Column(name = "id_cpf_enviado_por", length = 11, nullable = false)
//    private String idCpfEnviadoPor;
//
//    @NotNull
//    @Size(max = 60)
//    @Column(name = "nome_enviado_por", length = 60, nullable = false)
//    private String nomeEnviadoPor;
//
//    @NotNull
//    @Size(max = 10)
//    @Column(name = "in_perfil_usuario", length = 10, nullable = false)
//    private String inPerfilUsuario;
//
//    @NotNull
//    @Size(max = 100)
//    @Column(name = "nm_arquivo", length = 100, nullable = false)
//    private String nmArquivo;
//
//    @NotNull
//    @Size(max = 1024)
//    @Column(name = "caminho", length = 1024, nullable = false)
//    private String caminho;
//
//    @NotNull
//    @Size(max = 25)
//    @Column(name = "bucket", length = 25, nullable = false)
//    private String bucket;
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
//    @OneToMany(mappedBy = "anexo")
//    private Set<DocComplementar> docComplementars = new HashSet<>();
//
//    @ManyToOne
//    @JsonIgnoreProperties("anexos")
//    private Contrato contrato;
//
//    @ManyToOne
//    @JsonIgnoreProperties("anexos")
//    private TermoAditivo termoAditivo;
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
//    public String getTxDescricao() {
//        return txDescricao;
//    }
//
//    public Anexo txDescricao(String txDescricao) {
//        this.txDescricao = txDescricao;
//        return this;
//    }
//
//    public void setTxDescricao(String txDescricao) {
//        this.txDescricao = txDescricao;
//    }
//
//    public String getInTipoAnexo() {
//        return inTipoAnexo;
//    }
//
//    public Anexo inTipoAnexo(String inTipoAnexo) {
//        this.inTipoAnexo = inTipoAnexo;
//        return this;
//    }
//
//    public void setInTipoAnexo(String inTipoAnexo) {
//        this.inTipoAnexo = inTipoAnexo;
//    }
//
//    public LocalDate getDtUpload() {
//        return dtUpload;
//    }
//
//    public Anexo dtUpload(LocalDate dtUpload) {
//        this.dtUpload = dtUpload;
//        return this;
//    }
//
//    public void setDtUpload(LocalDate dtUpload) {
//        this.dtUpload = dtUpload;
//    }
//
//    public String getIdCpfEnviadoPor() {
//        return idCpfEnviadoPor;
//    }
//
//    public Anexo idCpfEnviadoPor(String idCpfEnviadoPor) {
//        this.idCpfEnviadoPor = idCpfEnviadoPor;
//        return this;
//    }
//
//    public void setIdCpfEnviadoPor(String idCpfEnviadoPor) {
//        this.idCpfEnviadoPor = idCpfEnviadoPor;
//    }
//
//    public String getNomeEnviadoPor() {
//        return nomeEnviadoPor;
//    }
//
//    public Anexo nomeEnviadoPor(String nomeEnviadoPor) {
//        this.nomeEnviadoPor = nomeEnviadoPor;
//        return this;
//    }
//
//    public void setNomeEnviadoPor(String nomeEnviadoPor) {
//        this.nomeEnviadoPor = nomeEnviadoPor;
//    }
//
//    public String getInPerfilUsuario() {
//        return inPerfilUsuario;
//    }
//
//    public Anexo inPerfilUsuario(String inPerfilUsuario) {
//        this.inPerfilUsuario = inPerfilUsuario;
//        return this;
//    }
//
//    public void setInPerfilUsuario(String inPerfilUsuario) {
//        this.inPerfilUsuario = inPerfilUsuario;
//    }
//
//    public String getNmArquivo() {
//        return nmArquivo;
//    }
//
//    public Anexo nmArquivo(String nmArquivo) {
//        this.nmArquivo = nmArquivo;
//        return this;
//    }
//
//    public void setNmArquivo(String nmArquivo) {
//        this.nmArquivo = nmArquivo;
//    }
//
//    public String getCaminho() {
//        return caminho;
//    }
//
//    public Anexo caminho(String caminho) {
//        this.caminho = caminho;
//        return this;
//    }
//
//    public void setCaminho(String caminho) {
//        this.caminho = caminho;
//    }
//
//    public String getBucket() {
//        return bucket;
//    }
//
//    public Anexo bucket(String bucket) {
//        this.bucket = bucket;
//        return this;
//    }
//
//    public void setBucket(String bucket) {
//        this.bucket = bucket;
//    }
//
//    public BigDecimal getVersao() {
//        return versao;
//    }
//
//    public Anexo versao(BigDecimal versao) {
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
//    public Anexo adtLogin(String adtLogin) {
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
//    public Anexo adtDataHora(LocalDate adtDataHora) {
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
//    public Anexo adtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//        return this;
//    }
//
//    public void setAdtOperacao(String adtOperacao) {
//        this.adtOperacao = adtOperacao;
//    }
//
//    public Set<DocComplementar> getDocComplementars() {
//        return docComplementars;
//    }
//
//    public Anexo docComplementars(Set<DocComplementar> docComplementars) {
//        this.docComplementars = docComplementars;
//        return this;
//    }
//
//    public Anexo addDocComplementar(DocComplementar docComplementar) {
//        this.docComplementars.add(docComplementar);
//        docComplementar.setAnexo(this);
//        return this;
//    }
//
//    public Anexo removeDocComplementar(DocComplementar docComplementar) {
//        this.docComplementars.remove(docComplementar);
//        docComplementar.setAnexo(null);
//        return this;
//    }
//
//    public void setDocComplementars(Set<DocComplementar> docComplementars) {
//        this.docComplementars = docComplementars;
//    }
//
//    public Contrato getContrato() {
//        return contrato;
//    }
//
//    public Anexo contrato(Contrato contrato) {
//        this.contrato = contrato;
//        return this;
//    }
//
//    public void setContrato(Contrato contrato) {
//        this.contrato = contrato;
//    }
//
//    public TermoAditivo getTermoAditivo() {
//        return termoAditivo;
//    }
//
//    public Anexo termoAditivo(TermoAditivo termoAditivo) {
//        this.termoAditivo = termoAditivo;
//        return this;
//    }
//
//    public void setTermoAditivo(TermoAditivo termoAditivo) {
//        this.termoAditivo = termoAditivo;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Anexo)) {
//            return false;
//        }
//        return id != null && id.equals(((Anexo) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "Anexo{" +
//            "id=" + getId() +
//            ", txDescricao='" + getTxDescricao() + "'" +
//            ", inTipoAnexo='" + getInTipoAnexo() + "'" +
//            ", dtUpload='" + getDtUpload() + "'" +
//            ", idCpfEnviadoPor='" + getIdCpfEnviadoPor() + "'" +
//            ", nomeEnviadoPor='" + getNomeEnviadoPor() + "'" +
//            ", inPerfilUsuario='" + getInPerfilUsuario() + "'" +
//            ", nmArquivo='" + getNmArquivo() + "'" +
//            ", caminho='" + getCaminho() + "'" +
//            ", bucket='" + getBucket() + "'" +
//            ", versao=" + getVersao() +
//            ", adtLogin='" + getAdtLogin() + "'" +
//            ", adtDataHora='" + getAdtDataHora() + "'" +
//            ", adtOperacao='" + getAdtOperacao() + "'" +
//            "}";
//    }
//
//
}
