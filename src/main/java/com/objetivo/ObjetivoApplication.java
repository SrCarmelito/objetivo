package com.objetivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ObjetivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjetivoApplication.class, args);
	}

	//TODO: criar camada service
	//TODO: refazer rotina da api viacep
	//tODO: implementar testes
	//TODO: REVISAR REQUISITOS DO OBJETIVO

}
