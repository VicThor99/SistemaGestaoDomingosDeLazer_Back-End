package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.Usuario;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoSacolinhaResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.CadastroUsuarioResponseDTO;
import br.com.paraisopolis.SistemaGestao.service.impl.AlunoServiceImpl;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = { "*" })
@Tag(name = "Alunos", description = "API de Cadastro de Alunos")
public class AlunoController {

    @Autowired
    private AlunoServiceImpl service;

    @GetMapping
    @ApiOperation("Listar Alunos")
    @Tag(name = "Alunos")
    public ResponseEntity getTodosAlunos() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<AlunoResponseDTO> alunos = service.listAllAlunos(false).stream().map(a ->{
            return AlunoResponseDTO.builder()
                    .nome(a.getNome())
                    .codigo(a.getCodigo())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .nascimento(sdf.format(a.getNascimento()))
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/sacolinha")
    @ApiOperation("Listar Alunos Aptos a Receber Sacolinha")
    @Tag(name = "Alunos")
    public ResponseEntity getAptosASacolinha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<AlunoSacolinhaResponseDTO> alunos = service.listAllAlunos(true).stream().map(a ->{
            return AlunoSacolinhaResponseDTO.builder()
                    .nome(a.getNome())
                    .codigo(a.getCodigo())
                    .nascimento(sdf.format(a.getNascimento()))
                    .sapato(a.getSapato())
                    .calca(a.getCalca())
                    .camisa(a.getCamisa())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

}
