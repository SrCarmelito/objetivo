package com.objetivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.objetivo.entities.Endereco;

@Repository
public interface EnderecoRepository  extends JpaRepository<Endereco, Integer>{
	
	@Query(nativeQuery = true, value = "select e.pessoa_id from elo.endereco e where e.id = :id")
	Integer findPessoaByEndereco(@Param("id") Integer id);
	
	@Query(nativeQuery = true, value = "select max(p.id) from elo.pessoa p")
	Integer findMaxPessoa();

}
