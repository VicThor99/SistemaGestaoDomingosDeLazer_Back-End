package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.RegistroPresencas;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegistroPresencasRepository extends JpaRepository<RegistroPresencas, Integer> {
    @Query(nativeQuery = true, value = "select r.* from registropresencas r inner join aluno a on r.id = a.registro_id where a.id = :id")
    RegistroPresencas carregarRegistroPorIdAluno(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "select r.* from registropresencas r inner join aluno a on r.id = a.registro_id where a.codigo = :codigo")
    RegistroPresencas carregarRegistroPorCodigoAluno(@Param("codigo") String codigo);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set fevereiro = 4 where s.domingo = :dom")
    void darFaltaParaAlunosFevereiro(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set marco = 4 where s.domingo = :dom")
    void darFaltaParaAlunosMarco(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set abril = 4 where s.domingo = :dom")
    void darFaltaParaAlunosAbril(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set maio = 4 where s.domingo = :dom")
    void darFaltaParaAlunosMaio(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set junho = 4 where s.domingo = :dom")
    void darFaltaParaAlunosJunho(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set agosto = 4 where s.domingo = :dom")
    void darFaltaParaAlunosAgosto(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set setembro = 4 where s.domingo = :dom")
    void darFaltaParaAlunosSetembro(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set outubro = 4 where s.domingo = :dom")
    void darFaltaParaAlunosOutubro(@Param("dom") String dom);

    @Modifying
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id inner join serie s on s.id = a.serie_id set novembro = 4 where s.domingo = :dom")
    void darFaltaParaAlunosNovembro(@Param("dom") String dom);

    @Query("select new br.com.paraisopolis.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO(a.codigo, a.nome, " +
            "r.fevereiro, r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro) " +
            "from RegistroPresencas r inner join Aluno a on r.id = a.registroPresencas")
    List<RegistroPresencaAlunoResponseDTO> carregarRegistrosAlunos();

}
