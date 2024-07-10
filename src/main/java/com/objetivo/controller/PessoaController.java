package com.objetivo.controller;

import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Pessoa;
import com.objetivo.service.PessoaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

	private final PessoaService pessoaService;
	
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
	
	@GetMapping
	public ResponseEntity<Page<Pessoa>> findAllPaginada (
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,			
			Pageable pageable) {
		
		return ResponseEntity.ok(this.pessoaService.findByIdCpfNomeContaining(cpf, nome, pageable));
	}

	@GetMapping("/count")
	public Long quantidadePessoas() {
		return pessoaService.count();
	}

	@GetMapping("/{id}")
	public Pessoa obterPessoaPorId(@PathVariable Long id) {
		return pessoaService.obterPessoaPorId(id);
	}

	@PostMapping
	public Pessoa novaPessoa(@RequestBody @Valid PessoaDTO pessoa)  {
    	return pessoaService.createPessoa(pessoa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> alterarPessoa(
			@PathVariable("id") Long id,
			@RequestBody PessoaDTO pessoa) {
		return ResponseEntity.ok(this.pessoaService.editPessoa(id, pessoa));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
		this.pessoaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
