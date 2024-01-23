package com.objetivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.objetivo.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
