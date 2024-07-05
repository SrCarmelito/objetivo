package com.objetivo.controller;

import com.objetivo.entities.Pessoa;
import com.objetivo.service.PessoaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
@Slf4j
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping
	public ResponseEntity<Page<Pessoa>> findAllPaginada (
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,			
			Pageable pageable) {
		
		return ResponseEntity.ok(this.pessoaService.findByIdCpfNomeContaining(cpf, nome, pageable));
	}

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping("/count")
	public Long quantidadePessoas() {
		return pessoaService.count();
	}

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping("/{id}")
	public Pessoa obterPessoaPorId(@PathVariable Long id) {
		return pessoaService.obterPessoaPorId(id);
	}

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PostMapping
	public Pessoa novaPessoa(@RequestBody @Valid Pessoa pessoa)  {
    	return pessoaService.createPessoa(pessoa);
	}

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> alterarPessoa(
			@PathVariable("id") Long id,
			@RequestBody @Valid Pessoa pessoa) {
		return ResponseEntity.ok(this.pessoaService.editPessoa(id, pessoa));
	}

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
		this.pessoaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
