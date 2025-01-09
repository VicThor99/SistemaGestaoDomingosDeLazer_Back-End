package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.SerieResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.SerieServiceImpl;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.SeriesEmMassaRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/series")
@Tag(name = "Séries", description = "API de Cadastro de Séries")
public class SerieController {

    @Autowired
    private SerieServiceImpl service;

    @Autowired
    private EscolaServiceImpl escolaService;

    @GetMapping("/{escolaId}")
    @ApiOperation("Listar Séries")
    @Tag(name = "Séries")
    public ResponseEntity listAllSeries(@PathVariable Integer escolaId) {
        List<SerieResponseDTO> series = this.service.listAll(escolaId).stream().map(s -> {
                return SerieResponseDTO.builder()
                        .id(s.getId())
                        .serie(s.getSerie())
                        .sala(s.getSala())
                        .domingo(s.getDomingo())
                        .build();
                }
        ).collect(Collectors.toList());

        return ResponseEntity.ok(series);
    }

    @PostMapping("/{escolaId}")
    @ApiOperation("Salvar nova Série")
    @Tag(name = "Séries")
    public ResponseEntity saveDataSerie(@RequestBody Serie serie, @PathVariable Integer escolaId) {
        try {
            serie.setEscola(this.escolaService.getEscolaById(escolaId));
            return ResponseEntity.ok(this.service.save(serie));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listaString/{escolaId}")
    @ApiOperation("Listar Séries como String")
    @Tag(name = "Séries")
    public ResponseEntity listSeriesString(@PathVariable Integer escolaId) {
        try {
            return ResponseEntity.ok(this.service.listSeriesString(escolaId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listaStringSalas/{escolaId}")
    @ApiOperation("Listar Salas como String")
    @Tag(name = "Séries")
    public ResponseEntity listSalasString(@PathVariable Integer escolaId) {
        try {
            return ResponseEntity.ok(this.service.listSalasString(escolaId).stream()
                    .sorted(Comparator.comparingInt(o -> Integer.parseInt(o.split(" ")[1])))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
