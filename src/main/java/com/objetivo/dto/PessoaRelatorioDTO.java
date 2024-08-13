package com.objetivo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PessoaRelatorioDTO {

    private String nome;
    private String cpf;
    private String telefone;
    private BigDecimal idade;
    private LocalDate dataNascimento;

    private String cep;
    private String logradouro;
    private String numero;
    private String cidade;
    private String uf;
    private String bairro;

}