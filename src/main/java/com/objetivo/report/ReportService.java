package com.objetivo.report;

import com.objetivo.entities.Pessoa;
import com.objetivo.service.PessoaService;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.jsf.FacesContextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    //public static final String JASPER_DIRETORIO = "src/main/reports/jasper/pessoa/";
    public static final String JASPER_DIRETORIO =  "classpath:reports/jasper/pessoa";
    public static final String JASPER_PREFIXO = "pessoa";
    public static final String JASPER_SUFIXO = ".jasper";

    private final PessoaService pessoaService;

    public ReportService(PessoaService pessoaService) {
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

}
