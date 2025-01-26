package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.entity.dto.request.GerarCrachasRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.GerarListaSalasRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.GerarMatriculasRequestDTO;
import br.com.domingosdelazer.SistemaGestao.entity.dto.request.GerarProtocolosRequestDTO;
import br.com.domingosdelazer.SistemaGestao.service.impl.JasperServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jaspers")
@Tag(name = "Jasper", description = "API que faz a gestão dos jaspers")
public class JasperController {

    @Autowired
    private JasperServiceImpl service;

    @PostMapping(value = "/crachas/{escolaId}", produces = {"application/pdf"})
    @ApiOperation("Gerar crachás")
    @Tag(name = "Jasper")
    public ResponseEntity gerarCrachas(@PathVariable Integer escolaId,
                                       @RequestBody GerarCrachasRequestDTO request) {
        try {
            System.out.println("Chegou");
            byte[] reportContent = service.preencherJasperCrachas(request, escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=Crachas.pdf")
                    .header("Content-Type", "application/pdf")
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping(value = "/matriculas/{escolaId}", produces = {"application/pdf"})
    @ApiOperation("Gerar Matriculas")
    @Tag(name = "Jasper")
    public ResponseEntity gerarMatriculas(@PathVariable Integer escolaId,
                                          @RequestBody GerarMatriculasRequestDTO request) {
        try {
            byte[] reportContent = service.preencherJasperMatriculas(request, escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=Matriculas.pdf")
                    .header("Content-Type", "application/pdf")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping(value = "/protocolos/{escolaId}", produces = {"application/pdf"})
    @ApiOperation("Gerar Protocolos de Sacolinha")
    @Tag(name = "Jasper")
    public ResponseEntity gerarProtocolos(@PathVariable Integer escolaId,
                                          @RequestBody GerarProtocolosRequestDTO request) {
        try {
            byte[] reportContent = service.preencherJasperProtocolos(request, escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=Protocolos.pdf")
                    .header("Content-Type", "application/pdf")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping(value = "/listas/{escolaId}", produces = {"application/pdf"})
    @ApiOperation("Gerar Listas das Sacolinhas")
    @Tag(name = "Jasper")
    public ResponseEntity gerarListaDeSalas(@PathVariable Integer escolaId,
                                            @RequestBody GerarListaSalasRequestDTO request) {
        try {
            byte[] reportContent = service.preencherJasperLista(request, escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=ListaSalas.pdf")
                    .header("Content-Type", "application/pdf")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
