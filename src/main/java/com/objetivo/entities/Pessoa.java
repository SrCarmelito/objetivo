package com.objetivo.entities;

import com.objetivo.audit.AuditInfo;
import com.objetivo.audit.AuditListener;
import com.objetivo.audit.Auditable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "elo", name = "pessoa")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder()
@Audited
@EntityListeners(AuditListener.class)
public class Pessoa implements Auditable {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pessoa")
	@SequenceGenerator(name = "seq_pessoa", schema = "elo", sequenceName = "s_pessoa", allocationSize=1)
	private Long id;
		
	@NotBlank(message = "É Necessário informar o Nome!")
	private String nome;

	@NotNull(message = "Não é permitido Data de Nascimento Vazia!")
	@Past(message = "Não é permitido Data de Nascimento no futuro!")
	@Column(name = "datanascimento")
	private LocalDate dataNascimento;

	@NotBlank(message = "É necessário informar o CPF")
	@CPF(message = "CPF Inválido! Verifique!")
	private String cpf;
	
	@NotBlank(message = "É Necessário Informar o Telefone!")
	@Size(min = 10, max = 11, message = "Deve ser entre 10 e 11 caracteres com DDD")
	private String telefone;

	@Transient
	private BigDecimal idade;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "pessoa")
	private List<Endereco> enderecos = new ArrayList<>();

	@Embedded
	@Audited
	private AuditInfo audit = AuditInfo.now();

	public BigDecimal getIdade() {
		return BigDecimal.valueOf(LocalDate.now().getYear()).subtract(new BigDecimal(getDataNascimento().getYear()));
	}

}
