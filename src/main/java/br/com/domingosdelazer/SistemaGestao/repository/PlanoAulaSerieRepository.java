package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.PlanoAula;
import br.com.domingosdelazer.SistemaGestao.entity.PlanoAulaSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanoAulaSerieRepository extends JpaRepository<PlanoAulaSerie, Integer> {

}
