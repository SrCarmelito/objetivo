package com.objetivo.controller;

import java.util.List;
import java.util.Optional;

import com.objetivo.service.EnderecoService;
import com.objetivo.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping("/{id}")
	public Endereco obterEnderecoPorId(@PathVariable Long id) {
		return enderecoService.findById(id);
	}

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping("/end/{id}")
	public Long pessoaPorEnderecoId(@PathVariable Long id) {
		return this.enderecoService.findPessoaByEndereco(id);
	}

	@Transactional()
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/{id}")
	public ResponseEntity<Endereco> alteraEndereco(
			@PathVariable("id") Long id,
			@RequestBody Endereco endereco
			) {
		return new ResponseEntity<>(this.enderecoService.save(id, endereco), HttpStatus.OK);
	}
	
	@CrossOrigin(allowedHeaders = "*")
	@PostMapping("/{pessoa}")
	public ResponseEntity<Endereco> novoEndereco(
			@PathVariable("pessoa") Long pessoa,
			@RequestBody Endereco endereco) {
		return ResponseEntity.ok(this.enderecoService.novoEndereco(pessoa, endereco));
	}

	@CrossOrigin(allowedHeaders = "*")
	@GetMapping("/cep/{cep}")
	public EnderecoJson buscaPorCep(@PathVariable String cep) {
		return enderecoService.buscaCepRest(cep);
	}

	@Transactional
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
		this.enderecoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
