package com.objetivo.fixtures;


import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PessoaFixtures {

    public static Pessoa pessoaLinusSemEndereco() {
        Pessoa pessoa = new Pessoa();
            pessoa.setId(365L);
            pessoa.setNome("Linus Torvalds");
            pessoa.setDataNascimento(LocalDate.of(1969, 12, 28));
            pessoa.setCpf("06455083903");
            pessoa.setTelefone("6189745612");
            pessoa.setIdade(BigDecimal.ZERO);
        return pessoa;
    }

    public static Pessoa pessoaJamesGosling() {
        return new Pessoa(42L, "James Gosling", LocalDate.of(1965, 5, 19), "62510728490", "44988080437", BigDecimal.ZERO, null);
    }



}


















