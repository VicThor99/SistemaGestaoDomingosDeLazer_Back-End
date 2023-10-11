package br.com.paraisopolis.SistemaGestao.rest.controller;

import br.com.paraisopolis.SistemaGestao.entity.Aluno;
import br.com.paraisopolis.SistemaGestao.entity.RegistroPresencas;
import br.com.paraisopolis.SistemaGestao.entity.Serie;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.AlunoRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.ImportRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.request.SalvarAlunoRequestDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoResponseDTO;
import br.com.paraisopolis.SistemaGestao.entity.dto.response.AlunoSacolinhaResponseDTO;
import br.com.paraisopolis.SistemaGestao.repository.RegistroPresencasRepository;
import br.com.paraisopolis.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.paraisopolis.SistemaGestao.service.impl.SerieServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
                    .id(a.getId())
                    .codigo(a.getCodigo())
                    .nome(a.getNome())
                    .sexo(a.getSexo())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .nascimento(sdf.format(a.getNascimento()))
                    .sapato(a.getSapato())
                    .blusa(a.getCamisa())
                    .calca(a.getCalca())
                    .endereco(a.getEndereco())
                    .nomeResponsavel(a.getNomeResponsavel())
                    .telefoneResponsavel(a.getTelefoneResponsavel())
                    .emailResponsavel(a.getEmailResponsavel())
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
            for (AlunoRequestDTO aluno : request.getAlunos()) {
                Serie serie = this.serieService
                        .verificarOuSalvar(aluno.getSerie(), aluno.getSala(), aluno.getDomingo());

                String codigo = carregarNovoCodigo(serie.getSerie());
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

    @PostMapping("/save")
    @ApiOperation("Salva novo Aluno ou altera Aluno")
    @Tag(name = "Alunos")
    public ResponseEntity salvarAluno(@RequestBody SalvarAlunoRequestDTO request) {
        try {
            verificarAluno(request);
            Aluno aluno;
            if (request.getId() > 0) {
                aluno = this.service.save(Aluno.builder()
                        .id(request.getId())
                        .codigo(request.getCodigo())
                        .nome(request.getNome())
                        .sexo(request.getSexo())
                        .nascimento(request.getNascimento())
                        .serie(carregarSeriePorString(request.getSerie()))
                        .sapato(request.getSapato())
                        .camisa(request.getBlusa())
                        .calca(request.getCalca())
                        .endereco(request.getEndereco())
                        .nomeResponsavel(request.getNomeResponsavel())
                        .emailResponsavel(request.getEmailResponsavel())
                        .telefoneResponsavel(request.getTelefoneResponsavel())
                        .registroPresencas(carregarRegistroPresenca(request.getId()))
                        .build());
            } else {
                RegistroPresencas registro =
                        this.registroService.save(RegistroPresencas.builder().id(0).build());

                aluno = this.service.save(Aluno.builder()
                        .id(0)
                        .codigo(carregarNovoCodigo(request.getSerie()))
                        .nome(request.getNome())
                        .sexo(request.getSexo())
                        .nascimento(request.getNascimento())
                        .serie(carregarSeriePorString(request.getSerie()))
                        .sapato(request.getSapato())
                        .camisa(request.getBlusa())
                        .calca(request.getCalca())
                        .endereco(request.getEndereco())
                        .nomeResponsavel(request.getNomeResponsavel())
                        .emailResponsavel(request.getEmailResponsavel())
                        .telefoneResponsavel(request.getTelefoneResponsavel())
                        .registroPresencas(registro)
                        .build());
            }

            return ResponseEntity.ok(aluno);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private void verificarAluno(SalvarAlunoRequestDTO request) throws Exception {
        if (StringUtils.isEmpty(request.getNome()) || request.getNome().length() < 3) {
            throw new Exception("Nome do Aluno não pode ser menor do que 3 letras");
        }

        if (StringUtils.isEmpty(request.getSexo())) {
            throw new Exception("Sexo do Aluno não pode ser nulo");
        }

        if (request.getNascimento() == null || !request.getNascimento().before(new Date())) {
            throw new Exception("Data de nascimento do Aluno não pode ser nulo nem após o dia de hoje");
        }
    }

    private RegistroPresencas carregarRegistroPresenca(Integer id) {
        return this.registroService.carregarRegistroPorIdAluno(id);
    }

    private String carregarNovoCodigo(String serie) {
        return this.service.carregarCodigoPorSerie(serie);
    }

    private Serie carregarSeriePorString(String serie) {
        return this.serieService.verificarSerie(serie);
    }

    private Integer calcularIdade(Date nascimento) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dataAtual = new Date();
        return Integer.parseInt(sdf.format(dataAtual)) - Integer.parseInt(sdf.format(nascimento));
    }

}
