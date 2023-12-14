package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DataAulaRepository extends JpaRepository<DataAula, Integer> {

    @Query(nativeQuery = true, value = "select * from paraisopolis.dataaula d where d.domingo = :domingo AND d.dataaula > :dataAtual order by d.dataaula ASC LIMIT 1")
    Optional<DataAula> getProximaAula(@Param("domingo") String domingo, @Param("dataAtual") String dataAtual);

    @Query(nativeQuery = true, value = "select * from paraisopolis.dataaula d where d.domingo = :domingo order by d.dataaula ASC")
    List<DataAula> getAulasPorDomingo(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select * from dataaula d WHERE d.dataaula <= :dataAtual order by d.dataaula DESC LIMIT 1")
    DataAula getAulaParaPresenca(@Param("dataAtual") String dataAtual);

    @Query(nativeQuery = true, value = "select * from paraisopolis.dataaula d where d.domingo = 'E' LIMIT 1")
    DataAula getEntregaSacolinha();

    @Query(nativeQuery = true, value = "select * from paraisopolis.dataaula d where d.domingo = :domingo LIMIT 1")
    DataAula getPrimeiraAula(@Param("domingo") String domingo);
}
