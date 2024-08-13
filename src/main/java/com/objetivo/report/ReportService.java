package com.objetivo.report;

import com.objetivo.dto.PessoaRelatorioDTO;
import com.objetivo.entities.Pessoa;
import com.objetivo.service.PessoaService;
import com.objetivo.utils.Formatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReportService {

    public static final String JASPER_DIRETORIO =  "classpath:reports/jasper/pessoa/";
    public static final String JASPER_PREFIXO = "pessoa";
    public static final String JASPER_SUFIXO = ".jasper";

    private final PessoaService pessoaService;

    public ReportService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    public byte[] exportarPDF(Long id) {

        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(JASPER_DIRETORIO.concat(JASPER_PREFIXO.concat(JASPER_SUFIXO)));
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), null, new JRBeanCollectionDataSource(dataToExport(id)));
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return bytes;

    }

    public Collection<PessoaRelatorioDTO> dataToExport(Long id) {
        Pessoa pessoa = this.pessoaService.obterPessoaPorId(id);

        Collection<PessoaRelatorioDTO> coll = new ArrayList<>();

        PessoaRelatorioDTO pessoaExport = new PessoaRelatorioDTO();
        pessoaExport.setNome(pessoa.getNome());
        pessoaExport.setCpf(Formatter.cpfCnpjMask(pessoa.getCpf()));
        pessoaExport.setTelefone(Formatter.telephoneMask(pessoa.getTelefone()));
        pessoaExport.setIdade(pessoa.getIdade());
        pessoaExport.setDataNascimento(pessoa.getDataNascimento());
        coll.add(pessoaExport);

        pessoa.getEnderecos().forEach(endereco -> {
            PessoaRelatorioDTO dto = new PessoaRelatorioDTO();

            dto.setCep(Formatter.cepMask(endereco.getCep()));
            dto.setLogradouro(endereco.getLogradouro());
            dto.setNumero(endereco.getNumero());
            dto.setCidade(endereco.getCidade());
            dto.setUf(endereco.getUf());
            dto.setBairro(endereco.getBairro());

            coll.add(dto);
        });

        return coll;
    }

}
