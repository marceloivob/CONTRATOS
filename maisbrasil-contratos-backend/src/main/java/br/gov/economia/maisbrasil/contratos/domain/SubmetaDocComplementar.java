package br.gov.economia.maisbrasil.contratos.domain;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
import java.io.Serializable;
//import java.time.LocalDate;
//
///**
// * A SubmetaDocComplementar.
// */
//@Entity
//@Table(name = "submeta_doc_complementar")
public class SubmetaDocComplementar implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
//    private Long id;
//
//    @Column(name = "data_associacao")
//    private LocalDate dataAssociacao;
//
//    @ManyToOne
//    @JsonIgnoreProperties("submetaDocComplementars")
//    private Submeta submeta;
//
//    @ManyToOne
//    @JsonIgnoreProperties("submetaDocComplementars")
//    private DocComplementar docComplementar;
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
//    public LocalDate getDataAssociacao() {
//        return dataAssociacao;
//    }
//
//    public SubmetaDocComplementar dataAssociacao(LocalDate dataAssociacao) {
//        this.dataAssociacao = dataAssociacao;
//        return this;
//    }
//
//    public void setDataAssociacao(LocalDate dataAssociacao) {
//        this.dataAssociacao = dataAssociacao;
//    }
//
//    public Submeta getSubmeta() {
//        return submeta;
//    }
//
//    public SubmetaDocComplementar submeta(Submeta submeta) {
//        this.submeta = submeta;
//        return this;
//    }
//
//    public void setSubmeta(Submeta submeta) {
//        this.submeta = submeta;
//    }
//
//    public DocComplementar getDocComplementar() {
//        return docComplementar;
//    }
//
//    public SubmetaDocComplementar docComplementar(DocComplementar docComplementar) {
//        this.docComplementar = docComplementar;
//        return this;
//    }
//
//    public void setDocComplementar(DocComplementar docComplementar) {
//        this.docComplementar = docComplementar;
//    }
//    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof SubmetaDocComplementar)) {
//            return false;
//        }
//        return id != null && id.equals(((SubmetaDocComplementar) o).id);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31;
//    }
//
//    @Override
//    public String toString() {
//        return "SubmetaDocComplementar{" +
//            "id=" + getId() +
//            ", dataAssociacao='" + getDataAssociacao() + "'" +
//            "}";
//    }
}
