package com.objetivo.utils.pesquisaporcep;

import java.io.BufferedReader;
import java.io.IOException;

public class Utils {
	 public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
	        String resposta, jsonEmString = "";
	        while ((resposta = buffereReader.readLine()) != null) {
	            jsonEmString += resposta;
	        }
	        return jsonEmString;
	    }
}
