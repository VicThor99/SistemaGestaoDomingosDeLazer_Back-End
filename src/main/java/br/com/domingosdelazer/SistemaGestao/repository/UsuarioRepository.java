package br.com.domingosdelazer.SistemaGestao.repository;

import br.com.domingosdelazer.SistemaGestao.entity.Usuario;
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

    @Query("select u from Usuario u where u.id = :id")
    Optional<Usuario> getUserById(@Param("id") Integer usuarioId);

    @Query("select u from Usuario u where nome = :username")
    Optional<Usuario> getUserByName(@Param("username") String username);
}
