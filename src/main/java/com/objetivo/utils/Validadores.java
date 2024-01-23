package com.objetivo.utils;

import java.time.LocalDate;

import com.objetivo.entities.Pessoa;

public class Validadores {
	
	public static boolean validador(Pessoa pessoa) {
		if (ValidaCpf.isCPF(pessoa.cnpj_cpf) == true &&
				(pessoa.datanascimento.compareTo(LocalDate.now())) <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void mensagens(Pessoa pessoa) {	
		if (ValidaCpf.isCPF(pessoa.cnpj_cpf) == false ) {
			System.out.println(pessoa.cnpj_cpf + " Cpf inválido, verifique! ");
		} else {
			System.out.println(pessoa.datanascimento + " Não pode ser uma data futura!");
		}	
	}
}
