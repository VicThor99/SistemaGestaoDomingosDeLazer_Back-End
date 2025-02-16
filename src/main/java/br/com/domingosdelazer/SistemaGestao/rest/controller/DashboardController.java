package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.PlanoAula;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.*;
import br.com.domingosdelazer.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.DataAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.PlanoAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.SerieServiceImpl;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.repository.RegistroPresencasRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "API do Dashboard")
public class DashboardController {

    @Autowired
    private AlunoServiceImpl service;

    @Autowired
    private SerieServiceImpl serieService;

    @Autowired
    private DataAulaServiceImpl dataAulaService;

    @Autowired
    private RegistroPresencasRepository registroService;

    @Autowired
    private PlanoAulaServiceImpl planoAulaService;

    @GetMapping("/comuns/{escolaId}/{username}")
    @ApiOperation("Retornar o Dashboard para usuários comuns")
    @Tag(name = "Dashboard")
    public ResponseEntity getDashboardComuns(@PathVariable Integer escolaId, @PathVariable String username) {
        Optional<DataAula> dataAulaA = this.dataAulaService.getProximaAula("A", escolaId);
        Optional<DataAula> dataAulaB = this.dataAulaService.getProximaAula("B", escolaId);
        Optional<DataAula> dataAulaC = this.dataAulaService.getProximaAula("C", escolaId);
        Optional<DataAula> dataAulaD = this.dataAulaService.getProximaAula("D", escolaId);

        String proxDomA = dataAulaA.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");
        String proxDomB = dataAulaB.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");
        String proxDomC = dataAulaC.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");
        String proxDomD = dataAulaD.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");

        PlanoAulaResponseDTO planoAula = this.planoAulaService.getBySala(username,escolaId);

        DashboardResponseDTO responseDTO = DashboardResponseDTO.builder()
                .proximaDataDomA(proxDomA)
                .proximaDataDomB(proxDomB)
                .proximaDataDomC(proxDomC)
                .proximaDataDomD(proxDomD)
                .planoAula(planoAula != null ? planoAula : PlanoAulaResponseDTO.builder().mes("").build())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/admin/{escolaId}")
    @ApiOperation("Retornar o Dashboard para Administradores")
    @Tag(name = "Dashboard")
    public ResponseEntity getDashboardAdmin(@PathVariable Integer escolaId) {
        ParametrosDomingoResponseDTO domA = ParametrosDomingoResponseDTO.builder()
                .total(this.service.countAlunosTotal("A", escolaId))
                .aptas(this.service.countAlunosAptosASacolinha("A", escolaId))
                .risco(this.service.countAlunosEmRisco("A", escolaId))
                .build();

        ParametrosDomingoResponseDTO domB = ParametrosDomingoResponseDTO.builder()
                .total(this.service.countAlunosTotal("B", escolaId))
                .aptas(this.service.countAlunosAptosASacolinha("B", escolaId))
                .risco(this.service.countAlunosEmRisco("B", escolaId))
                .build();

        ParametrosDomingoResponseDTO domC = ParametrosDomingoResponseDTO.builder()
                .total(this.service.countAlunosTotal("C", escolaId))
                .aptas(this.service.countAlunosAptosASacolinha("C", escolaId))
                .risco(this.service.countAlunosEmRisco("C", escolaId))
                .build();

        ParametrosDomingoResponseDTO domD = ParametrosDomingoResponseDTO.builder()
                .total(this.service.countAlunosTotal("D", escolaId))
                .aptas(this.service.countAlunosAptosASacolinha("D", escolaId))
                .risco(this.service.countAlunosEmRisco("D", escolaId))
                .build();

        ParametrosDomingoResponseDTO domTodos = ParametrosDomingoResponseDTO.builder()
                .total(domA.getTotal() + domB.getTotal() + domC.getTotal() + domD.getTotal())
                .aptas(domA.getAptas() + domB.getAptas() + domC.getAptas() + domD.getAptas())
                .risco(domA.getRisco() + domB.getRisco() + domC.getRisco() + domD.getRisco())
                .build();

        Optional<DataAula> dataAulaA = this.dataAulaService.getProximaAula("A", escolaId);
        Optional<DataAula> dataAulaB = this.dataAulaService.getProximaAula("B", escolaId);
        Optional<DataAula> dataAulaC = this.dataAulaService.getProximaAula("C", escolaId);
        Optional<DataAula> dataAulaD = this.dataAulaService.getProximaAula("D", escolaId);

        List<DadosGraficoResponseDTO> graficoA = this.service.getPresencas("A", dataAulaA.orElse(null), escolaId);
        List<DadosGraficoResponseDTO> graficoB = this.service.getPresencas("B", dataAulaB.orElse(null), escolaId);
        List<DadosGraficoResponseDTO> graficoC = this.service.getPresencas("C", dataAulaC.orElse(null), escolaId);
        List<DadosGraficoResponseDTO> graficoD = this.service.getPresencas("D", dataAulaD.orElse(null), escolaId);

        String proxDomA = dataAulaA.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");
        String proxDomB = dataAulaB.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");
        String proxDomC = dataAulaC.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");
        String proxDomD = dataAulaD.map(aula -> aula.getDataAula().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("");

        DashboardResponseDTO responseDTO = DashboardResponseDTO.builder()
                .domingoA(domA)
                .domingoB(domB)
                .domingoC(domC)
                .domingoD(domD)
                .domingos(domTodos)
                .dadosGraficoA(graficoA)
                .dadosGraficoB(graficoB)
                .dadosGraficoC(graficoC)
                .dadosGraficoD(graficoD)
                .proximaDataDomA(proxDomA)
                .proximaDataDomB(proxDomB)
                .proximaDataDomC(proxDomC)
                .proximaDataDomD(proxDomD)
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{filtro}/{escolaId}")
    @ApiOperation("Listar alunos do Dashboard")
    @Tag(name = "Dashboard")
    public ResponseEntity getDashboardLista(@PathVariable("filtro") String filtro, @PathVariable Integer escolaId) {
        List<Aluno> alunos = this.service.getDashboardLista(filtro, escolaId);
        if(alunos == null || alunos.isEmpty()){
            return ResponseEntity.badRequest().body("Não foi possível retornar nenhum dado sobre esses alunos");
        } else {
            List<AlunoDashboardResponseDTO> response = alunos.stream().map(a -> {
                return AlunoDashboardResponseDTO.builder()
                        .id(a.getId())
                        .codigo(a.getCodigo())
                        .nome(a.getNome())
                        .sexo(a.getSexo())
                        .serie(a.getSerie().getSerie())
                        .sala(a.getSerie().getSala().getSala())
                        .nascimento(a.getNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .build();
            }).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        }
    }

}
