package com.objetivo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.MaskFormatter;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.objetivo.utils.FormataTelefone;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(schema = "elo", name = "pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id;
	
	@NotBlank(message = "Não é permitido nome vazio")
	public String nome;
	
	@NotNull(message = "Não é permitido Data de Nascimento Vazia")
	public LocalDate datanascimento;
	
	@NotBlank
	public String cnpj_cpf;
	
	@NotBlank
	public String telefone;
	
	@NotBlank
	public String idade;
	
	@OneToMany
	@JoinColumn(name = "pessoa_id", referencedColumnName = "id")
	public List<Endereco> enderecos = new ArrayList<Endereco>();
		
	public Pessoa() {
		
	}
		
	public Pessoa(Integer id, String nome, LocalDate datanascimento, String cnpj_cpf, String telefone, String idade) {
		super();
		this.id = id;
		this.nome = nome;
		this.datanascimento = datanascimento;
		this.cnpj_cpf = cnpj_cpf;
		this.telefone = telefone;
		this.idade = idade;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public LocalDate getDatanascimento() {
		return datanascimento;
	}
	public void setDatanascimento(LocalDate datanascimento) {
		this.datanascimento = datanascimento;
	}
	public String getCnpj_cpf() {
		return cnpj_cpf;
	}
	public void setCnpj_cpf(String cnpj_cpf) {
		this.cnpj_cpf = cnpj_cpf;
	}

	public String getTelefone() {
		return FormataTelefone.format(telefone);
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getIdade() {
		return idade;
	}
	public void setIdade(String idade) {
		this.idade = idade;
	}
		
}
