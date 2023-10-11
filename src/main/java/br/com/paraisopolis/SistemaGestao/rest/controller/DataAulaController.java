package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.DataAula;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.SalvarAlunoRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.SalvarDataRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.DataAulaResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.SerieResponseDTO;
import br.com.paraisopolis.SistemaGestao.service.impl.DataAulaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/datas")
@CrossOrigin(origins = {"*"})
@Tag(name = "Datas", description = "API de Cadastro de Datas das Atividades")
public class DataAulaController {

    @Autowired
    private DataAulaServiceImpl service;

    @GetMapping
    @ApiOperation("Listar Datas")
    @Tag(name = "Datas")
    public ResponseEntity listAllDatas() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<DataAulaResponseDTO> datas = this.service.listAll().stream().map(d -> {
                    return DataAulaResponseDTO.builder()
                            .id(d.getId())
                            .date(sdf.format(d.getDataAula()))
                            .domingo(d.getDomingo())
                            .build();
                }
        ).collect(Collectors.toList());

        return ResponseEntity.ok(datas);
    }

    @PostMapping
    @ApiOperation("Salvar nova Data")
    @Tag(name = "Datas")
    public ResponseEntity saveData(@RequestBody SalvarDataRequestDTO request) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(request.getDate());
            cal.add(Calendar.HOUR, 12);

            DataAula d = this.service.save(DataAula.builder()
                    .id(request.getId())
                    .dataAula(cal.getTime())
                    .domingo(request.getDomingo())
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

}
