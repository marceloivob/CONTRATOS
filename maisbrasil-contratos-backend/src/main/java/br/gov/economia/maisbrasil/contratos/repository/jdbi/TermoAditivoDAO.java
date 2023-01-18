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
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.bd.TermoAditivoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;
import br.gov.economia.maisbrasil.contratos.domain.dto.TermoAditivoDTO;

public interface TermoAditivoDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.termo_aditivo (contrato_id, dt_assinatura, dt_publicacao, in_alteracao_clausula, in_alteracao_objeto, in_alteracao_vigencia, in_alteracao_supressao_acrescimo, in_situacao, tx_justificativa, dt_nova_data_fim_vigencia, numero, vl_supressao_acrescimo, tx_descricao_ampliacao_objeto, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :contratoId,  :dtAssinatura,  :dtPublicacao,  :inAlteracaoClausula,  :inAlteracaoObjeto,  :inAlteracaoVigencia,  :inAlteracaoSupressaoAcrescimo,  :inSituacao, :txJustificativa,  :dtNovaDataFimVigencia,  :numero,  :vlSupressaoAcrescimo,  :txDescricaoAmpliacaoObjeto, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(TermoAditivoBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    TermoAditivoBD inserirTermoAditivo(@BindBean TermoAditivoBD termoAditivo);

    @SqlUpdate("UPDATE contratos.termo_aditivo SET contrato_id = :contratoId, dt_assinatura = :dtAssinatura, dt_publicacao = :dtPublicacao, in_alteracao_clausula = :inAlteracaoClausula, in_alteracao_objeto = :inAlteracaoObjeto, in_alteracao_vigencia = :inAlteracaoVigencia, in_alteracao_supressao_acrescimo = :inAlteracaoSupressaoAcrescimo, tx_justificativa = :txJustificativa,  in_situacao = :inSituacao, dt_nova_data_fim_vigencia = :dtNovaDataFimVigencia, numero = :numero, vl_supressao_acrescimo = :vlSupressaoAcrescimo, tx_descricao_ampliacao_objeto = :txDescricaoAmpliacaoObjeto, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(TermoAditivoBD.class)
    @GetGeneratedKeys
    TermoAditivoBD alterarTermoAditivo(@BindBean TermoAditivoBD termoAditivo);
    
    @SqlUpdate("DELETE FROM contratos.termo_aditivo WHERE id = :id")
    @DefineUserInSession
    void excluirTermoAditivo(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.termo_aditivo WHERE id = :id")
    @RegisterFieldMapper(TermoAditivoBD.class)
    Optional<TermoAditivoBD> recuperarTermoAditivoPorId(@Bind ("id") Long id);

    @SqlQuery("SELECT * FROM contratos.termo_aditivo WHERE contrato_id = :contratoId AND in_situacao != 'EXC' order by id desc")
    @RegisterFieldMapper(TermoAditivoBD.class)
    List<TermoAditivoBD> recuperarTermoAditivoPorContratoId(@Bind ("contratoId") Long contratoId);

    @SqlQuery("SELECT * FROM contratos.termo_aditivo")
    @RegisterFieldMapper(TermoAditivoBD.class)
    List<TermoAditivoBD> recuperarTodosTermoAditivo();
    
    @SqlBatch("INSERT INTO contratos.termo_aditivo (contrato_id, dt_assinatura, dt_publicacao, in_alteracao_clausula, in_alteracao_objeto, in_alteracao_vigencia, in_alteracao_supressao_acrescimo, tx_justificativa, dt_nova_data_fim_vigencia, numero, vl_supressao_acrescimo, tx_descricao_ampliacao_objeto, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :contratoId,  :dtAssinatura,  :dtPublicacao,  :inAlteracaoClausula,  :inAlteracaoObjeto,  :inAlteracaoVigencia,  :inAlteracaoSupressaoAcrescimo,  :txJustificativa,  :dtNovaDataFimVigencia,  :numero,  :vlSupressaoAcrescimo,  :txDescricaoAmpliacaoObjeto, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(TermoAditivoBD.class)
    void inserirTermoAditivoBatch (@BindBean Collection<TermoAditivoBD> termoAditivos); 

    @SqlUpdate("UPDATE contratos.termo_aditivo SET in_situacao = 'EXC', versao = (versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE contrato_id = :contratoId")
    @DefineUserInSession
    @RegisterFieldMapper(TermoAditivoBD.class)
    @GetGeneratedKeys
    TermoAditivoBD excluirTermosAditivosPorContratoId(@Bind ("contratoId") Long contratoId);
    
	@SqlQuery("select exists (select 1 from contratos.termo_aditivo ta where ta.numero = :numero and ta.in_situacao != 'EXC' and ta.contrato_id = :contratoId)")
	boolean seExisteTermoAditivoComONumero(@BindBean TermoAditivoDTO termoAditivo);
	
	@SqlQuery("select exists (select 1 from contratos.termo_aditivo ta where ta.numero = :numero and ta.in_situacao != 'EXC' and ta.id != :id and ta.contrato_id = :contratoId)")
	@RegisterFieldMapper(TermoAditivoBD.class)
	boolean seExisteTermoAditivoComONumeroDiferenteDoId(@BindBean TermoAditivoBD termoAditivo);

}
