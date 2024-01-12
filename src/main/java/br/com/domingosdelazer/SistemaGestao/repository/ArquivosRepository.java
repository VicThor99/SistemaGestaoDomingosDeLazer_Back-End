package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.ArquivosAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArquivosRepository extends JpaRepository<ArquivosAluno, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM arquivosaluno a " +
            "inner join aluno al on al.arquivo_id = a.id where a.id = :idAluno")
    ArquivosAluno getArquivosByIdAluno(@Param("idAluno") Integer idAluno);
}
