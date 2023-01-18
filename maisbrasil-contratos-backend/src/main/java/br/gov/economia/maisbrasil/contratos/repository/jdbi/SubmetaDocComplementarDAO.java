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
import org.springframework.stereotype.Repository;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaDocComplementarBD;

public interface SubmetaDocComplementarDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.submeta_doc_complementar (data_associacao, doc_complementar_id, submeta_id, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :dataAssociacao,  :docComplementarId,  :submetaId, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(SubmetaDocComplementarBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    SubmetaDocComplementarBD inserirSubmetaDocComplementar(@BindFields SubmetaDocComplementarBD submetaDocComplementar);

    @SqlUpdate("UPDATE contratos.submeta_doc_complementar SET data_associacao = :dataAssociacao, doc_complementar_id = :docComplementarId, submeta_id = :submetaId, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(SubmetaDocComplementarBD.class)
    @GetGeneratedKeys
    SubmetaDocComplementarBD alterarSubmetaDocComplementar(@BindFields SubmetaDocComplementarBD submetaDocComplementar);
    
    @SqlUpdate("DELETE FROM contratos.submeta_doc_complementar WHERE id = :id")
    @DefineUserInSession
    void excluirSubmetaDocComplementar(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.submeta_doc_complementar WHERE id = :id")
    @RegisterFieldMapper(SubmetaDocComplementarBD.class)
    Optional<SubmetaDocComplementarBD> recuperarSubmetaDocComplementarPorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.submeta_doc_complementar")
    @RegisterFieldMapper(SubmetaDocComplementarBD.class)
    List<SubmetaDocComplementarBD> recuperarTodosSubmetaDocComplementar();
    
    
    @SqlBatch("INSERT INTO contratos.submeta_doc_complementar (data_associacao, doc_complementar_id, submeta_id, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :dataAssociacao,  :docComplementarId,  :submetaId, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(SubmetaDocComplementarBD.class)
    void inserirSubmetaDocComplementarBatch (@BindBean Collection<SubmetaDocComplementarBD> submetaDocComplementars); 
    
}
