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
import br.gov.economia.maisbrasil.contratos.domain.bd.ContratoBD;
import br.gov.economia.maisbrasil.contratos.domain.dto.ContratoDTO;


public interface ContratoDAO {
    
   
    @SqlUpdate("INSERT INTO contratos.contrato (data_assinatura, data_publicacao, fim_vigencia, dt_fim_vigencia_original, in_situacao, inicio_vigencia, numero, objeto, proposta_id, valor_total, versao, adt_data_hora, adt_operacao, adt_login) VALUES (  :dataAssinatura,  :dataPublicacao,  :fimVigencia, :dtFimVigenciaOriginal,  :inSituacao,  :inicioVigencia,  :numero,  :objeto,  (SELECT prop.id FROM contratos.proposta prop WHERE prop.id_siconv = :propostaIdSiconv),  :valorTotal, 1, LOCALTIMESTAMP, 'INSERT', current_setting('contratos.cpf_usuario'))")
    @RegisterFieldMapper(ContratoBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    ContratoBD inserirContrato(@BindBean ContratoBD contrato);

    @SqlUpdate("UPDATE contratos.contrato SET data_assinatura = :dataAssinatura, data_publicacao = :dataPublicacao, fim_vigencia = :fimVigencia, dt_fim_vigencia_original = :dtFimVigenciaOriginal, in_situacao = :inSituacao, inicio_vigencia = :inicioVigencia, numero = :numero, objeto = :objeto, proposta_id = :propostaId, valor_total = :valorTotal, versao = (:versao + 1),  adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE', adt_login = current_setting('contratos.cpf_usuario') WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(ContratoBD.class)
	@GetGeneratedKeys
    ContratoBD alterarContrato(@BindBean ContratoBD contrato);
    
    @SqlUpdate("DELETE FROM contratos.contrato WHERE id = :id")
    @DefineUserInSession
    void excluirContrato(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.contrato WHERE id = :id")
    @RegisterFieldMapper(ContratoBD.class)
    Optional<ContratoBD> recuperarContratoPorId(@Bind ("id") Long id);
    
    @SqlQuery("SELECT * FROM contratos.contrato")
    @RegisterFieldMapper(ContratoBD.class)
    List<ContratoBD> recuperarTodosContrato();
    
    @SqlBatch("INSERT INTO contratos.contrato (data_assinatura, data_publicacao, fim_vigencia, dt_fim_vigencia_original, in_situacao, inicio_vigencia, numero, objeto, proposta_id, valor_total, versao, adt_data_hora, adt_operacao, adt_login) VALUES (  :dataAssinatura,  :dataPublicacao,  :fimVigencia, :dtFimVigenciaOriginal, :inSituacao,  :inicioVigencia,  :numero,  :objeto,  :propostaId,  :valorTotal, 1, LOCALTIMESTAMP, 'INSERT', current_setting('contratos.cpf_usuario'))")
    @DefineUserInSession
    @RegisterFieldMapper(ContratoBD.class)
    void inserirContratoBatch (@BindBean Collection<ContratoBD> contratos);
    
	@SqlQuery("SELECT contrato.* FROM contratos.contrato AS contrato JOIN contratos.proposta ON contrato.proposta_id = proposta.id WHERE proposta.id_siconv = :idProposta AND contrato.in_situacao != 'EXC' order by contrato.numero asc")
    @RegisterFieldMapper(ContratoBD.class)
	List<ContratoBD> loadContratosPorIdSiconvProposta(@Bind("idProposta") Long idProposta);

	@SqlQuery("SELECT cont.* FROM contratos.contrato cont, contratos.lote lot WHERE lot.licitacao_id = :idLicitacao AND lot.contrato_id = cont.id")
	@RegisterFieldMapper(ContratoBD.class)
	List<ContratoBD> recuperarContratosDaLicitacao(@Bind("idLicitacao") Long idLicitacao);
	
	@SqlQuery("select exists (select 1 from contratos.contrato c where c.numero = :numero and c.in_situacao = 'CON' and c.proposta_id = (SELECT prop.id FROM contratos.proposta prop WHERE prop.id_siconv = :propostaIdSiconv))")
	boolean seExisteContratoConcluidoComONumero(@BindBean ContratoDTO contrato);

	@SqlQuery("select exists (select 1 from contratos.contrato c where c.numero = :numero and c.in_situacao = 'CON' and c.id != :id and c.proposta_id = :propostaId)")
	@RegisterFieldMapper(ContratoBD.class)
	boolean seExisteContratoConcluidoComONumeroDiferenteDoId(@BindBean ContratoBD contrato);
	
	@SqlQuery("SELECT contrato.* FROM contratos.contrato WHERE contrato.proposta_id = :idProposta order by contrato.numero asc")
    @RegisterFieldMapper(ContratoBD.class)
	List<ContratoBD> loadContratosPorIdProposta(@Bind("idProposta") Long idProposta);
	
	@SqlQuery("SELECT contrato.* FROM contratos.contrato AS contrato JOIN contratos.proposta ON contrato.proposta_id = proposta.id WHERE proposta.id_siconv = :idProposta AND contrato.in_situacao != 'EXC' order by contrato.id desc")
    @RegisterFieldMapper(ContratoBD.class)
	List<ContratoBD> loadContratosPorIdSiconvPropostaOrdenadosPorDataInclusao(@Bind("idProposta") Long idProposta);

	@SqlQuery("SELECT contrato.* FROM contratos.contrato AS contrato JOIN contratos.proposta ON contrato.proposta_id = proposta.id WHERE proposta.id_siconv = :idProposta AND contrato.in_situacao = 'EXC' order by contrato.id desc")
    @RegisterFieldMapper(ContratoBD.class)
	List<ContratoBD> loadContratosExcluidosPorIdSiconvPropostaOrdenadosPorDataInclusao(@Bind("idProposta") Long idProposta);

}
