package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.PlanoAulaRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.SerieResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.PlanoAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.SalaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.SerieServiceImpl;
import com.sun.xml.bind.v2.TODO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save/{escolaId}")
    @ApiOperation("Salvar Plano de Aula")
    @Tag(name = "Plano de Aula")
    public ResponseEntity save(@RequestBody PlanoAulaRequestDTO request, @PathVariable Integer escolaId) {
        try {
            this.service.verificarOuSalvar(request, escolaId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
