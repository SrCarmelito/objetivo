package com.objetivo.utils.pesquisaporcep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class ApiCep {

	static String web = "https://viacep.com.br/ws/";
	static int successo = 200;
	
	public static Endereco buscaCep(String cep) throws Exception {
		String params = web + cep + "/json";
		
		try {
			
			URL url = new URL(params);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			if(connection.getResponseCode() != successo) {
				throw new RuntimeException("HTTP error code : " + connection.getResponseCode());
			}
			
			BufferedReader response = new BufferedReader(new InputStreamReader((connection.getInputStream())));

			String jsonString = Utils.converteJsonEmString(response);
			
			Gson gson = new Gson();
			Endereco endereco = gson.fromJson(jsonString, Endereco.class);
			
			return endereco;			
		} catch (Exception e) {
			throw new Exception("Ops, ocorreu o seguinte erro inesperado: " + e);
		}
	}
}
