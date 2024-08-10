package com.objetivo.report;

import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import com.objetivo.service.PessoaService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.eclipse.jdt.internal.compiler.batch.ClasspathDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public static final String JASPER_DIRETORIO =  "classpath:reports/jasper/pessoa/";
    public static final String JASPER_PREFIXO = "pessoa";
    public static final String JASPER_SUFIXO = ".jasper";

    private final PessoaService pessoaService;

    public ReportService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    private Map<String, Object> params = new HashMap<>();

    public byte[] exportarPDF(Long id) {
        Pessoa pessoa = this.pessoaService.obterPessoaPorId(id);

        Collection<Pessoa> coll = new ArrayList<>();
        coll.add(pessoa);

        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(JASPER_PREFIXO.concat(JASPER_SUFIXO)));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params, new JRBeanCollectionDataSource(coll));
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

}
