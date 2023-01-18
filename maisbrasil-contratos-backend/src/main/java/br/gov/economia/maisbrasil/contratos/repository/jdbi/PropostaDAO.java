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
import br.gov.economia.maisbrasil.contratos.domain.bd.PropostaBD;


public interface PropostaDAO {

	@SqlUpdate("INSERT INTO contratos.proposta (ano_convenio, ano_proposta, apelido_empreendimento, categoria, codigo_programa, data_assinatura_convenio, id_siconv, identificacao_proponente, modalidade, nivel_contrato, nome_mandataria, nome_objeto, nome_programa, nome_proponente, numero_convenio, numero_proposta, pc_min_contrapartida, spa_homologado, uf, valor_contrapartida, valor_global, valor_repasse, versao, adt_login, adt_data_hora, adt_operacao, termo_compromisso_tem_mandatar) VALUES (  :anoConvenio,  :anoProposta,  :apelidoEmpreendimento,  :categoria,  :codigoPrograma,  :dataAssinaturaConvenio,  :idSiconv,  :identificacaoProponente,  :modalidade,  :nivelContrato,  :nomeMandataria,  :nomeObjeto,  :nomePrograma,  :nomeProponente,  :numeroConvenio,  :numeroProposta,  :pcMinContrapartida,  :spaHomologado,  :uf,  :valorContrapartida,  :valorGlobal,  :valorRepasse, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT', :termoCompromissoTemMandatar)")
	@RegisterFieldMapper(PropostaBD.class)
	@DefineUserInSession
	@GetGeneratedKeys
	PropostaBD inserirProposta(@BindBean PropostaBD proposta);

	@SqlUpdate("UPDATE contratos.proposta SET ano_convenio = :anoConvenio, ano_proposta = :anoProposta, apelido_empreendimento = :apelidoEmpreendimento, categoria = :categoria, codigo_programa = :codigoPrograma, data_assinatura_convenio = :dataAssinaturaConvenio, id_siconv = :idSiconv, identificacao_proponente = :identificacaoProponente, modalidade = :modalidade, nivel_contrato = :nivelContrato, nome_mandataria = :nomeMandataria, nome_objeto = :nomeObjeto, nome_programa = :nomePrograma, nome_proponente = :nomeProponente, numero_convenio = :numeroConvenio, numero_proposta = :numeroProposta, pc_min_contrapartida = :pcMinContrapartida, spa_homologado = :spaHomologado, uf = :uf, valor_contrapartida = :valorContrapartida, valor_global = :valorGlobal, valor_repasse = :valorRepasse, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE', termo_compromisso_tem_mandatar = :termoCompromissoTemMandatar WHERE id = :id")
	@DefineUserInSession
	@RegisterFieldMapper(PropostaBD.class)
	@GetGeneratedKeys
	PropostaBD alterarProposta(@BindBean PropostaBD proposta);

	@SqlUpdate("DELETE FROM contratos.proposta WHERE id = :id")
	@DefineUserInSession
	void excluirProposta(@Bind("id") Long id);

	@SqlQuery("SELECT * FROM contratos.proposta WHERE id = :id")
	@RegisterFieldMapper(PropostaBD.class)
	Optional<PropostaBD> recuperarPropostaPorId(@Bind("id") Long id);

	@SqlQuery("SELECT * FROM contratos.proposta")
	@RegisterFieldMapper(PropostaBD.class)
	List<PropostaBD> recuperarTodasProposta();

	@SqlBatch("INSERT INTO contratos.proposta (ano_convenio, ano_proposta, apelido_empreendimento, categoria, codigo_programa, data_assinatura_convenio, id_siconv, identificacao_proponente, modalidade, nivel_contrato, nome_mandataria, nome_objeto, nome_programa, nome_proponente, numero_convenio, numero_proposta, pc_min_contrapartida, spa_homologado, uf, valor_contrapartida, valor_global, valor_repasse, versao, adt_login, adt_data_hora, adt_operacao, termo_compromisso_tem_mandatar) VALUES (  :anoConvenio,  :anoProposta,  :apelidoEmpreendimento,  :categoria,  :codigoPrograma,  :dataAssinaturaConvenio,  :idSiconv,  :identificacaoProponente,  :modalidade,  :nivelContrato,  :nomeMandataria,  :nomeObjeto,  :nomePrograma,  :nomeProponente,  :numeroConvenio,  :numeroProposta,  :pcMinContrapartida,  :spaHomologado,  :uf,  :valorContrapartida,  :valorGlobal,  :valorRepasse, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT', :termoCompromissoTemMandatar)")
	@DefineUserInSession
	@RegisterFieldMapper(PropostaBD.class)
	void inserirPropostaBatch(@BindBean Collection<PropostaBD> propostas);

	@SqlQuery("SELECT * FROM contratos.proposta WHERE id_siconv = :idSiconv")
	@RegisterFieldMapper(PropostaBD.class)
	Optional<PropostaBD> recuperarPropostaPorIdSiconv(@Bind("idSiconv") Long idSiconv);
	
}
