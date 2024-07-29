package com.objetivo.report;

import com.objetivo.entities.Pessoa;
import com.objetivo.service.PessoaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class RelatorioCarmelitoService {


    public static final String JASPER_DIRETORIO = "classpath:jasper/pessoa/";
    public static final String JASPER_PREFIXO = "pessoa";
    public static final String JASPER_SUFIXO = ".jasper";
//    public static final String ARQUIVOJRXML = "pessoa.jrxml";
//
//    public static final String DESTINOPDF = "C:\\Users\\carmelito.benali\\Downloads\\";
//
    private final PessoaService pessoaService;

    public RelatorioCarmelitoService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    private Map<String, Object> params = new HashMap<>();

    public byte[] exportarPDF(Long id) {
        Pessoa pessoa = this.pessoaService.obterPessoaPorId(id);

        params.put("nome", pessoa.getNome());
        params.put("cpf", pessoa.getCpf());
        params.put("dataNascimento", pessoa.getDataNascimento());
        params.put("telefone", pessoa.getTelefone());

        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(JASPER_PREFIXO.concat(JASPER_SUFIXO)));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, new JREmptyDataSource());
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

/* CAGADA DO CARMELITO */
//    public void gerarRelatorioCarmelito(Long id, HttpServletRequest request, HttpServletResponse response ) throws IOException {
//        Pessoa pessoa = this.pessoaService.obterPessoaPorId(id);
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("nome", pessoa.getNome());
//        params.put("cpf", pessoa.getCpf());
//        params.put("dataNascimento", pessoa.getDataNascimento());
//        params.put("telefone", pessoa.getTelefone());
//      //  params.put("imageJasper", imagebg);
//
//        String pathAbsoluto = getAbsolutePath();
//
//        try {
//            String folderDiretorio = getDiretorioSave("pessoas-salvas");
//            JasperReport report = JasperCompileManager.compileReport(pathAbsoluto);
//            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
//            JasperExportManager.exportReportToPdfFile(print, folderDiretorio);
//            this.doGet(request, response, print);
//        } catch (JRException e) {
//            throw new RuntimeException(e);
//        } catch (ServletException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    private String getAbsolutePath() throws FileNotFoundException {
//        return ResourceUtils.getFile(PESSOAS+ARQUIVOJRXML).getAbsolutePath();
//    }
//
//    private void createDiretorio(String name) {
//        File dir = new File(DESTINOPDF);
//        if ((!dir.exists())) {
//            dir.mkdir();
//        }
//    }
//
//    private String getDiretorioSave(String name) {
//        this.createDiretorio(name);
//        return DESTINOPDF+name.concat(".pdf");
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response, JasperPrint file) throws ServletException, IOException {
//
//        String id = request.getParameter("id");
//
//        String fileName = "";
//        String fileType = "";
//        // Find this file id in database to get file name, and file type
//
//        // You must tell the browser the file type you are going to send
//        // for example application/pdf, text/plain, text/html, image/jpg
//        response.setContentType(fileType);
//
//        // Make sure to show the download dialog
//        response.setHeader("Content-disposition","attachment; filename=yourcustomfilename.pdf");
//
//        // Assume file name is retrieved from database
//        // For example D:\\file\\test.pdf
//
//        //  File my_file = new File(fileName);
//
//        // This should send the file to browser
//        OutputStream out = response.getOutputStream();
//
//        FileInputStream in = new FileInputStream(getDiretorioSave("pessoas-salvas"));
//        byte[] buffer = new byte[4096];
//        int length;
//        while ((length = in.read(buffer)) > 0){
//            out.write(buffer, 0, length);
//        }
//        in.close();
//        out.flush();
//    }

}
