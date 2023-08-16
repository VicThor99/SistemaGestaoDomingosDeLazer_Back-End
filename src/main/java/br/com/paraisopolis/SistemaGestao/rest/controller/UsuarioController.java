package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.LoginRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.CadastroUsuarioResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.LoginResponseDTO;
import br.com.paraisopolis.SistemaGestao.exception.InvalidPasswordException;
import br.com.paraisopolis.SistemaGestao.security.jwt.JWTService;
import br.com.paraisopolis.SistemaGestao.service.impl.UsuarioServiceImpl;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "*" })
@Tag(name = "Authentication", description = "API de Autenticação de Usuários")
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioServiceImpl userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping
    @ApiOperation("Salvar Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário salvo!"),
            @ApiResponse(code = 404, message = "Erro de validação!")
    })
    @Tag(name = "Authentication")
    public ResponseEntity save(@RequestBody @Valid Usuario user) {
        String codificatedPassword = passwordEncoder.encode(user.getSenha());
        user.setSenha(codificatedPassword);
        Usuario usuario = userService.save(user);

        return ResponseEntity.ok(CadastroUsuarioResponseDTO.builder()
                        .id(usuario.getId())
                        .username(usuario.getUsername())
                        .email(usuario.getEmail())
                        .cargo(usuario.isAdmin() ? "Administrador" : "Comum")
                        .build());
    }

    @PostMapping("/auth")
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

            return ResponseEntity.ok(LoginResponseDTO.builder()
                    .token(jwtService.generateToken(user))
                    .username(user.getUsername())
                    .build());
        } catch (UsernameNotFoundException | InvalidPasswordException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
