package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Integer> {

    @Query("select s from Serie s where serie = :serie")
    Serie verificarSeries(@Param("serie") String serie);

    @Query("select s.domingo from Serie s where serie = :serie")
    String getDomingoPorSerie(String serie);
}
