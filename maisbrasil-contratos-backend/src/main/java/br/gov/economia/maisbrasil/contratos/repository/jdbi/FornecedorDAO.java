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
import br.gov.economia.maisbrasil.contratos.domain.bd.FornecedorBD;

public interface FornecedorDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.fornecedor (identificacao, razao_social, tipo_identificacao, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :identificacao,  :razaoSocial,  :tipoIdentificacao, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(FornecedorBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    FornecedorBD inserirFornecedor(@BindFields FornecedorBD fornecedor);

    @SqlUpdate("UPDATE contratos.fornecedor SET identificacao = :identificacao, razao_social = :razaoSocial, tipo_identificacao = :tipoIdentificacao, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(FornecedorBD.class)
    @GetGeneratedKeys
    FornecedorBD alterarFornecedor(@BindFields FornecedorBD fornecedor);
    
    @SqlUpdate("DELETE FROM contratos.fornecedor WHERE id = :id")
    @DefineUserInSession
    void excluirFornecedor(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.fornecedor WHERE id = :id")
    @RegisterFieldMapper(FornecedorBD.class)
    Optional<FornecedorBD> recuperarFornecedorPorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.fornecedor")
    @RegisterFieldMapper(FornecedorBD.class)
    List<FornecedorBD> recuperarTodosFornecedor();
    

    @SqlQuery("SELECT DISTINCT f.* FROM contratos.fornecedor f, contratos.licitacao lc, contratos.lote lt where lt.fornecedor_id = f.id and lt.licitacao_id = lc.id and lc.proposta_id = (SELECT prop.id FROM contratos.proposta prop WHERE prop.id_siconv = :idProposta) order by f.tipo_identificacao, f.identificacao")
    @RegisterFieldMapper(FornecedorBD.class)
    List<FornecedorBD> recuperarFornecedoresPorProposta(@Bind ("idProposta") Long idProposta);

    
    @SqlBatch("INSERT INTO contratos.fornecedor (identificacao, razao_social, tipo_identificacao, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :identificacao,  :razaoSocial,  :tipoIdentificacao, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(FornecedorBD.class)
    void inserirFornecedorBatch (@BindBean Collection<FornecedorBD> fornecedors);
    
    @SqlQuery("SELECT * FROM contratos.fornecedor WHERE identificacao = :identificacao")
    @RegisterFieldMapper(FornecedorBD.class)
    List<FornecedorBD> recuperarFornecedorPorIdentificacao(@Bind ("identificacao") String identificacao);
    
}
