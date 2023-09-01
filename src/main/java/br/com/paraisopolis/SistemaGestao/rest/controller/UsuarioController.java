package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.CadastroUsuarioResponseDTO;
import br.com.paraisopolis.SistemaGestao.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = { "*" })
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
                        .cargo(usuario.isAdmin() ? "Administrador" : "Comum")
                        .build());
    }

}
