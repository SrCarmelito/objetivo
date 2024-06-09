package com.objetivo.utils.pesquisaporcep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiCep {

	private static String web = "https://viacep.com.br/ws/";

	public static EnderecoJson buscaCepRest(String cep) {
		String params = web + cep + "/json";
		RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(params, EnderecoJson.class).getBody();
	}
}
