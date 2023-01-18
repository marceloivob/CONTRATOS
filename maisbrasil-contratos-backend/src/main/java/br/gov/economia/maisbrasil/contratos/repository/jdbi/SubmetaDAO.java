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
import br.gov.economia.maisbrasil.contratos.domain.bd.SubmetaBD;

public interface SubmetaDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.submeta (id_submeta_vrpl, descricao, in_regime_execucao, in_situacao, lote_id, meta_id, numero, proposta_id, vl_contrapartida, vl_outros, vl_repasse, vl_total_licitado, versao, adt_login, adt_data_hora, adt_operacao) VALUES ( :idSubmetaVrpl, :descricao, :inRegimeExecucao,  :inSituacao,  :loteId,  :metaId,  :numero,  :propostaId,  :vlContrapartida,  :vlOutros,  :vlRepasse,  :vlTotalLicitado, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(SubmetaBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    SubmetaBD inserirSubmeta(@BindBean SubmetaBD submeta);

    @SqlUpdate("UPDATE contratos.submeta SET id_submeta_vrpl = :idSubmetaVrpl, descricao = :descricao, in_regime_execucao = :inRegimeExecucao, in_situacao = :inSituacao, lote_id = :loteId, meta_id = :metaId, numero = :numero, proposta_id = :propostaId, vl_contrapartida = :vlContrapartida, vl_outros = :vlOutros, vl_repasse = :vlRepasse, vl_total_licitado = :vlTotalLicitado, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(SubmetaBD.class)
    @GetGeneratedKeys
    SubmetaBD alterarSubmeta(@BindBean SubmetaBD submeta);
    
    @SqlUpdate("DELETE FROM contratos.submeta WHERE id = :id")
    @DefineUserInSession
    void excluirSubmeta(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.submeta WHERE id = :id")
    @RegisterFieldMapper(SubmetaBD.class)
    Optional<SubmetaBD> recuperarSubmetaPorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.submeta")
    @RegisterFieldMapper(SubmetaBD.class)
    List<SubmetaBD> recuperarTodosSubmeta();
    
    
    @SqlBatch("INSERT INTO contratos.submeta (id_submeta_vrpl, descricao, in_regime_execucao, in_situacao, lote_id, meta_id, numero, proposta_id, vl_contrapartida, vl_outros, vl_repasse, vl_total_licitado, versao, adt_login, adt_data_hora, adt_operacao) VALUES ( :idSubmetaVrpl, :descricao, :inRegimeExecucao,  :inSituacao,  :loteId,  :metaId,  :numero,  :propostaId,  :vlContrapartida,  :vlOutros,  :vlRepasse,  :vlTotalLicitado, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(SubmetaBD.class)
    void inserirSubmetaBatch (@BindBean Collection<SubmetaBD> submetas);
    
    @SqlQuery("SELECT * FROM contratos.submeta WHERE lote_id = :idLote order by numero asc")
    @RegisterFieldMapper(SubmetaBD.class)
    List<SubmetaBD> recuperarSubmetasDoLote(@Bind("idLote") Long idLote);
    
}
