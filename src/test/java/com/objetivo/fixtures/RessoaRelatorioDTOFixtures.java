package com.objetivo.fixtures;

import com.objetivo.report.PessoaRelatorioDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RessoaRelatorioDTOFixtures {

    public static PessoaRelatorioDTO melvinJones() {
        PessoaRelatorioDTO melvinJones = new PessoaRelatorioDTO();
        melvinJones.setNome("Melvin Jones");
        melvinJones.setCpf("12345678910");
        melvinJones.setTelefone("4465851155");
        melvinJones.setIdade(BigDecimal.valueOf(42L));
        melvinJones.setDataNascimento(LocalDate.of(1975, 8, 26));
        melvinJones.setCep("98652221");
        melvinJones.setLogradouro("Rua das Flores");
        melvinJones.setNumero("465");
        melvinJones.setCidade("Balsa Nova");
        melvinJones.setUf("SP");
        melvinJones.setBairro("Praga");
        return melvinJones;
    }

}