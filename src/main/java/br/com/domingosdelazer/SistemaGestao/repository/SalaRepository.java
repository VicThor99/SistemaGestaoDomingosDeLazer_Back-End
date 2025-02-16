package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    @Query(nativeQuery = true, value = "select distinct(sa.sala) from serie s inner join sala sa on sa.id = s.sala_id where s.escola_id = :escolaId")
    List<String> listSalasString(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select s.sala from sala s where s.sala = :nomeSala")
    Sala getSalaPorNomeSala(@Param("nomeSala") String nomeSala);
}
