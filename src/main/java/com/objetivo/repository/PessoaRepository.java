package com.objetivo.repository;

import com.objetivo.entities.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	Page<Pessoa> findByCpfContainingAndNomeIgnoreCaseContaining(@Param("cpf") String cpf, @Param("nome") String nome, Pageable pageable);

}
