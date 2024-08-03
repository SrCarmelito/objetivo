package com.objetivo.report;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@Service
public class PessoaReportService {
    public static final String PESSOAS = "classpath:jasper/pessoa/";
    public static final String IMAGEBG = "classpath:jasper/img/pessoa.jpg";
    public static final String ARQUIVOJRXML = "pessoa.jrxml";
    public static final String DESTINOPDF = "C:\\Users\\carmelito.benali\\Downloads\\";
    public static final Logger LOGGER = LoggerFactory.getLogger(PessoaReportService.class);
    public void gerar(PessoaReportEntity pessoa) throws IOException {

        byte[] imagebg = this.loadimage(IMAGEBG);

        Map<String, Object> params = new HashMap<>();
        params.put("nome", pessoa.getNome());
        params.put("cpf", pessoa.getCpf());
        params.put("dataNascimento", pessoa.getDataNascimento());
        params.put("telefone", pessoa.getTelefone());
        params.put("imageJasper", imagebg);

        String pathAbsoluto = getAbsolutePath();

        try {
            String folderDiretorio = getDiretorioSave("pessoas-salvas");
            JasperReport report = JasperCompileManager.compileReport(pathAbsoluto);
            LOGGER.info("report compilado...{} ", pathAbsoluto);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            LOGGER.info("jasper print");
            JasperExportManager.exportReportToPdfFile(print, folderDiretorio);
            LOGGER.info("PDF EXPORTADO PARA: {} ", folderDiretorio);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }

    private byte[] loadimage(String imagebg) throws IOException {
        String image = ResourceUtils.getFile(imagebg).getAbsolutePath();
        File file = new File(image);
        try (InputStream inputStream = new FileInputStream(file)) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    private String getDiretorioSave(String name) {
        this.createDiretorio(name);
        return DESTINOPDF+name.concat(".pdf");
    }

    private void createDiretorio(String name) {
        File dir = new File(DESTINOPDF);
        if ((!dir.exists())) {
            dir.mkdir();
        }
    }

    private String getAbsolutePath() throws FileNotFoundException {
        return ResourceUtils.getFile(PESSOAS+ARQUIVOJRXML).getAbsolutePath();
    }
}
