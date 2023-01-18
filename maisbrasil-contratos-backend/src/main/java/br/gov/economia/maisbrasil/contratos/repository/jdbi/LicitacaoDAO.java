package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.time.Instant;
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
import br.gov.economia.maisbrasil.contratos.domain.bd.LicitacaoBD;

public interface LicitacaoDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.licitacao (data_homologacao, data_publicacao, id_licitacao_fk, in_situacao, modalidade, numero_ano, objeto, processo_de_execucao, proposta_id, regime_contratacao, status_processo, valor_processo, versao, versao_nr, adt_login, adt_data_hora, adt_operacao, numero_processo, situacao_aceite_processo_execu) VALUES (  :dataHomologacao,  :dataPublicacao,  :idLicitacaoFk,  :inSituacao,  :modalidade,  :numeroAno,  :objeto,  :processoDeExecucao,  :propostaId,  :regimeContratacao,  :statusProcesso,  :valorProcesso, :versao, :versaoNr, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT', :numeroProcesso, :situacaoAceiteProcessoExecucao)")
    @RegisterFieldMapper(LicitacaoBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    LicitacaoBD inserirLicitacao(@BindBean LicitacaoBD licitacao);

    @SqlUpdate("UPDATE contratos.licitacao SET data_homologacao = :dataHomologacao, data_publicacao = :dataPublicacao, id_licitacao_fk = :idLicitacaoFk, in_situacao = :inSituacao, modalidade = :modalidade, numero_ano = :numeroAno, objeto = :objeto, processo_de_execucao = :processoDeExecucao, proposta_id = :propostaId, regime_contratacao = :regimeContratacao, status_processo = :statusProcesso, valor_processo = :valorProcesso, versao_nr = :versaoNr, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(LicitacaoBD.class)
    @GetGeneratedKeys
    LicitacaoBD alterarLicitacao(@BindBean LicitacaoBD licitacao);
    
    @SqlUpdate("DELETE FROM contratos.licitacao WHERE id = :id")
    @DefineUserInSession
    void excluirLicitacao(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.licitacao WHERE id = :id")
    @RegisterFieldMapper(LicitacaoBD.class)
    Optional<LicitacaoBD> recuperarLicitacaoPorId(@Bind ("id") Long id);
    
    
    @SqlQuery("SELECT * FROM contratos.licitacao")
    @RegisterFieldMapper(LicitacaoBD.class)
    List<LicitacaoBD> recuperarTodosLicitacao();

    
    @SqlQuery("select lic.* from contratos.licitacao lic, contratos.lote l where lic.id = l.licitacao_id and l.fornecedor_id = :idFornecedor and lic.proposta_id = (SELECT prop.id FROM contratos.proposta prop WHERE prop.id_siconv = :idProposta) order by lic.numero_ano")
    @RegisterFieldMapper(LicitacaoBD.class)
    List<LicitacaoBD> recuperarLicitacoesDaPropostaPorIdFornecedor(@Bind ("idFornecedor") Long idFornecedor, @Bind ("idProposta") Long idProposta);
    
    @SqlBatch("INSERT INTO contratos.licitacao (data_homologacao, data_publicacao, id_licitacao_fk, in_situacao, modalidade, numero_ano, objeto, processo_de_execucao, proposta_id, regime_contratacao, status_processo, valor_processo, versao, versao_nr, adt_login, adt_data_hora, adt_operacao, numero_processo, situacao_aceite_processo_execu) VALUES (  :dataHomologacao,  :dataPublicacao,  :idLicitacaoFk,  :inSituacao,  :modalidade,  :numeroAno,  :objeto,  :processoDeExecucao,  :propostaId,  :regimeContratacao,  :statusProcesso,  :valorProcesso, :versao, :versaoNr, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT', :numeroProcesso, :situacaoAceiteProcessoExecucao)")
    @DefineUserInSession
    @RegisterFieldMapper(LicitacaoBD.class)
    void inserirLicitacaoBatch (@BindBean Collection<LicitacaoBD> licitacaos);
    
    @SqlQuery("SELECT * FROM contratos.licitacao WHERE id_licitacao_fk = :licitacaoFK")
    @RegisterFieldMapper(LicitacaoBD.class)
    Optional<LicitacaoBD> recuperarLicitacaoPorFK(@Bind ("licitacaoFK") Long licitacaoFK);
    
    @SqlQuery("SELECT * FROM contratos.licitacao where proposta_id = :idProposta")
    @RegisterFieldMapper(LicitacaoBD.class)
    List<LicitacaoBD> recuperarLicitacoesPorProposta(@Bind ("idProposta") Long idProposta);

    
    @SqlQuery("SELECT * FROM contratos.licitacao where proposta_id = (SELECT prop.id FROM contratos.proposta prop WHERE prop.id_siconv = :idProposta)")
    @RegisterFieldMapper(LicitacaoBD.class)
    List<LicitacaoBD> recuperarLicitacoesPorPropostaIdSiconv(@Bind ("idProposta") Long idProposta);
    
    @SqlQuery("select max(adt_data_hora) from contratos.licitacao_log_rec where entity_id = :idLicitacao")
    Instant recuperarDataImportacaoDaLicitacao(@Bind ("idLicitacao") Long idLicitacao);
    

}
