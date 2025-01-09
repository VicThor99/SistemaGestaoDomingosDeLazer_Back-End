package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Usuario;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.LoginRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.LoginResponseDTO;
import br.com.domingosdelazer.SistemaGestao.exception.InvalidPasswordException;
import br.com.domingosdelazer.SistemaGestao.security.jwt.JWTService;
import br.com.domingosdelazer.SistemaGestao.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "API de Autenticação de Usuários")
public class AuthenticationController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioServiceImpl userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping
    @ApiOperation(value = "Autenticar Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário autenticado!"),
            @ApiResponse(code = 404, message = "Usuário não encontrado!")
    })
    @Tag(name = "Authentication")
    public ResponseEntity login(@RequestBody LoginRequestDTO request) {
        try {
            Usuario user = Usuario.builder()
                    .username(request.getUsername())
                    .senha(request.getSenha())
                    .build();

            UserDetails details = userService
                    .authenticate(user);

            user.setAdmin(userService.userAdministrador(user));
            user.setNome(userService.getUserName(user.getUsername()));

            return ResponseEntity.ok(LoginResponseDTO.builder()
                    .token(jwtService.generateToken(user))
                    .username(user.getNome())
                    .admin(user.isAdmin())
                    .build());
        } catch (UsernameNotFoundException | InvalidPasswordException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
