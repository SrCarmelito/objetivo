package com.objetivo.utils.pesquisaporcep;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.client.RestTemplate;

public class ApiCep {

	public static EnderecoJson buscaCepRest(String cep) {
		String params = "https://viacep.com.br/ws/" + cep + "/json";
		RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(params, EnderecoJson.class).getBody();
	}
}
