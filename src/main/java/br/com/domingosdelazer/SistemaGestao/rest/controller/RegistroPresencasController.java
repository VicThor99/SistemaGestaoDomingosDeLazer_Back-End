package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.RegistroPresencas;
import br.com.domingosdelazer.SistemaGestao.entity.enums.EnumPresencas;
import br.com.domingosdelazer.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.RegistroPresencaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.CorrecaoRegistroRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.RegistroRequestDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.DataAulaServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registros")
@Tag(name = "Presenças", description = "API de Registro de Presenças")
public class RegistroPresencasController {

    @Autowired
    private RegistroPresencaServiceImpl service;

    @Autowired
    private DataAulaServiceImpl dataAulaService;

    @Autowired
    private AlunoServiceImpl alunoService;

    @GetMapping("/{escolaId}")
    @ApiOperation("Listar Presenças")
    @Tag(name = "Presenças")
    public ResponseEntity listAllRegistros(@PathVariable Integer escolaId) {
        return ResponseEntity.ok(service.getListaRegistros(escolaId));
    }

    @GetMapping("/{codigo}/{escolaId}")
    @ApiOperation("Listar Presenças por Código do Aluno")
    @Tag(name = "Presenças")
    public ResponseEntity getRegistrosPorCodigoAluno(@PathVariable String codigo, @PathVariable Integer escolaId) {
        return ResponseEntity.ok(service.getRegistrosPorCodigoAluno(codigo, escolaId));
    }

    @PostMapping("/{codigo}/{escolaId}")
    @ApiOperation("Registrar Presença para Aluno")
    @Tag(name = "Presenças")
    public ResponseEntity adjustPresences(@PathVariable String codigo, @PathVariable Integer escolaId,
                                          @RequestBody CorrecaoRegistroRequestDTO request) {
        if (StringUtils.isEmpty(codigo)) {
            return ResponseEntity.badRequest().body("Código do Aluno não foi preenchido!");
        }

        RegistroPresencas registroPresencas = this.service.getRegistroByCodigoAluno(codigo, escolaId);
        ajustarPresencas(registroPresencas, request);

        return ResponseEntity.ok(this.service.save(registroPresencas));
    }

    @PostMapping("/leitor/{escolaId}")
    @ApiOperation("Registrar Presença para Lista de Alunos")
    @Tag(name = "Presenças")
    public ResponseEntity registerReaderPresences(@RequestBody RegistroRequestDTO request, @PathVariable Integer escolaId) {
        try {
            if (request.getCodigos() == null || request.getCodigos().isEmpty()) {
                throw new Exception("Lista de Alunos está vazia!");
            }

            DataAula dataAula = this.dataAulaService.getAulaParaPresenca(request.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), escolaId);

            List<String> codigos = request.getCodigos().stream().distinct().collect(Collectors.toList());

            this.service.darPresencaParaLista(codigos, dataAula, escolaId);

            return ResponseEntity.ok("Foram dadas presenças a " + codigos.size()
                    + " crianças na aula do dia " + dataAula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/celular/{escolaId}")
    @ApiOperation("Registrar Presença para Aluno")
    @Tag(name = "Presenças")
    public ResponseEntity registerCellphonePresence(@RequestBody List<String> request, @PathVariable Integer escolaId) {
        try {
            DataAula dataAula = this.dataAulaService.getAulaParaPresenca(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")), escolaId);

            List<String> codigos = request.stream().distinct().collect(Collectors.toList());

            this.service.darPresencaParaLista(codigos, dataAula, escolaId);

            return ResponseEntity.ok("Foram dadas presenças a " + codigos.size()
                    + " crianças na aula do dia " + dataAula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void ajustarPresencas(RegistroPresencas registroPresencas, CorrecaoRegistroRequestDTO request) {
        registroPresencas.setFevereiro(transformarEmEnum(request.getFevereiro()));
        registroPresencas.setMarco(transformarEmEnum(request.getMarco()));
        registroPresencas.setAbril(transformarEmEnum(request.getAbril()));
        registroPresencas.setMaio(transformarEmEnum(request.getMaio()));
        registroPresencas.setJunho(transformarEmEnum(request.getJunho()));
        registroPresencas.setAgosto(transformarEmEnum(request.getAgosto()));
        registroPresencas.setSetembro(transformarEmEnum(request.getSetembro()));
        registroPresencas.setOutubro(transformarEmEnum(request.getOutubro()));
        registroPresencas.setNovembro(transformarEmEnum(request.getNovembro()));
    }

    private EnumPresencas transformarEmEnum(String request) {
        switch (request) {
            case "":
                return EnumPresencas.N;
            case "Presença":
            case "P":
                return EnumPresencas.P;
            case "Manual":
            case "M":
                return EnumPresencas.M;
            case "Atestado":
            case "A":
                return EnumPresencas.A;
            case "Esqueceu o Crachá":
            case "E":
                return EnumPresencas.E;
            default:
                return EnumPresencas.F;
        }
    }

    private void darPresenca(RegistroPresencas registroPresencas, LocalDate date) throws Exception {
        switch (date.getMonth()) {
            case FEBRUARY:
                registroPresencas.setFevereiro(EnumPresencas.P);
                break;
            case MARCH:
                registroPresencas.setMarco(EnumPresencas.P);
                break;
            case APRIL:
                registroPresencas.setAbril(EnumPresencas.P);
                break;
            case MAY:
                registroPresencas.setMaio(EnumPresencas.P);
                break;
            case JUNE:
                registroPresencas.setJunho(EnumPresencas.P);
                break;
            case AUGUST:
                registroPresencas.setAgosto(EnumPresencas.P);
                break;
            case SEPTEMBER:
                registroPresencas.setSetembro(EnumPresencas.P);
                break;
            case OCTOBER:
                registroPresencas.setOutubro(EnumPresencas.P);
                break;
            case NOVEMBER:
                registroPresencas.setNovembro(EnumPresencas.P);
                break;
        }
    }

}
