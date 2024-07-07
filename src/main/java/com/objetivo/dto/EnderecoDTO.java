package com.objetivo.dto;

import com.objetivo.entities.Pessoa;
import lombok.Data;

@Data
public class EnderecoDTO {

    private Long id;
    private String cep;
    private String logradouro;
    private String numero;
    private String cidade;
    private String uf;
    private String bairro;
    private Pessoa pessoa;

}
