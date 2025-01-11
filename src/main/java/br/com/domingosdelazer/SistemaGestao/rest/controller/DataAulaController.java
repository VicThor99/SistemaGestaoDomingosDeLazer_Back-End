package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Escola;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.SalvarDataRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.DataAulaResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.DatasEmMassaRequestDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.DataAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datas")
@Tag(name = "Datas", description = "API de Cadastro de Datas das Atividades")
public class DataAulaController {

    @Autowired
    private DataAulaServiceImpl service;

    @Autowired
    private EscolaServiceImpl escolaService;

    @GetMapping("{escolaId}")
    @ApiOperation("Listar Datas")
    @Tag(name = "Datas")
    public ResponseEntity listAllDatas(@PathVariable Integer escolaId) {
        List<DataAulaResponseDTO> datas = this.service.listAll(escolaId).stream().map(d -> {
                    return DataAulaResponseDTO.builder()
                            .id(d.getId())
                            .date(d.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            .domingo(d.getDomingo())
                            .build();
                }
        ).collect(Collectors.toList());

        return ResponseEntity.ok(datas);
    }

    @PostMapping("/{escolaId}")
    @ApiOperation("Salvar nova Data")
    @Tag(name = "Datas")
    public ResponseEntity saveData(@RequestBody SalvarDataRequestDTO request, @PathVariable Integer escolaId) {
        try {
            DataAula d = this.service.save(DataAula.builder()
                    .id(request.getId())
                    .dataAula(request.getDate())
                    .domingo(request.getDomingo())
                    .escola(this.escolaService.getEscolaById(escolaId))
                    .build());

            return ResponseEntity.ok(DataAulaResponseDTO.builder()
                    .id(d.getId())
                    .date(d.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .domingo(d.getDomingo())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/emMassa/{escolaId}")
    @ApiOperation("Cadastrar Datas em Massa")
    @Tag(name = "Datas")
    public ResponseEntity cadastrarDatasEmMassa(@RequestBody DatasEmMassaRequestDTO json, @PathVariable Integer escolaId) {
        try {
            if ((json.getDatasDomA() == null || json.getDatasDomA().isEmpty()) &&
                    (json.getDatasDomB() == null || json.getDatasDomB().isEmpty()) &&
                    (json.getDatasDomC() == null || json.getDatasDomC().isEmpty()) &&
                    (json.getDatasDomD() == null || json.getDatasDomD().isEmpty())) {
                throw new Exception("Listas de Datas não podem estar nulas ou vazias");
            }

            Escola escola = this.escolaService.getEscolaById(escolaId);

            AtomicInteger contador = new AtomicInteger();

            if (json.getDatasDomA() != null && !json.getDatasDomA().isEmpty()) {
                json.getDatasDomA().forEach(data -> {
                    if (data.isAfter(LocalDate.now())) {
                        DataAula dataAula = DataAula.builder()
                                .dataAula(data)
                                .domingo("A")
                                .escola(escola)
                                .build();
                        this.service.save(dataAula);
                        contador.getAndIncrement();
                    }
                });
            }

            if (json.getDatasDomB() != null && !json.getDatasDomB().isEmpty()) {
                json.getDatasDomB().forEach(data -> {
                    if (data.isAfter(LocalDate.now())) {
                        DataAula dataAula = DataAula.builder()
                                .dataAula(data)
                                .domingo("B")
                                .escola(escola)
                                .build();
                        this.service.save(dataAula);
                        contador.getAndIncrement();
                    }
                });
            }

            if (json.getDatasDomC() != null && !json.getDatasDomC().isEmpty()) {
                json.getDatasDomC().forEach(data -> {
                    if (data.isAfter(LocalDate.now())) {
                        DataAula dataAula = DataAula.builder()
                                .dataAula(data)
                                .domingo("C")
                                .escola(escola)
                                .build();
                        this.service.save(dataAula);
                        contador.getAndIncrement();
                    }
                });
            }

            if (json.getDatasDomD() != null && !json.getDatasDomD().isEmpty()) {
                json.getDatasDomD().forEach(data -> {
                    if (data.isAfter(LocalDate.now())) {
                        DataAula dataAula = DataAula.builder()
                                .dataAula(data)
                                .domingo("D")
                                .escola(escola)
                                .build();
                        this.service.save(dataAula);
                        contador.getAndIncrement();
                    }
                });
            }

            return ResponseEntity.ok(contador.get() + " datas foram cadastradas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
