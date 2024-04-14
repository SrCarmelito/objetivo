package com.objetivo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.objetivo.entities.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

	public List<Pessoa> findAllByOrderByIdAsc();
	
	@Query(nativeQuery = true, value = "select p.id, p.nome, p.datanascimento, p.cpf, p.telefone, "
			+ "(replace(replace(replace(replace(replace(replace(cast(age(datanascimento) as varchar), 'years', 'Anos'), 'year', 'Ano'), 'mons', 'Meses'), 'mon', 'Mês'), 'days', 'Dias'), 'day', 'Dia')) as idade"
			+ " from elo.Pessoa p where p.cpf ilike %:cpf% and p.nome ilike %:nome%"
			+ " and cast(p.id as varchar) like %:id%")
	Page<Pessoa> findByCpfNome(String id, String cpf, String nome, Pageable pageable);
//
//	@Query(nativeQuery = true, value = "select p.id, p.nome, p.datanascimento, p.cpf, p.telefone, "
//			+ "(select (current_date - p.dataNascimento) / 365) as idade"
//			+ " from elo.pessoa p where p.cpf ilike %:cpf%")
	List<Pessoa> findByCpfContaining(@Param("cpf") String cpf);	
	
}
