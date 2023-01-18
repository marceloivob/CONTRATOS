package br.gov.economia.maisbrasil.contratos.repository.jdbi;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterFieldMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import br.gov.economia.maisbrasil.contratos.core.inspect.DefineUserInSession;
import br.gov.economia.maisbrasil.contratos.domain.bd.PoBD;

public interface PoDAO {

    @SqlUpdate("INSERT INTO contratos.po (id_po_vrpl, submeta_id, dt_previsao_inicio_obra, qt_meses_duracao_obra, in_acompanhamento_eventos, in_desonerado, sg_localidade, dt_base_analise, referencia, dt_base_vrpl, dt_previsao_inicio_obra_analise, adt_login, adt_data_hora, adt_operacao, versao) VALUES (:idPoVrpl, :submetaId, :dtPrevisaoInicioObra, :qtMesesDuracaoObra, :inAcompanhamentoEventos, :inDesonerado, :sgLocalidade, :dtBaseAnalise, :referencia, :dtBaseVrpl, :dtPrevisaoInicioObraAnalise, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT', 1)")
    @RegisterFieldMapper(PoBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    PoBD inserirPo(@BindBean PoBD po);

    @SqlUpdate("UPDATE contratos.po SET id_po_vrpl = :idPoVrpl, submeta_id = :submetaId, dt_previsao_inicio_obra = :dtPrevisaoInicioObra, qt_meses_duracao_obra = :qtMesesDuracaoObra, in_acompanhamento_eventos = :inAcompanhamentoEventos, in_desonerado = :inDesonerado, sg_localidade = :sgLocalidade, dt_base_analise = :dtBaseAnalise, referencia = :referencia, dt_base_vrpl = :dtBaseVrpl, dt_previsao_inicio_obra_analise = :dtPrevisaoInicioObraAnalise, adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE', versao = (:versao + 1) WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(PoBD.class)
    @GetGeneratedKeys
    PoBD alterarPo(@BindFields PoBD po);

    @SqlUpdate("DELETE FROM contratos.po WHERE id = :id")
    @DefineUserInSession
    void excluirPo(@Bind ("id") Long id);

    @SqlQuery("SELECT * FROM contratos.po WHERE id = :id")
    @RegisterFieldMapper(PoBD.class)
    Optional<PoBD> recuperarPoPorId(@Bind ("id") Long id);


    @SqlQuery("SELECT * FROM contratos.po")
    @RegisterFieldMapper(PoBD.class)
    List<PoBD> recuperarTodosPo();

    @SqlBatch("INSERT INTO contratos.po (id_po_vrpl, submeta_id, dt_previsao_inicio_obra, qt_meses_duracao_obra, in_acompanhamento_eventos, in_desonerado, sg_localidade, dt_base_analise, referencia, dt_base_vrpl, dt_previsao_inicio_obra_analise, adt_login, adt_data_hora, adt_operacao, versao) VALUES (:idPoVrpl, :submetaId, :dtPrevisaoInicioObra, :qtMesesDuracaoObra, :inAcompanhamentoEventos, :inDesonerado, :sgLocalidade, :dtBaseAnalise, :referencia, :dtBaseVrpl, :dtPrevisaoInicioObraAnalise, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT', 1)")
    @DefineUserInSession
    @RegisterFieldMapper(PoBD.class)
    void inserirPoBatch (@BindBean Collection<PoBD> pos);

    @SqlQuery("SELECT * FROM contratos.po WHERE submeta_id = :idSubmeta")
    @RegisterFieldMapper(PoBD.class)
    Optional<PoBD> recuperarPoPorSubmeta(@Bind("idSubmeta") Long idSubmeta);

    @SqlQuery("SELECT * FROM contratos.po WHERE submeta_id in (<listaIdsSubmetas>)")
    @RegisterFieldMapper(PoBD.class)
	List<PoBD> recuperarPoPorListaSubmetas(@BindList("listaIdsSubmetas") List<Long> listaIdsSubmetas);

}
