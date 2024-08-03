package com.objetivo.report;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/report")
@Slf4j
public class PessoaReportController {

    private final ReportService pessoaReportService;

    private final ReportService reportService;

    public PessoaReportController(ReportService pessoaReportService, ReportService reportService) {
        this.pessoaReportService = pessoaReportService;
        this.reportService = reportService;
    }

    @GetMapping("/gerar/{id}")
    public void gerar(
            @PathVariable Long id,
            HttpServletResponse response) throws IOException {
        byte[] bytes = reportService.exportarPDF(id);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);

      //  log.info("esse é o tamanho do baguio: " + bytes.);

       // response.setHeader("Content-disposition", "inline; filename=pessoa" + id + ".pdf");
       // response.setHeader("Content-disposition", "attachment; filename=pessoa" + id + ".pdf");
        response.getOutputStream().write(bytes);
    }

}
