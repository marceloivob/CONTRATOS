package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.HistoricoBD;

public interface HistoricoDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.historico (contrato_id, termo_aditivo_id, cpf_responsavel, data_registro, evento_gerador, nome_responsavel, situacao_contrato, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :contratoId, :termoAditivoId, :cpfResponsavel, LOCALTIMESTAMP,  :eventoGerador,  :nomeResponsavel,  :situacaoContrato, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(HistoricoBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    HistoricoBD inserirHistorico(@BindBean HistoricoBD historico);

    @SqlUpdate("UPDATE contratos.historico SET contrato_id = :contratoId, termo_aditivo_id = :termoAditivoId, cpf_responsavel = :cpfResponsavel, data_registro = :dataRegistro, evento_gerador = :eventoGerador, nome_responsavel = :nomeResponsavel, situacao_contrato = :situacaoContrato, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(HistoricoBD.class)
    @GetGeneratedKeys
    HistoricoBD alterarHistorico(@BindBean HistoricoBD historico);
    
    @SqlUpdate("DELETE FROM contratos.historico WHERE id = :id")
    @DefineUserInSession
    void excluirHistorico(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.historico WHERE id = :id")
    @RegisterFieldMapper(HistoricoBD.class)
    Optional<HistoricoBD> recuperarHistoricoPorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.historico")
    @RegisterFieldMapper(HistoricoBD.class)
    List<HistoricoBD> recuperarTodosHistorico();
    
    
    @SqlBatch("INSERT INTO contratos.historico (contrato_id, cpf_responsavel, data_registro, evento_gerador, nome_responsavel, situacao_contrato, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :contratoId,  :cpfResponsavel,  :dataRegistro,  :eventoGerador,  :nomeResponsavel,  :situacaoContrato, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(HistoricoBD.class)
    void inserirHistoricoBatch (@BindBean Collection<HistoricoBD> historicos); 
    
    @SqlUpdate("DELETE FROM contratos.historico WHERE contrato_id = :idContrato")
    @DefineUserInSession
    void excluirHistoricoDoContrato(@Bind ("idContrato") Long idContrato);
    
    @SqlQuery("SELECT * FROM contratos.historico WHERE contrato_id = :idContrato order by id desc")
    @RegisterFieldMapper(HistoricoBD.class)
    List<HistoricoBD> recuperarHistoricoDoContrato(@Bind ("idContrato") Long idContrato);

    @SqlQuery("select * from contratos.historico where contrato_id = :idContrato and termo_aditivo_id != :idTermoAditivo and termo_aditivo_id not in (select id from contratos.termo_aditivo ta where ta.in_situacao = 'EXC') and evento_gerador = 'ADC' order by data_registro desc")
    @RegisterFieldMapper(HistoricoBD.class)
    List<HistoricoBD> recuperarHistoricoDeConclusaoDeOutrosTermosAditivosDoContrato(@Bind ("idContrato") Long idContrato, @Bind ("idTermoAditivo") Long idTermoAditivo);
    
}
