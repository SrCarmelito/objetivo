package com.objetivo.repository;

import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Page<Pessoa> findByCpfContainingAndNomeIgnoreCaseContaining(@Param("cpf") String cpf, @Param("nome") String nome, Pageable pageable);

	@Query(nativeQuery = true, value = "SELECT pessoa_id, cep, logradouro, numero, cidade, bairro, uf from elo.endereco where pessoa_id = 541;")
	List<String> tigas(); //TODO não pode ser desse jeito

}
