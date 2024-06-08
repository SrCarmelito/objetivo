package com.objetivo.repository;

import com.objetivo.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Page<Pessoa> findByIdOrCpfOrNome(@Param("id") Long id, @Param("cpf") String cpf, @Param("nome") String nome, Pageable pageable);

	List<Pessoa> findByCpfContaining(@Param("cpf") String cpf);

}
