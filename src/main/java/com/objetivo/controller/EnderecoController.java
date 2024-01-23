package com.objetivo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.objetivo.entities.Endereco;
import com.objetivo.repository.EnderecoRepository;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/{page}/{size}")
	public ResponseEntity<Page<Endereco>> findAll(
			@PathVariable(value = "page") Integer page,
			@PathVariable(value = "size") Integer size
			) {
		
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Endereco> list = enderecoRepository.findAll(pageRequest);
		return ResponseEntity.ok(list);
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping
	public List<Endereco> findAll() {
		return enderecoRepository.findAll();
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/{id}")
	public Optional<Endereco> obterPessoaPorId(@PathVariable Integer id) {
		return enderecoRepository.findById(id);
	}
	
}
