package com.objetivo.controller;

import java.util.Optional;

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

import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;

@RestController
@RequestMapping(value = "/pessoas")
@Slf4j
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	// pesquisa paginada nos parâmetros da url
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping
	public ResponseEntity<Page<Pessoa>> findAllPaginada (
			@RequestParam(value = "id", required = false, defaultValue = "") String id,
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
			@RequestParam(value = "nome", required = false, defaultValue = "") String nome,			
			Pageable pageable) throws Exception{
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.pessoaRepository.findByCpfNome(id, cpf, nome, pageable));
	};
	
	// pesquisa quantidade de pessoas
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/count")
	public Long quantidadePessoas() {
		return pessoaRepository.count();
	};
	
	// pesquisa por id específico
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/{id}")
	public Optional<Pessoa> obterPessoaPorId(@PathVariable Integer id) {
		return pessoaRepository.findById(id);
	};
		
	// insere uma nova pessoa // 
	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PostMapping
	public @ResponseBody Pessoa novaPessoa(@RequestBody @Validated Pessoa pessoa) throws Exception {
		Pessoa novaPessoa = pessoa;
					
		if (!pessoaRepository.findByCpfContaining(pessoa.cpf).isEmpty()){
			throw new Exception("CPF informado já existe no cadastro");
		} else {
			novaPessoa.setCpf(pessoa.cpf);
			novaPessoa.setNome(pessoa.nome);
			novaPessoa.setdataNascimento(pessoa.dataNascimento);
			novaPessoa.setTelefone(pessoa.telefone);
			return pessoaRepository.save(novaPessoa);
		}
	
	};

	//atualiza a pessoa // 
	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping(path = "/{id}")
	public Pessoa alterarPessoa(
			@PathVariable("id") Integer id, 
			@RequestBody @Validated Pessoa pessoa) throws Exception {		
		Pessoa pessoaAlterada = pessoaRepository.findById(id)
				.orElseThrow(() -> new Exception("Registro não encontrado."));
		
		if (pessoaRepository.findByCpfContaining(pessoa.cpf).size() > 1){
			throw new Exception("CPF informado já existe no cadastro");
		} else {
			pessoaAlterada.setNome(pessoa.nome);
			pessoaAlterada.setCpf(pessoa.cpf);
			pessoaAlterada.setTelefone(pessoa.telefone);
			pessoaAlterada.setdataNascimento(pessoa.dataNascimento);
			return pessoaRepository.save(pessoaAlterada);
		}
	};
	
	// deleta uma pessoa pelo id
	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping(path = "/{id}")
	public void excluirPessoa(@PathVariable int id) {
		pessoaRepository.deleteById(id);
	};
				
}
