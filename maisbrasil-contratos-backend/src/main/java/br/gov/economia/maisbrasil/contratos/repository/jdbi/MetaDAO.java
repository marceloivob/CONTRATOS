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
import br.gov.economia.maisbrasil.contratos.domain.bd.MetaBD;

public interface MetaDAO {


    @SqlUpdate("INSERT INTO contratos.meta (id_meta_vrpl, in_social, numero, qt_itens, tx_descricao, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :idMetaVrpl,  :inSocial,  :numero,  :qtItens,  :txDescricao, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @RegisterFieldMapper(MetaBD.class)
    @DefineUserInSession
    @GetGeneratedKeys
    MetaBD inserirMeta(@BindBean MetaBD meta);

    @SqlUpdate("UPDATE contratos.meta SET id_meta_vrpl = :idMetaVrpl, in_social = :inSocial, numero = :numero, qt_itens = :qtItens, tx_descricao = :txDescricao, versao = (:versao + 1),  adt_login = current_setting('contratos.cpf_usuario'), adt_data_hora = LOCALTIMESTAMP, adt_operacao = 'UPDATE' WHERE id = :id")
    @DefineUserInSession
    @RegisterFieldMapper(MetaBD.class)
    @GetGeneratedKeys
    MetaBD alterarMeta(@BindFields MetaBD meta);

    @SqlUpdate("DELETE FROM contratos.meta WHERE id = :id")
    @DefineUserInSession
    void excluirMeta(@Bind ("id") Long id);

    @SqlQuery("SELECT * FROM contratos.meta WHERE id = :id")
    @RegisterFieldMapper(MetaBD.class)
    Optional<MetaBD> recuperarMetaPorId(@Bind ("id") Long id);


    @SqlQuery("SELECT * FROM contratos.meta")
    @RegisterFieldMapper(MetaBD.class)
    List<MetaBD> recuperarTodosMeta();


    @SqlBatch("INSERT INTO contratos.meta (id_meta_vrpl, in_social, numero, qt_itens, tx_descricao, versao, adt_login, adt_data_hora, adt_operacao) VALUES (  :idMetaVrpl,  :inSocial,  :numero,  :qtItens,  :txDescricao, 1, current_setting('contratos.cpf_usuario'), LOCALTIMESTAMP, 'INSERT')")
    @DefineUserInSession
    @RegisterFieldMapper(MetaBD.class)
    void inserirMetaBatch (@BindBean Collection<MetaBD> metas);

    @SqlQuery("SELECT * FROM contratos.meta WHERE id_meta_vrpl = :idMetaVrpl")
    @RegisterFieldMapper(MetaBD.class)
    Optional<MetaBD> recuperarMetaPorIdVrpl(@Bind ("idMetaVrpl") Long idMetaVrpl);


    @SqlQuery("select (select count(*) from contratos.submeta s, contratos.lote lote, contratos.licitacao lic  where s.meta_id = :idMeta and s.lote_id = lote.id and lote.licitacao_id = lic.id and lic.id != :idLicitacao) = 0")
	boolean seMetaNaoEstaAssociadaAOutraLicitacao(@Bind ("idMeta") Long idMeta, @Bind ("idLicitacao") Long idLicitacao);

    @SqlQuery("SELECT * FROM contratos.meta WHERE id in (<listaIdsMetas>)")
    @RegisterFieldMapper(MetaBD.class)
    List<MetaBD> recuperarMetaPorListaIds(@BindList("listaIdsMetas") List<Long> listaIdsMetas);

}
