package com.objetivo.utils.pesquisaporcep;

import org.springframework.web.client.RestTemplate;

public class ApiCep {

	private static String web = "https://viacep.com.br/ws/";

	public static EnderecoJson buscaCepRest(String cep) {
		String params = web + cep + "/json";
		RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(params, EnderecoJson.class).getBody();
	}
}
