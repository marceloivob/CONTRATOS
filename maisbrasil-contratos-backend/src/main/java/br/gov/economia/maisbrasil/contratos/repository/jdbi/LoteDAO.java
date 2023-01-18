package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.LoteBD;

public interface LoteDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.lote (contrato_id, fornecedor_id, licitacao_id, numero, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :contratoId,  :fornecedorId,  :licitacaoId,  :numero, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(LoteBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    LoteBD inserirLote(@BindBean LoteBD lote);

    @SqlUpdate("UPDATE contratos.lote SET contrato_id = :contratoId, fornecedor_id = :fornecedorId, licitacao_id = :licitacaoId, numero = :numero, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(LoteBD.class)
    @GetGeneratedKeys
    LoteBD alterarLote(@BindBean LoteBD lote);
    
    @SqlUpdate("DELETE FROM contratos.lote WHERE id = :id")
    @DefineUserInSession
    void excluirLote(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.lote WHERE id = :id")
    @RegisterFieldMapper(LoteBD.class)
    Optional<LoteBD> recuperarLotePorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.lote")
    @RegisterFieldMapper(LoteBD.class)
    List<LoteBD> recuperarTodosLote();

    @SqlQuery("select l.* from contratos.lote l where l.licitacao_id = :idLicitacao and l.fornecedor_id = :idFornecedor")
    @RegisterFieldMapper(LoteBD.class)
    List<LoteBD> recuperarLotesPorIdFornecedorEIdLicitacao(@Bind ("idFornecedor") Long idFornecedor, @Bind ("idLicitacao") Long idLicitacao);

    @SqlBatch("INSERT INTO contratos.lote (contrato_id, fornecedor_id, licitacao_id, numero, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :contratoId,  :fornecedorId,  :licitacaoId,  :numero, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(LoteBD.class)
    void inserirLoteBatch (@BindBean Collection<LoteBD> lotes); 
    
    @SqlQuery("SELECT * FROM contratos.lote WHERE licitacao_id = :idLicitacao")
    @RegisterFieldMapper(LoteBD.class)
    List<LoteBD> recuperarLotesDaLicitacao(@Bind("idLicitacao") Long idLicitacao);
    
    @SqlQuery("SELECT * FROM contratos.lote WHERE contrato_id = :idContrato")
    @RegisterFieldMapper(LoteBD.class)
    List<LoteBD> recuperarLotesDoContrato(@Bind("idContrato") Long idContrato);
    
    @SqlUpdate("UPDATE contratos.lote SET contrato_id = null WHERE contrato_id = :idContrato")
    @DefineUserInSession
    void removerLotesDoContrato(@Bind("idContrato") Long idContrato);
    
    @SqlUpdate("UPDATE contratos.lote SET contrato_id = :idContrato WHERE id IN (<ids>)")
    @DefineUserInSession
    void associarLotesAoContrato (@BindList("ids") List<Long> ids, @Bind("idContrato") Long idContrato); 

    @SqlQuery("SELECT lt.* FROM contratos.lote lt, contratos.licitacao lc WHERE lt.licitacao_id = lc.id AND lc.proposta_id = (SELECT prop.id FROM contratos.proposta prop WHERE prop.id_siconv = :idProposta) ")
    @RegisterFieldMapper(LoteBD.class)
    List<LoteBD> recuperarTodosLotesDaProposta(@Bind("idProposta") Long idProposta);

}
