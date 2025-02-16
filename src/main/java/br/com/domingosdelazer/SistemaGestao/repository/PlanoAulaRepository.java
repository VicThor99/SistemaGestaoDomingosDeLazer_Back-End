package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.PlanoAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanoAulaRepository extends JpaRepository<PlanoAula, Integer> {

    @Query(nativeQuery = true, value = "select pa.* from planoaula pa " +
            "inner join planoaulaserie pas on pas.planoaula_id = pa.id " +
            "inner join serie se on se.id = pas.serie_id " +
            "inner join sala sa on sa.id = se.sala_id " +
            "where sa.sala = :nomeSala and pa.escola_id = :escola_id " +
            "order by pa.mes limit 1")
    PlanoAula getPlanoPorNomeSala(@Param("nomeSala") String nomeSala, @Param("escola_id") Integer escolaId);

    @Query(nativeQuery = true, value = "select IF(COUNT(*) > 0, 'true', 'false') from planoaulaserie pas " +
            "where pas.serie_id = (select id from serie where serie = :serie) " +
            "and pas.planoaula_id = :planoaula_id")
    Boolean verificaLigacaoPlanoAulaSerie(@Param("serie") String serie, @Param("planoaula_id") Integer planoAulaId);

    @Query(nativeQuery = true, value = "select se.serie from planoaulaserie pas " +
            "inner join serie se on pas.serie_id = se.id " +
            "where pas.planoaula_id = :planoaula_id")
    List<String> getLigacoesPlanoAulaSerie(@Param("planoaula_id") Integer planoAulaId);

    @Query(nativeQuery = true, value = "select * from planoaula pa " +
            "where pa.id = :planoaula_id and pa.escola_id = :escola_id")
    PlanoAula findById(@Param("planoaula_id") Integer planoId, @Param("escola_id") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from planoaula pa where pa.escola_id = :escola_id")
    List<PlanoAula> findAll(@Param("escola_id") Integer escolaId);
}
