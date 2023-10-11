package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.DataAula;
import br.com.paraisopolis.SistemaGestao.entity.RegistroPresencas;
import br.com.paraisopolis.SistemaGestao.entity.Serie;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.AlunoRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.ImportRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.SalvarDataRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoSacolinhaResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.DataAulaResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.SerieResponseDTO;
import br.com.paraisopolis.SistemaGestao.repository.RegistroPresencasRepository;
import br.com.paraisopolis.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.paraisopolis.SistemaGestao.service.impl.SerieServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

}
