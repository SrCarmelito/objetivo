package com.objetivo.repository;

import com.objetivo.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	@Query(nativeQuery = true, value = "select p.id, p.nome, p.datanascimento, p.cpf, p.telefone, "
			+ "(select (current_date - p.dataNascimento) / 365) as idade"
			+ " from elo.pessoa p where (p.cpf ilike %:cpf% and p.nome ilike %:nome%"
			+ " and cast(p.id as varchar) like %:id%)")
	Page<Pessoa> findByIdCpfNomeContaining(String cpf, String nome, String id, Pageable pageable);

	List<Pessoa> findByCpfContaining(@Param("cpf") String cpf);

}
