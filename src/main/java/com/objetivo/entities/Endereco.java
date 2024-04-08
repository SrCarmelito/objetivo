package com.objetivo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "endereco")
@Table(schema = "elo", name = "endereco")
public class Endereco {
	
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
	@SequenceGenerator(name = "seq_endereco", schema = "elo", sequenceName = "s_endereco", allocationSize=1)
	public Integer id;
	
	@JsonIgnore
	@NotNull(message = "É Necessário informar ID da Pessoa!")
	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false)
	public Pessoa pessoa;
		
	@NotBlank(message = "É Necessário informar o CEP!")
	@Column(name = "cep")
	public String cep;
	
	@NotBlank(message = "É Necessário informar o Logradouro!")
	@Column(name = "logradouro")
	public String logradouro;
	
	@NotBlank(message = "É Necessário informar o Número!")
	@Column(name = "numero")
	public String numero;
	
	@NotBlank(message = "É Necessário informar a Cidade!")
	@Column(name = "cidade")
	public String cidade;
	
	@NotBlank(message = "É Necessário informar o UF!")
	@Column(name = "uf")
	public String uf;
	
	@NotBlank(message = "É Necessário informar o Bairro!")
	@Column(name = "bairro")
	public String bairro;
	
	public Endereco() {
	
	}
	
	public Endereco(Integer id, @NotNull(message = "É Necessário informar ID da Pessoa!") Pessoa pessoa,
			@NotBlank(message = "É Necessário informar o CEP!") String cep,
			@NotBlank(message = "É Necessário informar o Logradouro!") String logradouro,
			@NotBlank(message = "É Necessário informar o Número!") String numero,
			@NotBlank(message = "É Necessário informar a Cidade!") String cidade,
			@NotBlank(message = "É Necessário informar o UF!") String uf,
			@NotBlank(message = "É Necessário informar o Bairro!") String bairro) {
		super();
		this.id = id;
		this.pessoa = pessoa;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cidade = cidade;
		this.uf = uf;
		this.bairro = bairro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
		
}
