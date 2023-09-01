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
                List<String> listaSalasDomA = new ArrayList<>();
                adicionarSalas(listaSalasDomA);

                String codigo = listaSalasDomA.contains(aluno.getSerie()) ? "" + codigoDomA++ : "" + codigoDomB++;
                Serie serie = this.serieService.verificarOuSalvar(aluno.getSerie());
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

    private void adicionarSalas(List<String> listaSalasDomA) {
        listaSalasDomA.add("1ºA");
        listaSalasDomA.add("1ºB");
        listaSalasDomA.add("1ºC");
        listaSalasDomA.add("1ºD");
        listaSalasDomA.add("1ºE");
        listaSalasDomA.add("1ºF");
        listaSalasDomA.add("1ºG");
        listaSalasDomA.add("1ºH");
        listaSalasDomA.add("2ºA");
        listaSalasDomA.add("2ºB");
        listaSalasDomA.add("2ºC");
        listaSalasDomA.add("2ºD");
        listaSalasDomA.add("2ºE");
        listaSalasDomA.add("2ºF");
        listaSalasDomA.add("3ºA");
        listaSalasDomA.add("3ºB");
        listaSalasDomA.add("3ºC");
    }

}
