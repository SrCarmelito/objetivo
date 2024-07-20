package com.objetivo.controller;

import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.service.EnderecoService;
import com.objetivo.utils.pesquisaporcep.EnderecoJson;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

	@GetMapping("/{id}")
	public Endereco obterEnderecoPorId(@PathVariable Long id) {
		return enderecoService.findById(id);
	}

	@PostMapping("/{pessoa}")
	public ResponseEntity<Endereco> novoEndereco(
			@PathVariable("pessoa") Long pessoa,
			@RequestBody @Valid EnderecoDTO endereco) {
		return ResponseEntity.ok(this.enderecoService.novoEndereco(pessoa, endereco));
	}

	@GetMapping("/end/{id}")
	public Long pessoaPorEnderecoId(@PathVariable Long id) {
		return this.enderecoService.findPessoaByEndereco(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Endereco> alteraEndereco(
			@PathVariable("id") Long id,
			@RequestBody EnderecoDTO endereco
			) {
		return new ResponseEntity<>(this.enderecoService.save(id, endereco), HttpStatus.OK);
	}

	@GetMapping("/cep/{cep}")
	public EnderecoJson buscaPorCep(@PathVariable String cep) {
		return enderecoService.buscaCepRest(cep);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEndereco(@PathVariable Long id) {
		this.enderecoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
