package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.RegistroPresencas;
import br.com.paraisopolis.SistemaGestao.entity.Serie;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.AlunoRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.ImportRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoSacolinhaResponseDTO;
import br.com.paraisopolis.SistemaGestao.repository.RegistroPresencasRepository;
import br.com.paraisopolis.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.paraisopolis.SistemaGestao.service.impl.SerieServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alunos")
@CrossOrigin(origins = {"*"})
@Tag(name = "Alunos", description = "API de Cadastro de Alunos")
public class AlunoController {

    @Autowired
    private AlunoServiceImpl service;

    @Autowired
    private SerieServiceImpl serieService;

    @Autowired
    private RegistroPresencasRepository registroService;

    @GetMapping
    @ApiOperation("Listar Alunos")
    @Tag(name = "Alunos")
    public ResponseEntity getTodosAlunos() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<AlunoResponseDTO> alunos = service.listAllAlunos(false).stream().map(a -> {
            return AlunoResponseDTO.builder()
                    .nome(a.getNome())
                    .codigo(a.getCodigo())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .nascimento(sdf.format(a.getNascimento()))
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/sacolinha")
    @ApiOperation("Listar Alunos Aptos a Receber Sacolinha")
    @Tag(name = "Alunos")
    public ResponseEntity getAptosASacolinha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<AlunoSacolinhaResponseDTO> alunos = service.listAllAlunos(true).stream().map(a -> {
            return AlunoSacolinhaResponseDTO.builder()
                    .nome(a.getNome())
                    .codigo(a.getCodigo())
                    .idade(calcularIdade(a.getNascimento()))
                    .nascimento(sdf.format(a.getNascimento()))
                    .sapato(a.getSapato())
                    .calca(a.getCalca())
                    .camisa(a.getCamisa())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

    @PostMapping("/import")
    @ApiOperation("Importar lista de Alunos passada pela escola")
    @Tag(name = "Alunos")
    public ResponseEntity importarDadosLista(@RequestBody ImportRequestDTO request) {
        try {
            List<String> alunos = new ArrayList<>();
            int codigoDomA = 100001;
            int codigoDomB = 200001;
            for (AlunoRequestDTO aluno : request.getAlunos()) {
                Serie serie = this.serieService
                        .verificarOuSalvar(aluno.getSerie(), aluno.getSala(), aluno.getDomingo());

                String codigo = serie.getDomingo().equalsIgnoreCase("A") ? "" + codigoDomA++ : "" + codigoDomB++;
                RegistroPresencas registro = this.registroService.save(RegistroPresencas.builder().id(0).build());

                Aluno a = this.service.save(Aluno.builder()
                        .nome(aluno.getNome())
                        .codigo(codigo)
                        .nascimento(aluno.getNascimento())
                        .serie(serie)
                        .registroPresencas(registro)
                        .build());

                alunos.add(a.getNome());
            }

            return ResponseEntity.ok(alunos);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Integer calcularIdade(Date nascimento) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dataAtual = new Date();
        return Integer.parseInt(sdf.format(dataAtual)) - Integer.parseInt(sdf.format(nascimento));
    }

}
