package com.objetivo.fixtures;

import com.objetivo.dto.PessoaDTO;

import java.time.LocalDate;
import java.util.List;

public class PessoaDTOFixtures {

    public static PessoaDTO pessoaJimiHendrix() {
        return PessoaDTO.builder()
                .id(71L)
                .nome("Jimi Hendrix")
                .dataNascimento(LocalDate.of(1942, 11, 27))
                .cpf("123456789")
                .telefone("4432627741")
                .enderecos(List.of(EnderecoDTOFixtures.enderecoDTOManhattan()))
                .build();
    }

}
