package com.objetivo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import com.objetivo.utils.Validadores;

@RestController
@RequestMapping(value = "/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	// pesquisa paginada nos parâmetros da url
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/{page}/{size}")
	public ResponseEntity<Page<Pessoa>> findAllPaginada(
			@PathVariable(value = "page") Integer page,
			@PathVariable(value = "size") Integer size
			) {
		
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Pessoa> list = pessoaRepository.findAll(pageRequest);
		
		return ResponseEntity.ok(list);
	}
	
	// pesquida todas as pessoas 
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
		
	// pesquisa por id
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/{id}")
	public Optional<Pessoa> obterPessoaPorId(@PathVariable Integer id) {
		return pessoaRepository.findById(id);
	}
	
	// insere uma nova pessoa com as validações
	@CrossOrigin(allowedHeaders = "*")
	@PostMapping
	public @ResponseBody Pessoa novaPessoa(@Validated Pessoa pessoa) {
		if(Validadores.validador(pessoa) == true) {
			pessoaRepository.save(pessoa);
			return pessoa;
		} else {
			Validadores.mensagens(pessoa);
			return null;
		}
	}

	//atualiza a pessoa seleciona com as validações
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping(path = "/{id}")
	public Pessoa alterarPessoa(@Validated Pessoa pessoa) {
		if(Validadores.validador(pessoa) == true) {
			pessoaRepository.save(pessoa);
			return pessoa;
		} else {
			Validadores.mensagens(pessoa);
			return null;
		}	
	}
	
	// deleta uma pessoa pelo id
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping(path = "/{id}")
	public void excluirPessoa(@PathVariable int id) {
		pessoaRepository.deleteById(id);
	}
			
}
