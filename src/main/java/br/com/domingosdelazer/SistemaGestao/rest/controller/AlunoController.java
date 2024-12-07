package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.*;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.AlunoRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.ImportRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.AlunoResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.AlunoSacolinhaResponseDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.AlunoServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.ArquivosServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.EscolaServiceImpl;
import br.com.domingosdelazer.SistemaGestao.service.impl.SerieServiceImpl;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.SalvarAlunoRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.ClasseResponseDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.response.ImportResponseDTO;
import br.com.domingosdelazer.SistemaGestao.repository.RegistroPresencasRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    @Autowired
    private EscolaServiceImpl escolaService;

    @Autowired
    private ArquivosServiceImpl arquivosService;


    @GetMapping("/{escolaId}")
    @ApiOperation("Listar Alunos")
    @Tag(name = "Alunos")
    public ResponseEntity getTodosAlunos(@PathVariable Integer escolaId) {
        List<AlunoResponseDTO> alunos = service.listAllAlunos(false, escolaId).stream().map(a -> {
            return AlunoResponseDTO.builder()
                    .id(a.getId())
                    .codigo(a.getCodigo())
                    .nome(a.getNome())
                    .sexo(a.getSexo())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .nascimento(a.getNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .sapato(a.getSapato())
                    .blusa(a.getCamisa())
                    .calca(a.getCalca())
                    .endereco(a.getEndereco())
                    .nomeResponsavel(a.getNomeResponsavel())
                    .telefoneResponsavel(a.getTelefoneResponsavel())
                    .emailResponsavel(a.getEmailResponsavel())
                    .numeroSacolinha(a.getNumeroSacolinha())
                    .ativo(a.getAtivo())
                    .sairSo(a.getSairSozinho())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/export/{escolaId}")
    @ApiOperation("Listar Alunos Aptos a Receber Sacolinha")
    @Tag(name = "Alunos")
    public ResponseEntity getAlunosParaExport(@PathVariable Integer escolaId) {
        List<AlunoSacolinhaResponseDTO> alunos = service.listAllAlunos(true, escolaId).stream().map(a -> {
            return AlunoSacolinhaResponseDTO.builder()
                    .codigo(a.getCodigo())
                    .nome(a.getNome())
                    .sexo(a.getSexo())
                    .idade(calcularIdade(a.getNascimento()))
                    .nascimento(a.getNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .sapato(a.getSapato())
                    .calca(a.getCalca())
                    .camisa(a.getCamisa())
                    .serie(a.getSerie().getSerie())
                    .sala(a.getSerie().getSala())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }

    @PostMapping("/import/{escolaId}")
    @ApiOperation("Importar lista de Alunos passada pela escola")
    @Tag(name = "Alunos")
    public ResponseEntity importarDadosLista(@RequestBody ImportRequestDTO request, @PathVariable Integer escolaId) {
        try {
            Escola escola = this.escolaService.getEscolaById(escolaId);
            Map<String, Integer> alunos = new TreeMap<>();
            int contador = 0;
            for (AlunoRequestDTO aluno : request.getAlunos()) {
                Serie serie = this.serieService
                        .verificarOuSalvar(aluno.getSerie(), aluno.getSala(), aluno.getDomingo(), escola.getId());

                String codigo = carregarNovoCodigo(serie.getSerie(), escolaId);
                RegistroPresencas registro = this.registroService.save(RegistroPresencas.builder().id(0).build());
                ArquivosAluno arquivos = this.arquivosService.save(ArquivosAluno.builder().id(0).build());

                Aluno a = this.service.save(Aluno.builder()
                        .nome(aluno.getNome())
                        .sexo(aluno.getSexo())
                        .codigo(codigo)
                        .nascimento(aluno.getNascimento().plusDays(1))
                        .serie(serie)
                        .registroPresencas(registro)
                        .escola(escola)
                        .arquivos(arquivos)
                        .build());

                String key = a.getSerie().getSerie().split("º")[0];

                if (alunos.containsKey(key)) {
                    alunos.replace(key, (alunos.get(key) + 1));
                } else {
                    alunos.put(key, 1);
                }

                contador++;
            }

            ImportResponseDTO response = new ImportResponseDTO();
            response.setSeries(new ArrayList<>());

            for (String key : alunos.keySet()) {
                if (key.equalsIgnoreCase("99")) {
                    response.getSeries().add(ClasseResponseDTO.builder()
                            .serie("Sala " + key)
                            .quantidade(alunos.get(key))
                            .build());
                } else {
                    response.getSeries().add(ClasseResponseDTO.builder()
                            .serie(key + "º ano")
                            .quantidade(alunos.get(key))
                            .build());
                }
            }

            response.setTotal(contador);

            return ResponseEntity.ok(response);

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
                        .numeroSacolinha(request.getNumeroSacolinha())
                        .nome(request.getNome())
                        .sexo(request.getSexo())
                        .nascimento(request.getNascimento().plusDays(1))
                        .serie(carregarSeriePorString(request.getSerie(), request.getEscolaId()))
                        .sapato(request.getSapato())
                        .camisa(request.getBlusa())
                        .calca(request.getCalca())
                        .endereco(request.getEndereco())
                        .nomeResponsavel(request.getNomeResponsavel())
                        .emailResponsavel(request.getEmailResponsavel())
                        .telefoneResponsavel(request.getTelefoneResponsavel())
                        .registroPresencas(carregarRegistroPresenca(request.getId()))
                        .ativo(request.getAtivo())
                        .sairSozinho(request.getSairSo())
                        .escola(this.escolaService.getEscolaById(request.getEscolaId()))
                        .arquivos(ArquivosAluno.builder().id(this.arquivosService.getArquivosByIdAluno(request.getId()).getId()).build())
                        .build());
            } else {
                RegistroPresencas registro =
                        this.registroService.save(RegistroPresencas.builder().id(0).build());
                ArquivosAluno arquivos =
                        this.arquivosService.save(ArquivosAluno.builder().id(0).build());

                aluno = this.service.save(Aluno.builder()
                        .id(0)
                        .codigo(carregarNovoCodigo(request.getSerie(), request.getEscolaId()))
                        .numeroSacolinha(request.getNumeroSacolinha())
                        .nome(request.getNome())
                        .sexo(request.getSexo())
                        .nascimento(request.getNascimento().plusDays(1))
                        .serie(carregarSeriePorString(request.getSerie(), request.getEscolaId()))
                        .sapato(request.getSapato())
                        .camisa(request.getBlusa())
                        .calca(request.getCalca())
                        .endereco(request.getEndereco())
                        .nomeResponsavel(request.getNomeResponsavel())
                        .emailResponsavel(request.getEmailResponsavel())
                        .telefoneResponsavel(request.getTelefoneResponsavel())
                        .registroPresencas(registro)
                        .ativo(request.getAtivo())
                        .sairSozinho(request.getSairSo())
                        .escola(this.escolaService.getEscolaById(request.getEscolaId()))
                        .arquivos(arquivos)
                        .build());
            }

            return ResponseEntity.ok(AlunoResponseDTO.builder()
                    .id(aluno.getId())
                    .codigo(aluno.getCodigo())
                    .numeroSacolinha(aluno.getNumeroSacolinha())
                    .nome(aluno.getNome())
                    .sexo(aluno.getSexo())
                    .nascimento(aluno.getNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .serie(aluno.getSerie().getSerie())
                    .sapato(aluno.getSapato())
                    .blusa(aluno.getCamisa())
                    .calca(aluno.getCalca())
                    .endereco(aluno.getEndereco())
                    .nomeResponsavel(aluno.getNomeResponsavel())
                    .emailResponsavel(aluno.getEmailResponsavel())
                    .telefoneResponsavel(aluno.getTelefoneResponsavel())
                    .ativo(aluno.getAtivo())
                    .sairSo(aluno.getSairSozinho())
                    .build());

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

        if (request.getNascimento() == null || !request.getNascimento().isBefore(LocalDate.now())) {
            throw new Exception("Data de nascimento do Aluno não pode ser nulo nem após o dia de hoje");
        }
    }

    private RegistroPresencas carregarRegistroPresenca(Integer id) {
        return this.registroService.carregarRegistroPorIdAluno(id);
    }

    private String carregarNovoCodigo(String serie, Integer escolaId) {
        return this.service.carregarCodigoPorSerie(serie, escolaId);
    }

    private Serie carregarSeriePorString(String serie, Integer escolaId) {
        return this.serieService.verificarSerie(serie, escolaId);
    }

    private Integer calcularIdade(LocalDate nascimento) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dataAtual = new Date();
        return Integer.parseInt(sdf.format(dataAtual)) - Integer.parseInt(sdf.format(nascimento));
    }

}
