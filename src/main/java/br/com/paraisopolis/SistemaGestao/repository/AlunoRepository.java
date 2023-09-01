package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query(nativeQuery = true, value = "select a.* from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) < 3;")
    List<Aluno> listAlunosAptosASacolinha();

}
