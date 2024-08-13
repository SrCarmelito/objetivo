package com.objetivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ObjetivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjetivoApplication.class, args);
	}

	//TODO: ALTERAR CAMINHO DOS REPORT, POIS ESTÁ DANDO ERRO NO RENDER

	//TODO: VER FORMA PARA RETIRAR OS DELETES DOS SQL PARA PASSAR OS TESTES NO DEPLOY DE FORMA NÃO FORÇACA
	// UTILIZAR ESTE EXEMPLO REPASSADO PELO MONSTRO
//	@DatabaseTearDown
//	@DatabaseSetup
//<?xml version="1.0" encoding="UTF-8"?>
//<dataset>
//
//	<LEIS_ATOS ID="1" NUMERO="100" ID_ESCOPO="9"
//	ID_TIPO_DOCUMENTO="1" ANO="2020" DATA_LEI="2020-01-01"/>
//
//	<LEIS_ATOS ID="2" NUMERO="200" ID_ESCOPO="10"
//	ID_TIPO_DOCUMENTO="2" ANO="2020" DATA_LEI="2020-01-01"/>
//
//</dataset>

	//TODO: IMPLEMENTAR RSQL

}
