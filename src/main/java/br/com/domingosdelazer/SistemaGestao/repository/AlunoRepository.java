package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    @Query(nativeQuery = true, value = "select a.* from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '5', ''))) < 3 " +
            "AND a.escola_id = :escolaId")
    List<Aluno> listAlunosAptosASacolinha(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '5', ''))) < 3 " +
            "AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosAptosASacolinha(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '5', ''))) = 2 " +
            "AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosEmRisco(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosTotal(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.fevereiro != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesFevereiro(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.marco != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesMarco(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.abril != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesAbril(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.maio != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesMaio(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.junho != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesJunho(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.agosto != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesAgosto(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.setembro != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesSetembro(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.outubro != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesOutubro(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select count(*) from aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE r.novembro != 5 AND s.domingo = :domingo AND a.escola_id = :escolaId")
    Integer countAlunosPresentesNovembro(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select a.codigo from aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.domingo = :domingo AND a.escola_id = :escolaId order by a.codigo desc LIMIT 1")
    String getUltimoCodigoPorDomingo(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) < 3 " +
            "AND s.domingo = :domingo AND a.escola_id = :escolaId")
    List<Aluno> getAlunosAptosASacolinha(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) < 3 " +
            "AND a.escola_id = :escolaId")
    List<Aluno> getAlunosAptosASacolinha(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) = 2 " +
            "AND s.domingo = :domingo AND a.escola_id = :escolaId")
    List<Aluno> getAlunosEmRiscoASacolinha(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join registropresencas r on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE (LENGTH(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro)) " +
            "- LENGTH(REPLACE(CONCAT(r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro), '4', ''))) = 2 " +
            "AND a.escola_id = :escolaId")
    List<Aluno> getAlunosEmRiscoASacolinha(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a WHERE a.codigo = :codigo AND a.escola_id = :escolaId")
    List<Aluno> getAlunosPorCodigo(@Param("codigo") String codigo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.domingo = :domingo AND a.escola_id = :escolaId")
    List<Aluno> getAlunosPorDomingo(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.domingo = :domingo AND a.ativo = true AND a.escola_id = :escolaId")
    List<Aluno> getAlunosAtivosPorDomingo(@Param("domingo") String domingo, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.serie = :serie AND a.escola_id = :escolaId")
    List<Aluno> getAlunosPorSerie(@Param("serie") String serie, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.serie = :serie AND a.ativo = true AND a.escola_id = :escolaId")
    List<Aluno> getAlunosAtivosPorSerie(@Param("serie") String serie, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.sala = :sala AND a.escola_id = :escolaId")
    List<Aluno> getAlunosPorSala(@Param("sala") String sala, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "inner join serie s on s.id = a.serie_id " +
            "WHERE s.sala = :sala AND a.ativo = true AND a.escola_id = :escolaId")
    List<Aluno> getAlunosAtivosPorSala(@Param("sala") String sala, @Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a WHERE a.ativo = true AND a.escola_id = :escolaId")
    List<Aluno> getAlunosAtivos(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a WHERE a.escola_id = :escolaId")
    List<Aluno> findAllByEscolaId(@Param("escolaId") Integer escolaId);

    @Query(nativeQuery = true, value = "select * from domingodelazer.aluno a " +
            "WHERE a.codigo = :codigo AND a.escola_id = :escolaId LIMIT 1")
    Optional<Aluno> getAlunoPorCodigo(@Param("codigo") String codigo, @Param("escolaId") Integer escolaId);
}
