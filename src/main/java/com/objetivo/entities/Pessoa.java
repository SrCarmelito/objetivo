package com.objetivo.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.br.CPF;

import com.objetivo.utils.FormataTelefone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity(name = "pessoa")
@Table(schema = "elo", name = "pessoa")
public class Pessoa {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
	@SequenceGenerator(name = "seq_pessoa", schema = "elo", sequenceName = "s_pessoa", allocationSize=1)
	public Integer id;
		
	@NotBlank(message = "É Necessário informar o Nome!")
	@Column(name = "nome")
	public String nome;

	@NotNull(message = "Não é permitido Data de Nascimento Vazia!")
	@Past(message = "Não é permitido Data de Nascimento no futuro!")
	@Column(name = "datanascimento")
	public LocalDate dataNascimento;

	@NotBlank(message = "É necessário informar o CPF")
	@CPF(message = "CPF Inválido! Verifique!")
	@Column(name = "cpf", unique = true, nullable = false)
	public String cpf;
	
	@NotBlank(message = "É Necessário Informar o Telefone!")
	@Column(name = "telefone", length = 11)
	public String telefone;
	
	//@Formula("(select (current_date - dataNascimento) / 365)")
	@Formula("(select replace(replace(replace(replace(replace(replace(cast(age(datanascimento) as varchar), 'years', 'Anos'), 'year', 'Ano'), 'mons', 'Meses'), 'mon', 'Mês'), 'days', 'Dias'), 'day', 'Dia'))")
	public String idade;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "pessoa")
	public List<Endereco> enderecos = new ArrayList<Endereco>();
		
	public Pessoa() {
		
	}
		
	public Pessoa(Integer id, String nome, LocalDate dataNascimento, String cpf, String telefone, String idade) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.cpf = cpf;
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
	public LocalDate getdataNascimento() {
		return dataNascimento;
	}
	public void setdataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
	 	return FormataTelefone.format(telefone);
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

}
