package com.objetivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;

    @NotBlank(message = "É Necessário informar o Nome!")
    private String nome;

    @NotNull(message = "Não é permitido Data de Nascimento Vazia!")
    @Past(message = "Não é permitido Data de Nascimento no futuro!")
    private LocalDate dataNascimento;

    @NotBlank(message = "É necessário informar o CPF")
    @CPF(message = "CPF Inválido! Verifique!")
    private String cpf;

    @NotBlank(message = "É Necessário Informar o Telefone!")
    @Size(min = 10, max = 11, message = "Deve ser entre 10 e 11 caracteres com DDD")
    private String telefone;

    private List<EnderecoDTO> enderecos = new ArrayList<>();

}
