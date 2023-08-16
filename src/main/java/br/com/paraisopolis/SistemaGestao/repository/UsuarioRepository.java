package br.com.paraisopolis.SistemaGestao.repository;

import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

}
