package br.com.paraisopolis.SistemaGestao.service.impl;


import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import br.com.paraisopolis.SistemaGestao.exception.InvalidPasswordException;
import br.com.paraisopolis.SistemaGestao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(Usuario user){
        return usuarioRepository.save(user);
    }

    public UserDetails authenticate(Usuario user){
        UserDetails details = this.loadUserByUsername(user.getUsername());
        if(passwordEncoder.matches(user.getSenha(), details.getPassword())){
            return details;
        }
        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(user.getUsername())
                .password(user.getSenha())
                .roles(roles)
                .build();
    }

}
