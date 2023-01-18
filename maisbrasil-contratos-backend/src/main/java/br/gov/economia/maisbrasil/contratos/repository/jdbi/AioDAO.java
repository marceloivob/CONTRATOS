package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.time.LocalDate;
import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.AioBD;

public interface AioDAO {

	@SqlUpdate("insert into contratos.aio (emissor, dt_emissao_aio, contrato_emissor_fk, proposta_fk, versao, adt_login, adt_data_hora, adt_operacao) values ('SISTEMA', :dataEmissaoAIO, :idContratoEmissor, :idProposta, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
	@DefineUserInSession
	@RegisterFieldMapper(AioBD.class)
	@GetGeneratedKeys
	AioBD emitirAIO(@Bind("idProposta") Long idProposta, @Bind("idContratoEmissor") Long idContratoEmissor, @Bind("dataEmissaoAIO") LocalDate dataEmissaoAIO);

	@SqlUpdate("update contratos.aio set contrato_emissor_fk = null, versao = (versao + 1), adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' where proposta_fk = :idProposta")
	@DefineUserInSession
	@RegisterFieldMapper(AioBD.class)
	@GetGeneratedKeys
	AioBD limparContratoEmissor(@Bind("idProposta") Long idProposta);

	@SqlQuery("select * from contratos.aio where proposta_fk = :idProposta")
	@RegisterFieldMapper(AioBD.class)
	@GetGeneratedKeys
	AioBD findByPropostaId(@Bind("idProposta") Long propostaId);

	@SqlUpdate("DELETE FROM contratos.aio where proposta_fk = :idProposta")
	@DefineUserInSession
	void apagarRegistroAIO(@Bind("idProposta") Long idProposta);

}
