package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    @Query("select admin from Usuario where username = :username")
    boolean userAdministrador(@Param("username") String username);

    @Query("select nome from Usuario where username = :username")
    String getUserName(@Param("username") String username);
}
