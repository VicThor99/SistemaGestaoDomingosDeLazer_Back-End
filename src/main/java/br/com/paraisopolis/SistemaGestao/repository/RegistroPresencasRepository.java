package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.RegistroPresencas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistroPresencasRepository extends JpaRepository<RegistroPresencas, Integer> {
    @Query(nativeQuery = true, value = "select r.* from registropresencas r inner join aluno a on r.id = a.registro_id where a.id = :id")
    RegistroPresencas carregarRegistroPorIdAluno(@Param("id") Integer id);
}
