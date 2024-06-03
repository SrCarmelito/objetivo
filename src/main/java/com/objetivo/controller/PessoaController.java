package com.objetivo.controller;

import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/pessoas")
@Slf4j
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping
	public ResponseEntity<Page<Pessoa>> findAllPaginada (
			@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,			
			Pageable pageable) {
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.pessoaRepository.findByIdCpfNomeContaining(id, cpf, nome, pageable));
	}

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/count")
	public Long quantidadePessoas() {
		return pessoaRepository.count();
	}

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/{id}")
	public Optional<Pessoa> obterPessoaPorId(@PathVariable Long id) {
		return pessoaRepository.findById(id);
	}
		

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PostMapping
	public @ResponseBody Pessoa novaPessoa(@RequestBody @Validated Pessoa pessoa)  {

        if (!pessoaRepository.findByCpfContaining(pessoa.getCpf()).isEmpty()){
			throw new IllegalArgumentException("CPF informado já existe no cadastro");
		} else {
			pessoa.setCpf(pessoa.getCpf());
			pessoa.setNome(pessoa.getNome());
			pessoa.setDataNascimento(pessoa.getDataNascimento());
			pessoa.setTelefone(pessoa.getTelefone());
			return pessoaRepository.save(pessoa);
		}
	
	}

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping(path = "/{id}")
	public Pessoa alterarPessoa(
			@PathVariable("id") Long id,
			@RequestBody @Validated Pessoa pessoa) {
		Pessoa pessoaAlterada = pessoaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Registro não encontrado."));
		
		if (pessoaRepository.findByCpfContaining(pessoa.getCpf()).size() > 1){
			throw new IllegalArgumentException("CPF informado já existe no cadastro");
		} else {
			pessoaAlterada.setNome(pessoa.getNome());
			pessoaAlterada.setCpf(pessoa.getCpf());
			pessoaAlterada.setTelefone(pessoa.getTelefone());
			pessoaAlterada.setDataNascimento(pessoa.getDataNascimento());
			return pessoaRepository.save(pessoaAlterada);
		}
	}

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping(path = "/{id}")
	public void excluirPessoa(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}

}
