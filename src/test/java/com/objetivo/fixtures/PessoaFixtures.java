package com.objetivo.fixtures;


import com.objetivo.entities.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PessoaFixtures {

    public static Pessoa pessoaLinusTorvalds() {
        return Pessoa.builder()
                .id(365L)
                .nome("Linus Torvalds")
                .dataNascimento(LocalDate.of(1969, 12, 28))
                .cpf("06455083903")
                .telefone("6189745612")
                .idade(BigDecimal.ZERO).build();
    }

    public static Pessoa pessoaJamesGosling() {
        return Pessoa.builder()
                .id(42L)
                .nome("James Gosling")
                .dataNascimento(LocalDate.of(1965, 5, 19))
                .cpf("62510728490")
                .telefone("44988080437")
                .idade(BigDecimal.ZERO).build();
    }

}


















