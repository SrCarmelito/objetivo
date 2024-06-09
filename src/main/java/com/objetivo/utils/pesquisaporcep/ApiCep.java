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
//	public int successo = 200;

//	public static EnderecoJson buscaCep(String cep) throws Exception {
//		String params = web + cep + "/json";
//
//		try {
//
//			URL url = new URL(params);
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//			if(connection.getResponseCode() != successo) {
//				System.out.println("to aqui");
//				throw new RuntimeException("HTTP error code : " + connection.getResponseCode());
//			}
//
//			BufferedReader response = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//
//			String jsonString = Utils.converteJsonEmString(response);
//			Gson gson = new Gson();
//
//			EnderecoJson endereco = gson.fromJson(jsonString, EnderecoJson.class);
//
//			return endereco;
//		} catch (Exception e) {
//			throw new Exception("Ops, ocorreu o seguinte erro inesperado: " + e);
//		}
//	}

	public static ResponseEntity<EnderecoJson> buscaCepRest(String cep) {
		String params = web + cep + "/json";
		RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(params, EnderecoJson.class);
	}
}
