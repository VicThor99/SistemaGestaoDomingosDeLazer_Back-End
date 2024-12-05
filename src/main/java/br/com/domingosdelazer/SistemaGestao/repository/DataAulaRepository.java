package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DataAulaRepository extends JpaRepository<DataAula, Integer> {

    @Query(nativeQuery = true, value = "select * from domingodelazer.dataaula d " +
            "where d.domingo = :domingo AND d.dataaula > :dataAtual AND d.escola_id = :escolaId " +
            "order by d.dataaula ASC LIMIT 1")
    Optional<DataAula> getProximaAula(@Param("domingo") String domingo, @Param("dataAtual") String dataAtual, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.dataaula d " +
            "where d.domingo = :domingo and d.escola_id = :escolaId order by d.dataaula ASC")
    List<DataAula> getAulasPorDomingo(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.dataaula d " +
            "WHERE d.dataaula <= :dataAtual and d.escola_id = :escolaId order by d.dataaula DESC LIMIT 1")
    DataAula getAulaParaPresenca(@Param("dataAtual") String dataAtual, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.dataaula d " +
            "where d.domingo = 'E' and d.escola_id = :escolaId LIMIT 1")
    DataAula getEntregaSacolinha(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.dataaula d " +
            "where d.domingo = :domingo and d.escola_id = :escolaId LIMIT 1")
    DataAula getPrimeiraAula(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.dataaula d " +
            "WHERE d.escola_id = :escolaId order by d.dataaula DESC")
    List<DataAula> listAll(@Param("escolaId") Integer escolaId);
}
