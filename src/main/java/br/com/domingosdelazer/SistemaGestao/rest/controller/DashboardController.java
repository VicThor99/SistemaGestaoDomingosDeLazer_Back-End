package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.Aluno;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.AlunoDashboardResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.DadosGraficoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.DashboardResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.ParametrosDomingoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.DataAulaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.SerieServiceImpl;
import br.com.domingosdelazer.SistemaGestao.entity.DataAula;
import br.com.domingosdelazer.SistemaGestao.repository.RegistroPresencasRepository;
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

        Optional<DataAula> dataAulaA = this.dataAulaService.getProximaAula("A");
        Optional<DataAula> dataAulaB = this.dataAulaService.getProximaAula("B");

        List<DadosGraficoResponseDTO> graficoA = this.service.getPresencas("A", dataAulaA.orElse(null));
        List<DadosGraficoResponseDTO> graficoB = this.service.getPresencas("B", dataAulaB.orElse(null));

        String proxDomA = dataAulaA.map(aula -> sdf.format(aula.getDataAula())).orElse("Acabaram as aulas!");
        String proxDomB = dataAulaB.map(aula -> sdf.format(aula.getDataAula())).orElse("Acabaram as aulas!");

        DashboardResponseDTO responseDTO = DashboardResponseDTO.builder()
                .domingoa(domA)
                .domingob(domB)
                .domingos(domTodos)
                .dadosGraficoA(graficoA)
                .dadosGraficoB(graficoB)
                .proximaDataDomA(proxDomA)
                .proximaDataDomB(proxDomB)
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{filtro}")
    @ApiOperation("Listar alunos do Dashboard")
    @Tag(name = "Dashboard")
    public ResponseEntity getDashboardLista(@PathVariable("filtro") String filtro) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<Aluno> alunos = this.service.getDashboardLista(filtro);
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
                        .sala(a.getSerie().getSala())
                        .nascimento(sdf.format(a.getNascimento()))
                        .build();
            }).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        }
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
