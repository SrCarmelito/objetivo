package com.objetivo.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaReportEntity {
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
   // private BigDecimal idade;
}
