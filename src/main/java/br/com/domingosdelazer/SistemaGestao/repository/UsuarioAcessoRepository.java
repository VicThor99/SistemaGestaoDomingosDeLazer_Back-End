package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.UsuarioAcesso;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.UsuarioAcessoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioAcessoRepository extends JpaRepository<UsuarioAcesso, Integer> {

    @Query(nativeQuery = true, value = "select * from UsuarioAcesso where usuario_id = :id ")
    List<UsuarioAcesso> listAccessForUser(@Param("id") Integer userId);

}
