package com.objetivo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "endereco")
@Table(schema = "elo", name = "endereco")
@AllArgsConstructor
@Data
@Builder()
@NoArgsConstructor
public class Endereco {

	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_endereco")
	@SequenceGenerator(name = "seq_endereco", schema = "elo", sequenceName = "s_endereco", allocationSize=1)
	private Long id;

	@NotBlank(message = "É Necessário informar o CEP!")
	@Size(min = 1, max = 10, message = "Deve ser entre 1 e 10 caracteres")
	private String cep;
	
	@NotBlank(message = "É Necessário informar o Logradouro!")
	@Size(min = 1, max = 150, message = "Deve ser entre 1 e 150 caracteres")
	private String logradouro;
	
	@NotBlank(message = "É Necessário informar o Número!")
	@Size(min = 1, max = 10, message = "Deve ser entre 1 e 10 caracteres")
	private String numero;
	
	@NotBlank(message = "É Necessário informar a Cidade!")
	@Size(min = 1, max = 100, message = "Deve ser entre 1 e 100 caracteres")
	private String cidade;
	
	@NotBlank(message = "É Necessário informar o UF!")
	@Size(min = 1, max = 2, message = "Deve ser entre 1 e 2 caracteres")
	private String uf;
	
	@NotBlank(message = "É Necessário informar o Bairro!")
	@Size(min = 1, max = 100, message = "Deve ser entre 1 e 100 caracteres")
	private String bairro;

	@JsonIgnore
	@NotNull(message = "É Necessário informar ID da Pessoa!")
	@ManyToOne
	@JoinColumn(name = "pessoa_id", nullable = false)
	private Pessoa pessoa;

}
