package com.objetivo.fixtures;

import com.objetivo.dto.EnderecoDTO;

public class EnderecoDTOFixtures {

    public static EnderecoDTO enderecoDTOManhattan() {
        new EnderecoDTO();
        return EnderecoDTO.builder()
                .id(21L)
                .cep("47850059")
                .logradouro("Rua das Azaléias")
                .numero("2530")
                .cidade("Manhattan")
                .uf("RJ")
                .bairro("Centro")
                .build();
    }
}
