package com.objetivo.dto;

import com.objetivo.entities.Endereco;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PessoaDTO {

    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;
    private String telefone;
    private List<Endereco> enderecos = new ArrayList<>();

}
