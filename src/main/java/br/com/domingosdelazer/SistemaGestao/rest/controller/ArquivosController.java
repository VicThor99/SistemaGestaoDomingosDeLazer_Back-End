package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.ArquivosServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/arquivos")
@Tag(name = "Arquivos", description = "API de Cadastro de Arquivos")
public class ArquivosController {

    @Autowired
    private AlunoServiceImpl alunoService;

    @Autowired
    private ArquivosServiceImpl service;

    @GetMapping(value = "/foto/{idAluno}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("Retornar foto de um Aluno")
    @Tag(name = "Arquivos")
    public ResponseEntity getAlunoFoto(@PathVariable Integer idAluno) {
        try {
            Aluno aluno = this.alunoService.getAlunoById(idAluno);

            if (aluno != null) {
                Blob blob = this.service.getArquivosById(aluno.getArquivos().getId()).getFoto();
                if (blob != null) {
                    String base64 = Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length()));
                    return ResponseEntity.ok(base64);
                } else {
                    return ResponseEntity.ok().build();
                }
            }
            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/matricula/{idAluno}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ApiOperation("Retornar foto da matricula de um Aluno")
    @Tag(name = "Arquivos")
    public ResponseEntity getAlunoMatricula(@PathVariable Integer idAluno) {
        try {
            Aluno aluno = this.alunoService.getAlunoById(idAluno);

            if (aluno != null) {
                Blob blob = this.service.getArquivosById(aluno.getArquivos().getId()).getMatricula();
                if (blob != null) {
                    String base64 = Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length()));
                    return ResponseEntity.ok(base64);
                } else {
                    return ResponseEntity.ok().build();
                }
            }

            return ResponseEntity.ok().build();
        } catch (SQLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/foto/{codigoAluno}/{escolaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("Salvar novo foto de Aluno")
    @Tag(name = "Arquivos")
    public ResponseEntity saveFoto(@PathVariable Integer codigoAluno, @PathVariable Integer
            escolaId, @RequestParam("image") MultipartFile image) {
        try {
            Aluno aluno = this.alunoService.getAlunoByCodigo(String.valueOf(codigoAluno), escolaId);
            if (aluno != null) {
                aluno.getArquivos().setFoto(new SerialBlob(image.getBytes()));
                this.alunoService.save(aluno);
            }
            return ResponseEntity.ok("Foto adicionada com sucesso!");
        } catch (IOException | SQLException e) {
                        e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/matricula/{codigoAluno}/{escolaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation("Salvar novo foto de Matricula")
    @Tag(name = "Arquivos")
    public ResponseEntity saveMatricula(@PathVariable Integer codigoAluno, @PathVariable Integer
            escolaId, @RequestPart MultipartFile image) {
        try {
            Aluno aluno = this.alunoService.getAlunoByCodigo(String.valueOf(codigoAluno), escolaId);
            if (aluno != null) {
                aluno.getArquivos().setMatricula(new SerialBlob(image.getBytes()));
                this.alunoService.save(aluno);
            }
            return ResponseEntity.ok("Matricula adicionada com sucesso!");
        } catch (IOException | SQLException e) {
                        e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
