package com.objetivo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import com.objetivo.repository.EnderecoRepository;
import com.objetivo.repository.PessoaRepository;
import com.objetivo.utils.pesquisaporcep.ApiCep;
import com.objetivo.utils.pesquisaporcep.EnderecoJson;

@RestController
@RequestMapping(value = "/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	ApiCep apiCep = new ApiCep();
	
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
	public Optional<Endereco> obterPessoaPorId(@PathVariable Long id) {
		return enderecoRepository.findById(id);
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/end/{id}")
	public Long pessoa(@PathVariable Long id) {
		return enderecoRepository.findPessoaByEndereco(id);
	}
		
	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping(path = "/{id}")
	public Endereco enderecoAlterado(
			@PathVariable("id") Long id,
			@RequestBody  Endereco endereco
			) {
		Endereco enderecoAlterado = enderecoRepository.findById(id).orElseThrow();
		Pessoa pessoaEndereco = pessoaRepository.findById(enderecoRepository.findPessoaByEndereco(id)).orElseThrow();
		enderecoAlterado.setCep(endereco.getCep());
		enderecoAlterado.setLogradouro(endereco.getLogradouro());
		enderecoAlterado.setNumero(endereco.getNumero());
		enderecoAlterado.setCidade(endereco.getCidade());
		enderecoAlterado.setUf(endereco.getUf());
		enderecoAlterado.setBairro(endereco.getBairro());
		enderecoAlterado.setPessoa(pessoaEndereco);
		return enderecoRepository.save(enderecoAlterado);
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@PostMapping(path = "/{pessoa}")
	public @ResponseBody Endereco novoEndereco(
			@PathVariable("pessoa") Long pessoa,
			@RequestBody Endereco endereco) {
		Pessoa pessoaEndereco = pessoaRepository.findById(pessoa).orElseThrow();
		Endereco novoEndereco = endereco;
		novoEndereco.setCep(endereco.getCep());
		novoEndereco.setLogradouro(endereco.getLogradouro());
		novoEndereco.setNumero(endereco.getNumero());
		novoEndereco.setCidade(endereco.getCidade());
		novoEndereco.setUf(endereco.getUf());
		novoEndereco.setBairro(endereco.getBairro());
		novoEndereco.setPessoa(pessoaEndereco);
		return enderecoRepository.save(novoEndereco);
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/max-pessoa")
	public Integer maxPessoa() {
		return enderecoRepository.findMaxPessoa();
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@GetMapping(path = "/cep/{cep}")
	public @ResponseBody EnderecoJson buscaPorCep(@PathVariable String cep) throws Exception {
		return ApiCep.buscaCep(cep);
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping(path = "/{id}")
	public void deleteAdress(@PathVariable Long id) {
		enderecoRepository.deleteById(id);
	}
	
}
