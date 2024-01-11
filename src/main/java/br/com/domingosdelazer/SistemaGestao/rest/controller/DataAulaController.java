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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datas")
@CrossOrigin(origins = {"*"})
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<DataAulaResponseDTO> datas = this.service.listAll(escolaId).stream().map(d -> {
                    return DataAulaResponseDTO.builder()
                            .id(d.getId())
                            .date(sdf.format(d.getDataAula()))
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(request.getDate());
            cal.add(Calendar.HOUR, 12);

            DataAula d = this.service.save(DataAula.builder()
                    .id(request.getId())
                    .dataAula(cal.getTime())
                    .domingo(request.getDomingo())
                    .escola(this.escolaService.getEscolaById(escolaId))
                    .build());

            return ResponseEntity.ok(DataAulaResponseDTO.builder()
                    .id(d.getId())
                    .date(sdf.format(d.getDataAula()))
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
                    if (data.after(new Date())) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(data);
                        cal.set(Calendar.HOUR, 12);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        DataAula dataAula = DataAula.builder()
                                .dataAula(cal.getTime())
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
                    if (data.after(new Date())) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(data);
                        cal.set(Calendar.HOUR, 12);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        DataAula dataAula = DataAula.builder()
                                .dataAula(cal.getTime())
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
                    if (data.after(new Date())) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(data);
                        cal.set(Calendar.HOUR, 12);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        DataAula dataAula = DataAula.builder()
                                .dataAula(cal.getTime())
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
                    if (data.after(new Date())) {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(data);
                        cal.set(Calendar.HOUR, 12);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        DataAula dataAula = DataAula.builder()
                                .dataAula(cal.getTime())
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
