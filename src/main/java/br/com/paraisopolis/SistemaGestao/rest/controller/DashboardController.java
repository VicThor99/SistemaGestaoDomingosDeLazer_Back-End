package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.DataAula;
import br.com.paraisopolis.SistemaGestao.entity.RegistroPresencas;
import br.com.paraisopolis.SistemaGestao.entity.Serie;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.AlunoRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.ImportRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.*;
import br.com.paraisopolis.SistemaGestao.repository.RegistroPresencasRepository;
import br.com.paraisopolis.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.paraisopolis.SistemaGestao.service.impl.DataAulaServiceImpl;
import br.com.paraisopolis.SistemaGestao.service.impl.SerieServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"*"})
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

    @GetMapping
    @ApiOperation("Retornar o Dashboard")
    @Tag(name = "Dashboard")
    public ResponseEntity getDashboardInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ParametrosDomingoResponseDTO domA = ParametrosDomingoResponseDTO.builder()
                .total(this.service.countAlunosTotal("A"))
                .aptas(this.service.countAlunosAptosASacolinha("A"))
                .risco(this.service.countAlunosEmRisco("A"))
                .build();

        ParametrosDomingoResponseDTO domB = ParametrosDomingoResponseDTO.builder()
                .total(this.service.countAlunosTotal("B"))
                .aptas(this.service.countAlunosAptosASacolinha("B"))
                .risco(this.service.countAlunosEmRisco("B"))
                .build();

        ParametrosDomingoResponseDTO domTodos = ParametrosDomingoResponseDTO.builder()
                .total(domA.getTotal() + domB.getTotal())
                .aptas(domA.getAptas() + domB.getAptas())
                .risco(domA.getRisco() + domB.getRisco())
                .build();

        List<DadosGraficoResponseDTO> graficoA = this.service.getPresencas("A");
        List<DadosGraficoResponseDTO> graficoB = this.service.getPresencas("B");
        List<DadosGraficoResponseDTO> graficoTotal = this.getPresencasTotal(graficoA, graficoB);

        Optional<DataAula> dataAulaA = this.dataAulaService.getProximaAula("A");
        Optional<DataAula> dataAulaB = this.dataAulaService.getProximaAula("B");

        String proxDomA = dataAulaA.map(aula -> sdf.format(aula.getDataAula())).orElse("Acabaram as aulas!");
        String proxDomB = dataAulaB.map(dataAula -> sdf.format(dataAula.getDataAula())).orElse("Acabaram as aulas!");

        DashboardResponseDTO responseDTO = DashboardResponseDTO.builder()
                .domingoa(domA)
                .domingob(domB)
                .domingos(domTodos)
                .dadosGraficoA(graficoA)
                .dadosGraficoB(graficoB)
                .dadosGraficoTotal(graficoTotal)
                .proximaDataDomA(proxDomA)
                .proximaDataDomB(proxDomB)
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    private List<DadosGraficoResponseDTO> getPresencasTotal(List<DadosGraficoResponseDTO> graficoA, List<DadosGraficoResponseDTO> graficoB) {

        List<DadosGraficoResponseDTO> lista = new ArrayList<>();

        graficoA.forEach(a -> lista.add(DadosGraficoResponseDTO.builder().label(a.getLabel()).y(a.getY()).build()));

        graficoB.forEach(b -> {
            for(DadosGraficoResponseDTO dados : lista){
                if(dados.getLabel().equalsIgnoreCase(b.getLabel())){
                    dados.setY(dados.getY() + b.getY());
                }
            }
        });

        return lista;
    }

}
