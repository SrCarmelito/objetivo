package com.objetivo.repository;

import com.objetivo.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.objetivo.entities.Endereco;

import java.util.List;
import java.util.Locale;

@Repository
public interface EnderecoRepository  extends JpaRepository<Endereco, Long>{
	
	@Query(nativeQuery = true, value = "select e.pessoa_id from elo.endereco e where e.id = :id")
	Long findPessoaByEndereco(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "select max(p.id) from elo.pessoa p")
	Integer findMaxPessoa();

}
