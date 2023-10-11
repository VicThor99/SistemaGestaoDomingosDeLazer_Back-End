package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.DataAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface DataAulaRepository extends JpaRepository<DataAula, Integer> {

    @Query(nativeQuery = true, value = "select * from paraisopolis.dataaula d where d.domingo = :domingo AND d.dataaula > :dataAtual order by d.dataaula ASC LIMIT 1")
    Optional<DataAula> getProximaAula(@Param("domingo") String domingo, @Param("dataAtual") String dataAtual);

}
