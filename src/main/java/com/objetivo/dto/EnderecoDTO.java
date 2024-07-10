package com.objetivo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    private Long id;

    @NotBlank(message = "É Necessário informar o CEP!")
    private String cep;

    @NotBlank(message = "É Necessário informar o Logradouro!")
    @Size(min = 1, max = 150, message = "Deve ser entre 1 e 150 caracteres")
    private String logradouro;

    @NotBlank(message = "É Necessário informar o Número!")
    @Size(min = 1, max = 10, message = "Deve ser entre 1 e 10 caracteres")
    private String numero;

    @NotBlank(message = "É Necessário informar a Cidade!")
    @Size(min = 1, max = 100, message = "Deve ser entre 1 e 100 caracteres")
    private String cidade;

    @NotBlank(message = "É Necessário informar o UF!")
    @Size(min = 1, max = 2, message = "Deve ser entre 1 e 2 caracteres")
    private String uf;

    @NotBlank(message = "É Necessário informar o Bairro!")
    @Size(min = 1, max = 100, message = "Deve ser entre 1 e 100 caracteres")
    private String bairro;

    private PessoaDTO pessoaDTO;

}
