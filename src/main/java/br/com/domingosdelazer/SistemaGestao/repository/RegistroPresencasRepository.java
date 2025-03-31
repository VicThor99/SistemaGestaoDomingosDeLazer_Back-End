package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.RegistroPresencas;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RegistroPresencasRepository extends JpaRepository<RegistroPresencas, Integer> {
    @Query(nativeQuery = true, value = "select r.* from registropresencas r inner join aluno a on r.id = a.registro_id where a.id = :id")
    RegistroPresencas carregarRegistroPorIdAluno(@Param("id") Integer id);

    @Query(nativeQuery = true, value = "select r.* from registropresencas r inner join aluno a on r.id = a.registro_id " +
            "where a.codigo = :codigo AND a.escola_id = :escolaId")
    RegistroPresencas carregarRegistroPorCodigoAluno(@Param("codigo") String codigo, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set fevereiro = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosFevereiro(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set marco = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosMarco(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set abril = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosAbril(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set maio = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosMaio(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set junho = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosJunho(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set agosto = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosAgosto(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set setembro = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosSetembro(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set outubro = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosOutubro(@Param("dom") String dom, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id " +
            "inner join serie s on s.id = a.serie_id set novembro = 5 where s.domingo = :dom AND a.escola_id = :escolaId")
    void darFaltaParaAlunosNovembro(@Param("dom")String dom, @Param("escolaId") Integer escolaId);

    @Query("select new br.com.domingosdelazer.SistemaGestao.entity.dto.response.RegistroPresencaAlunoResponseDTO(a.codigo, a.nome, " +
            "r.fevereiro, r.marco, r.abril, r.maio, r.junho, r.agosto, r.setembro, r.outubro, r.novembro) " +
            "from RegistroPresencas r inner join Aluno a on r.id = a.registroPresencas " +
            "where a.escola.id = :escolaId")
    List<RegistroPresencaAlunoResponseDTO> carregarRegistrosAlunos(@Param("escolaId") Integer escolaId);
    
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.fevereiro = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaFevereiro(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.marco = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaMarco(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.abril = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaAbril(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.maio = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaMaio(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.junho = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaJunho(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.agosto = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaAgosto(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.setembro = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaSetembro(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.outubro = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaOutubro(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update registropresencas r inner join aluno a on r.id = a.registro_id set r.novembro = 1, a.ativo = true " +
            "where a.nome in :nomes and a.escola_id = :escolaId")
    void darPresencaParaListaNovembro(@Param("nomes") List<String> nomes, @Param("escolaId") Integer escolaId);
}
