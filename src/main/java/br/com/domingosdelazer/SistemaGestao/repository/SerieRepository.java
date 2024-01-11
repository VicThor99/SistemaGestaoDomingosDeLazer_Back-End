package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

    @Query(nativeQuery = true, value = "select * from Serie s where serie = :serie and escola_id = :escolaId")
    Serie verificarSeries(@Param("serie") String serie, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select s.domingo from Serie s where serie = :serie and escola_id = :escolaId")
    String getDomingoPorSerie(@Param("serie") String serie, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select s.serie from Serie s where escola_id = :escolaId")
    List<String> listSeriesString(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select distinct s.sala from Serie s where escola_id = :escolaId")
    List<String> listSalasString(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from Serie s where escola_id = :escolaId")
    List<Serie> findAllEscola(Integer escolaId);
}
