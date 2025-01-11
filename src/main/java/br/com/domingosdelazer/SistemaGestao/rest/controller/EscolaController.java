package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.DatasEmMassaRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.SalvarDataRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.DataAulaResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.DataAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/escola")
@Tag(name = "Escolas", description = "API de Cadastro de Escolas")
public class EscolaController {

    @Autowired
    private EscolaServiceImpl service;

    @GetMapping
    @ApiOperation("Listar Escolas")
    @Tag(name = "Escolas")
    public ResponseEntity listAllEscolas() {
        return ResponseEntity.ok(this.service.listAll());
    }

    @PostMapping
    @ApiOperation("Salvar nova Escola")
    @Tag(name = "Escolas")
    public ResponseEntity save(@RequestBody Escola escola) {
            return ResponseEntity.ok(this.service.save(escola));
    }

}
