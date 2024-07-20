package com.objetivo.fixtures;

import com.objetivo.entities.Endereco;

public class EnderecoFixtures {

    public static Endereco endereco() {
        return Endereco.builder()
                .id(730L)
                .cep("75830112")
                .logradouro("Rua das manções de Las Vegas")
                .numero("6930")
                .cidade("HollyWood")
                .uf("SP")
                .bairro("Centro")
                .pessoa(PessoaFixtures.pessoaJamesGosling())
                .build();
    }

    public static Endereco enderecoSemPessoa() {
        return Endereco.builder()
                .id(421L)
                .cep("87005000")
                .logradouro("Rua Estados Unidos")
                .numero("9874")
                .cidade("Helsinque")
                .uf("AC")
                .bairro("Centro")
                .build();
    }

}
