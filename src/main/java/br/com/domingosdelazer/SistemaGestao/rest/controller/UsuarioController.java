package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Usuario;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.CadastroUsuarioResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.UsuarioServiceImpl;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.UsuarioResponseDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "https://www.domingodelazer.click", allowedHeaders = "*")
@RequestMapping("/api/users")
@Tag(name = "Usuário", description = "API de Cadastro de Usuários")
public class UsuarioController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioServiceImpl userService;

    @PostMapping
    @ApiOperation("Salvar Usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário salvo!"),
            @ApiResponse(code = 404, message = "Erro de validação!")
    })
    @Tag(name = "Usuário")
    public ResponseEntity save(@RequestBody @Valid Usuario user) {
        String codificatedPassword = passwordEncoder.encode(user.getSenha());
        user.setSenha(codificatedPassword);
        Usuario usuario = userService.save(user);

        return ResponseEntity.ok(CadastroUsuarioResponseDTO.builder()
                        .id(usuario.getId())
                        .username(usuario.getUsername())
                        .email(usuario.getEmail())
                        .cargo(usuario.isAdmin() ? "Administrador" : "Usuário Comum")
                        .build());
    }

    @GetMapping
    @ApiOperation("Listar Usuários")
    @Tag(name = "Usuário")
    public ResponseEntity listAllUsuario() {
        List<UsuarioResponseDTO> usuarios = this.userService.listAll().stream().map(user -> {
                    return UsuarioResponseDTO.builder()
                            .id(user.getId())
                            .nome(user.getNome())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .cargo(user.isAdmin() ? "Administrador" : "Usuário Comum")
                            .build();
                }
        ).collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

}
