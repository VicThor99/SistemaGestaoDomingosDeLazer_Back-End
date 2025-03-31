package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

    @Query(nativeQuery = true, value = "select * from domingodelazer.serie s where serie = :serie and escola_id = :escolaId")
    Serie verificarSeries(@Param("serie") String serie, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select s.domingo from serie s where serie = :serie and escola_id = :escolaId")
    String getDomingoPorSerie(@Param("serie") String serie, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select s.serie from serie s where escola_id = :escolaId")
    List<String> listSeriesString(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.serie s where escola_id = :escolaId")
    List<Serie> findAllEscola(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select s.serie from domingodelazer.serie s " +
            "inner join domingodelazer.sala sa on sa.id = s.sala_id " +
            "where sa.sala = :sala and s.escola_id = :escolaId " +
            "order by s.serie")
    List<String> getSeriesPorSala(@Param("sala") String sala, @Param("escolaId") Integer escolaId);
}
