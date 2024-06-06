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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Endereco {

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
	@SequenceGenerator(name = "seq_endereco", schema = "elo", sequenceName = "s_endereco", allocationSize=1)
	private Long id;
	
	@JsonIgnore
	@NotNull(message = "É Necessário informar ID da Pessoa!")
	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false)
	private Pessoa pessoa;
		
	@NotBlank(message = "É Necessário informar o CEP!")
	@Column(name = "cep")
	private String cep;
	
	@NotBlank(message = "É Necessário informar o Logradouro!")
	@Column(name = "logradouro")
	private String logradouro;
	
	@NotBlank(message = "É Necessário informar o Número!")
	@Column(name = "numero")
	private String numero;
	
	@NotBlank(message = "É Necessário informar a Cidade!")
	@Column(name = "cidade")
	private String cidade;
	
	@NotBlank(message = "É Necessário informar o UF!")
	@Column(name = "uf")
	private String uf;
	
	@NotBlank(message = "É Necessário informar o Bairro!")
	@Column(name = "bairro")
	private String bairro;

}
