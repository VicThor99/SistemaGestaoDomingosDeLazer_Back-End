package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EscolaRepository extends JpaRepository<Escola, Integer> {

    @Query("select e from Escola e where e.id = :id")
    Escola getEscolaById(@Param("id") Integer id);

}
