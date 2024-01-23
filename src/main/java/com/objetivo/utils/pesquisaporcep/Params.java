package com.objetivo.utils.pesquisaporcep;

import java.util.Scanner;

public class Params {

	public static void main(String[] args) throws Exception {
		System.out.println("Selecione o CEP a ser buscado: ");
		Scanner entrada = new Scanner(System.in);
		String cep = entrada.nextLine();

		Endereco endereco = ApiCep.buscaCep(cep);

		System.out.println("CEP: " + endereco.getCep());
	    System.out.println("logradouro: " + endereco.getLogradouro());
	    System.out.println("Bairro: " + endereco.getBairro());
	    System.out.println("Localidade: " + endereco.getLocalidade());
	    System.out.println("UF: " + endereco.getUf());
	    
	    entrada.close();
		
	}
}
