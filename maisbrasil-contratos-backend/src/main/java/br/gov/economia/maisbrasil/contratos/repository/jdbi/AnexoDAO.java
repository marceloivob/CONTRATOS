package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.BindList.EmptyHandling;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.AnexoBD;

public interface AnexoDAO {

	@SqlUpdate("INSERT INTO contratos.anexo (bucket, caminho, contrato_id, dt_upload, id_cpf_enviado_por, in_perfil_usuario, in_tipo_anexo, nm_arquivo, nome_enviado_por, termo_aditivo_id, tx_descricao, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :bucket,  :caminho,  :contratoId,  :dtUpload,  :idCpfEnviadoPor,  :inPerfilUsuario,  :inTipoAnexo,  :nmArquivo,  :nomeEnviadoPor,  :termoAditivoId,  :txDescricao, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
	@RegisterFieldMapper(AnexoBD.class)
	@DefineUserInSession
	@GetGeneratedKeys
	AnexoBD inserirAnexo(@BindBean AnexoBD anexo);

	@SqlUpdate("UPDATE contratos.anexo SET bucket = :bucket, caminho = :caminho, contrato_id = :contratoId, dt_upload = :dtUpload, id_cpf_enviado_por = :idCpfEnviadoPor, in_perfil_usuario = :inPerfilUsuario, in_tipo_anexo = :inTipoAnexo, nm_arquivo = :nmArquivo, nome_enviado_por = :nomeEnviadoPor, termo_aditivo_id = :termoAditivoId, tx_descricao = :txDescricao, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
	@DefineUserInSession
	@RegisterFieldMapper(AnexoBD.class)
	@GetGeneratedKeys
	AnexoBD alterarAnexo(@BindBean AnexoBD anexo);

	@SqlUpdate("DELETE FROM contratos.anexo WHERE id = :id")
	@DefineUserInSession
	void excluirAnexo(@Bind("id") Long id);

	@SqlQuery("SELECT * FROM contratos.anexo WHERE id = :id")
	@RegisterFieldMapper(AnexoBD.class)
	Optional<AnexoBD> recuperarAnexoPorId(@Bind("id") Long id);

	@SqlQuery("SELECT * FROM contratos.anexo")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> recuperarTodosAnexos();

	@SqlQuery("SELECT * FROM contratos.anexo where contrato_id = :idContrato")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> recuperarAnexoPorIdContrato(@Bind("idContrato") Long idContrato);

	@SqlQuery("SELECT * FROM contratos.anexo where termo_aditivo_id = :idTermoAditivo")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> recuperarAnexoPorIdTermoAditivo(@Bind("idTermoAditivo") Long idTermoAditivo);
	
	
	@SqlQuery("SELECT * FROM contratos.anexo where contrato_id = :idContrato AND in_tipo_anexo = :tipoAnexo")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> getAnexosContratoPorTipo(@Bind("idContrato") Long idContrato, @Bind("tipoAnexo") String tipoAnexo);

	@SqlQuery("SELECT * FROM contratos.anexo where contrato_id = :idContrato AND in_tipo_anexo in (<tiposAnexos>)")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> getAnexosContratoPorConjuntoTipos(@Bind("idContrato") Long idContrato,
			@BindList(value = "tiposAnexos", onEmpty = EmptyHandling.NULL_STRING) List<String> tiposAnexos);

	@SqlQuery("SELECT * FROM contratos.anexo where termo_aditivo_id = :idTermoAditivo AND in_tipo_anexo in (<tiposAnexos>)")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> getAnexosTermoAditivoPorConjuntoTipos(@Bind("idTermoAditivo") Long idTermoAditivo,
			@BindList(value = "tiposAnexos", onEmpty = EmptyHandling.NULL_STRING) List<String> tiposAnexos);

	
	@SqlQuery("SELECT * FROM contratos.anexo where contrato_id = :idContrato AND id != :id")
	@RegisterFieldMapper(AnexoBD.class)
	List<AnexoBD> recuperarOutrosAnexosDaLicitacao(@Bind("idContrato") Long idContrato, @Bind("id") Long id);

	@SqlUpdate("DELETE FROM contratos.anexo WHERE contrato_id = :idContrato")
	@DefineUserInSession
	void excluirAnexosPorIdContrato(@Bind("idContrato") Long idContrato);

}
