package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.dto.request.PlanoAulaRequestDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.PlanoAulaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/planoaula")
@Tag(name = "Plano de Aula", description = "API de Cadastro de Plano de Aula")
public class PlanoAulaController {

    @Autowired
    private PlanoAulaServiceImpl service;

    @GetMapping("/{escolaId}")
    @ApiOperation("Listar todos os Planos de Aulas")
    @Tag(name = "Plano de Aula")
    public ResponseEntity listAll(@PathVariable Integer escolaId) {
        try {
            return ResponseEntity.ok(this.service.listAll(escolaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{planoId}/{escolaId}")
    @ApiOperation("Resgatar um Plano de Aula pelo Id")
    @Tag(name = "Plano de Aula")
    public ResponseEntity getById(@PathVariable Integer planoId, @PathVariable Integer escolaId) {
        try {
            return ResponseEntity.ok(this.service.getById(planoId, escolaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{escolaId}")
    @ApiOperation("Salvar Plano de Aula")
    @Tag(name = "Plano de Aula")
    public ResponseEntity save(@RequestBody PlanoAulaRequestDTO request, @PathVariable Integer escolaId) {
        try {
            this.service.verificarOuSalvar(request, escolaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
