package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Serie;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.SerieResponseDTO;
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
@CrossOrigin(origins = {"*"})
@Tag(name = "Séries", description = "API de Cadastro de Séries")
public class SerieController {

    @Autowired
    private SerieServiceImpl service;

    @GetMapping
    @ApiOperation("Listar Séries")
    @Tag(name = "Séries")
    public ResponseEntity listAllSeries() {
        List<SerieResponseDTO> series = this.service.listAll().stream().map(s -> {
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

    @PostMapping
    @ApiOperation("Salvar nova Série")
    @Tag(name = "Séries")
    public ResponseEntity saveDataSerie(@RequestBody Serie serie) {
        try {
            return ResponseEntity.ok(this.service.save(serie));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listaString")
    @ApiOperation("Listar Séries como String")
    @Tag(name = "Séries")
    public ResponseEntity listSeriesString() {
        try {
            return ResponseEntity.ok(this.service.listSeriesString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/listaStringSalas")
    @ApiOperation("Listar Salas como String")
    @Tag(name = "Séries")
    public ResponseEntity listSalasString() {
        try {
            return ResponseEntity.ok(this.service.listSalasString().stream()
                    .sorted(Comparator.comparingInt(o -> Integer.parseInt(o.split(" ")[1])))
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/emMassa")
    @ApiOperation("Cadastrar Séries em Massa")
    @Tag(name = "Séries")
    public ResponseEntity cadastrarSeriesEmMassa(@RequestBody SeriesEmMassaRequestDTO json) {
        try {
            if((json.getSeriesDomA() == null || json.getSeriesDomA().isEmpty()) &&
                    (json.getSeriesDomB() == null || json.getSeriesDomB().isEmpty()) &&
                    (json.getSeriesDomC() == null || json.getSeriesDomC().isEmpty()) &&
                    (json.getSeriesDomD() == null || json.getSeriesDomD().isEmpty())) {
                throw new Exception("Listas de Datas não podem estar nulas ou vazias");
            }

            AtomicInteger contador = new AtomicInteger();

            if(json.getSeriesDomA() != null && !json.getSeriesDomA().isEmpty()){
                json.getSeriesDomA().forEach(dto -> {
                    Serie serie = Serie.builder()
                            .serie(dto.getSerie())
                            .sala(dto.getSala())
                            .domingo("A")
                            .build();
                    this.service.save(serie);
                    contador.getAndIncrement();
                });
            }

            if(json.getSeriesDomB() != null && !json.getSeriesDomB().isEmpty()){
                json.getSeriesDomB().forEach(dto -> {
                    Serie serie = Serie.builder()
                            .serie(dto.getSerie())
                            .sala(dto.getSala())
                            .domingo("B")
                            .build();
                    this.service.save(serie);
                    contador.getAndIncrement();
                });
            }

            if(json.getSeriesDomC() != null && !json.getSeriesDomC().isEmpty()){
                json.getSeriesDomC().forEach(dto -> {
                    Serie serie = Serie.builder()
                            .serie(dto.getSerie())
                            .sala(dto.getSala())
                            .domingo("C")
                            .build();
                    this.service.save(serie);
                    contador.getAndIncrement();
                });
            }

            if(json.getSeriesDomD() != null && !json.getSeriesDomD().isEmpty()){
                json.getSeriesDomD().forEach(dto -> {
                    Serie serie = Serie.builder()
                            .serie(dto.getSerie())
                            .sala(dto.getSala())
                            .domingo("D")
                            .build();
                    this.service.save(serie);
                    contador.getAndIncrement();
                });
            }

            return ResponseEntity.ok(contador.get() + " séries foram cadastradas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
