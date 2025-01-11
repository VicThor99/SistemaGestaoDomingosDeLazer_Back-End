package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.entity.Usuario;
import br.com.domingosdelazer.SistemaGestao.entity.UsuarioAcesso;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.UsuarioAcessoRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.CadastroUsuarioResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.UsuarioResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.UsuarioAcessoServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.UsuarioServiceImpl;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/access")
@Tag(name = "Acessos", description = "API de Cadastro de Acessos do Usuários")
public class UsuarioAcessoController {

    @Autowired
    private UsuarioAcessoServiceImpl service;

    @Autowired
    private UsuarioServiceImpl userService;

    @Autowired
    private EscolaServiceImpl escolaService;

    @PostMapping
    @ApiOperation("Salvar Acesso")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Acesso salvo!"),
            @ApiResponse(code = 404, message = "Erro de validação!")
    })
    @Tag(name = "Acessos")
    public ResponseEntity save(@RequestBody UsuarioAcessoRequestDTO accessDTO) {
        try {
            if (accessDTO.getEscolaId() == null)
                throw new Exception("Escola não pode ser nulo");

            if (accessDTO.getUsuarioId() == null)
                throw new Exception("Usuário não pode ser nulo");

            Escola escola = this.escolaService.getEscolaById(accessDTO.getEscolaId());
            Optional<Usuario> usuario = this.userService.getUserById(accessDTO.getUsuarioId());

            if (escola == null || !usuario.isPresent())
                throw new Exception("Escola ou Usuário inválidos!");

            if (accessDTO.getId() != null && accessDTO.getId() > 0) {
                return ResponseEntity.ok(this.service.save(UsuarioAcesso
                        .builder()
                        .id(accessDTO.getId())
                        .escola(escola)
                        .usuario(usuario.get())
                        .build()));
            } else {
                return ResponseEntity.ok(this.service.save(UsuarioAcesso
                        .builder()
                        .id(0)
                        .escola(escola)
                        .usuario(usuario.get())
                        .build()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @ApiOperation("Listar Todos Acessos")
    @Tag(name = "Acessos")
    public ResponseEntity listAll() {
        return ResponseEntity.ok(this.service.listAll());
    }

    @GetMapping("/{username}")
    @ApiOperation("Listar Acessos do Usuário")
    @Tag(name = "Acessos")
    public ResponseEntity listAccessForUsuario(@PathVariable String username) {
        Usuario usuario = this.userService.getUserByName(username);
        return ResponseEntity.ok(this.service.listAccessForUser(usuario));
    }

}
