package com.objetivo.report;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/report")
@Slf4j
public class PessoaReportController {

    private final PessoaReportService pessoaReportService;

    private final RelatorioCarmelitoService relatorioCarmelitoService;

    public PessoaReportController(PessoaReportService pessoaReportService, RelatorioCarmelitoService relatorioCarmelitoService) {
        this.pessoaReportService = pessoaReportService;
        this.relatorioCarmelitoService = relatorioCarmelitoService;
    }

    @PostMapping("/gerar")
    public void gerar(@RequestBody PessoaReportEntity pessoa) throws IOException {
        this.pessoaReportService.gerar(pessoa);
    }

    public String abrir() {
        return "reports";
    }

    @GetMapping("/gerar/{id}")
    public void gerarCarmelito(
            @PathVariable Long id,
            HttpServletResponse response) throws IOException {
        byte[] bytes = relatorioCarmelitoService.exportarPDF(id);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);

      //  log.info("esse é o tamanho do baguio: " + bytes.);

       // response.setHeader("Content-disposition", "inline; filename=pessoa" + id + ".pdf");
       // response.setHeader("Content-disposition", "attachment; filename=pessoa" + id + ".pdf");
        response.getOutputStream().write(bytes);
    }

}
