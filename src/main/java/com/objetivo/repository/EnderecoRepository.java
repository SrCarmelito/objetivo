package com.objetivo.repository;

import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository  extends JpaRepository<Endereco, Long>{

    @Query(value = "select e.pessoa from com.objetivo.entities.Endereco e WHERE e.Id = :id")
	Pessoa findPessoaByIdEndereco(@Param("id") Long id);

}
