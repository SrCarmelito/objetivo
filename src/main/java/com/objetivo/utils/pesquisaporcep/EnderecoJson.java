package com.objetivo.utils.pesquisaporcep;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoJson {

	private String cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
				
}