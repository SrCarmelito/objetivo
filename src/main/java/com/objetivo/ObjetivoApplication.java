package com.objetivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObjetivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjetivoApplication.class, args);
	}

	//TODO: ALTERAR CAMINHO DOS REPORT, incluir em pacote junto com as classes POIS ESTÁ DANDO ERRO NO RENDER
	//TODO: ESTUDAR API DO GOVERNO PARA VALIDAR PESSOA EXISTENTE NA RFB
	//TODO: ALTERAR PARA VARIAVEIS DE AMBIENTE AS CONFIGS DE E-MAIL

}
