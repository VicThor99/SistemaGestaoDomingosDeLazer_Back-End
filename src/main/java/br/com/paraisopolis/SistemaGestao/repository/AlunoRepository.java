package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query(nativeQuery = true, value = "select a.* from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) < 3")
    List<Aluno> listAlunosAptosASacolinha();

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) < 3 " +
            "AND s.domingo = :domingo")
    Integer countAlunosAptosASacolinha(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) = 2 " +
            "AND s.domingo = :domingo")
    Integer countAlunosEmRisco(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE s.domingo = :domingo")
    Integer countAlunosTotal(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.fevereiro != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesFevereiro(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.marco != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesMarco(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.abril != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesAbril(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.maio != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesMaio(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.junho != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesJunho(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.agosto != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesAgosto(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.setembro != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesSetembro(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.outubro != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesOutubro(@Param("domingo") String domingo);

    @Query(nativeQuery = true, value = "select count(*) from paraisopolis.aluno a " +
            "inner join paraisopolis.registropresencas r on r.id = a.registro_id " +
            "inner join paraisopolis.serie s on s.id = a.serie_id " +
            "WHERE r.novembro != 4 AND s.domingo = :domingo")
    Integer countAlunosPresentesNovembro(@Param("domingo") String domingo);

}
