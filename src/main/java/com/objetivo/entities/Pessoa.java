package com.objetivo.entities;

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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "pessoa")
@Table(schema = "elo", name = "pessoa")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pessoa {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
	@SequenceGenerator(name = "seq_pessoa", schema = "elo", sequenceName = "s_pessoa", allocationSize=1)
	private Long id;
		
	@NotBlank(message = "É Necessário informar o Nome!")
	@Column(name = "nome")
	private String nome;

	@NotNull(message = "Não é permitido Data de Nascimento Vazia!")
	@Past(message = "Não é permitido Data de Nascimento no futuro!")
	@Column(name = "datanascimento")
	private LocalDate dataNascimento;

	@NotBlank(message = "É necessário informar o CPF")
	@CPF(message = "CPF Inválido! Verifique!")
	@Column(name = "cpf", unique = true, nullable = false)
	private String cpf;
	
	@NotBlank(message = "É Necessário Informar o Telefone!")
	@Column(name = "telefone", length = 11)
	private String telefone;

	@Transient
	private BigDecimal idade;

	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "pessoa")
	private List<Endereco> enderecos = new ArrayList<>();

	public BigDecimal getIdade() {
		return BigDecimal.valueOf(LocalDate.now().getYear()).subtract(new BigDecimal(getDataNascimento().getYear()));
	}

	public String getTelefone() {
		return FormataTelefone.format(telefone);
	}
}
