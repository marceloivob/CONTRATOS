package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.DocComplementarBD;

public interface DocComplementarDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.doc_complementar (anexo_id, data_emissao, data_validade, numero_documento, orgao_emissor, tipo, tipo_manifesto_ambiental, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :anexoId,  :dataEmissao,  :dataValidade,  :numeroDocumento,  :orgaoEmissor,  :tipo,  :tipoManifestoAmbiental, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(DocComplementarBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    DocComplementarBD inserirDocComplementar(@BindFields DocComplementarBD docComplementar);

    @SqlUpdate("UPDATE contratos.doc_complementar SET anexo_id = :anexoId, data_emissao = :dataEmissao, data_validade = :dataValidade, numero_documento = :numeroDocumento, orgao_emissor = :orgaoEmissor, tipo = :tipo, tipo_manifesto_ambiental = :tipoManifestoAmbiental, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(DocComplementarBD.class)
    @GetGeneratedKeys
    DocComplementarBD alterarDocComplementar(@BindFields DocComplementarBD docComplementar);
    
    @SqlUpdate("DELETE FROM contratos.doc_complementar WHERE id = :id")
    @DefineUserInSession
    void excluirDocComplementar(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.doc_complementar WHERE id = :id")
    @RegisterFieldMapper(DocComplementarBD.class)
    Optional<DocComplementarBD> recuperarDocComplementarPorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.doc_complementar")
    @RegisterFieldMapper(DocComplementarBD.class)
    List<DocComplementarBD> recuperarTodosDocComplementar();
    
    
    @SqlBatch("INSERT INTO contratos.doc_complementar (anexo_id, data_emissao, data_validade, numero_documento, orgao_emissor, tipo, tipo_manifesto_ambiental, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :anexoId,  :dataEmissao,  :dataValidade,  :numeroDocumento,  :orgaoEmissor,  :tipo,  :tipoManifestoAmbiental, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(DocComplementarBD.class)
    void inserirDocComplementarBatch (@BindBean Collection<DocComplementarBD> docComplementars); 
    
}
