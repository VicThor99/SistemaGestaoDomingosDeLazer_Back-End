package br.com.domingosdelazer.SistemaGestao.rest.controller;

import br.com.domingosdelazer.SistemaGestao.service.impl.JasperServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jaspers")
@CrossOrigin(origins = {"*"})
@Tag(name = "Jasper", description = "API que faz a gestão dos jaspers")
public class JasperController {

    @Autowired
    private JasperServiceImpl service;

    @GetMapping("/crachas/{escolaId}")
    @ApiOperation("Gerar crachás")
    @Tag(name = "Jasper")
    public ResponseEntity gerarCrachas(@PathVariable Integer escolaId,
                                       @RequestParam(required = false, name = "domingo") String domingo,
                                       @RequestParam(required = false, name = "codigo") String codigo,
                                       @RequestParam(required = false, name = "serie") String serie,
                                       @RequestParam(required = false, name = "sala") String sala,
                                       @RequestParam(required = false, name = "ativos", defaultValue = "false") Boolean ativos) {
        try {
            byte[] reportContent = service.preencherJasperCrachas(domingo, codigo, serie, sala, ativos, escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("content-disposition", "inline; filename=Crachas.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/matriculas/{escolaId}")
    @ApiOperation("Gerar Matriculas")
    @Tag(name = "Jasper")
    public ResponseEntity gerarMatriculas(@PathVariable Integer escolaId,
                                          @RequestParam(required = false, name = "domingo") String domingo,
                                          @RequestParam(required = false, name = "codigo") String codigo,
                                          @RequestParam(required = false, name = "serie") String serie,
                                          @RequestParam(required = false, name = "sala") String sala) {
        try {
            byte[] reportContent = service.preencherJasperMatriculas(domingo, codigo, serie, sala,escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("content-disposition", "inline; filename=Matriculas.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/protocolos/{escolaId}")
    @ApiOperation("Gerar Protocolos de Sacolinha")
    @Tag(name = "Jasper")
    public ResponseEntity gerarProtocolos(@PathVariable Integer escolaId,
                                          @RequestParam(required = false, name = "domingo") String domingo,
                                          @RequestParam(required = false, name = "codigo") String codigo,
                                          @RequestParam(required = false, name = "serie") String serie,
                                          @RequestParam(required = false, name = "sala") String sala,
                                          @RequestParam(required = false, name = "ativos", defaultValue = "false") Boolean ativos) {
        try {
            byte[] reportContent = service.preencherJasperProtocolos(domingo, codigo, serie, sala, ativos,escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("content-disposition", "inline; filename=Protocolos.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/listas/{escolaId}")
    @ApiOperation("Gerar Listas das Sacolinhas")
    @Tag(name = "Jasper")
    public ResponseEntity gerarListaDeSalas(@PathVariable Integer escolaId,
                                            @RequestParam(required = false, name = "domingo") String domingo,
                                            @RequestParam(required = false, name = "serie") String serie,
                                            @RequestParam(required = false, name = "sala") String sala,
                                            @RequestParam(required = false, name = "ativos", defaultValue = "false") Boolean ativos) {
        try {
            byte[] reportContent = service.preencherJasperLista(domingo, serie, sala, ativos, escolaId);

            ByteArrayResource resource = new ByteArrayResource(reportContent);

            return ResponseEntity.ok()
                    .header("content-disposition", "inline; filename=ListaSalas.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
