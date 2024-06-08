package com.objetivo.service;

import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import com.objetivo.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PessoaService pessoaService;

    public Endereco novoEndereco(Long pessoa, Endereco endereco) {
        Pessoa pessoaEndereco = pessoaService.obterPessoaPorId(pessoa);
        endereco.setCep(endereco.getCep());
        endereco.setLogradouro(endereco.getLogradouro());
        endereco.setNumero(endereco.getNumero());
        endereco.setCidade(endereco.getCidade());
        endereco.setUf(endereco.getUf());
        endereco.setBairro(endereco.getBairro());
        endereco.setPessoa(pessoaEndereco);
        return enderecoRepository.saveAndFlush(endereco);
    }

    public Endereco save(Long id, Endereco endereco) {
        Endereco enderecoAlterado = enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereço não Encontrado"));

        Pessoa pessoaEndereco = pessoaService.obterPessoaPorId(enderecoRepository.findPessoaByEndereco(id));

        enderecoAlterado.setCep(endereco.getCep());
        enderecoAlterado.setLogradouro(endereco.getLogradouro());
        enderecoAlterado.setNumero(endereco.getNumero());
        enderecoAlterado.setCidade(endereco.getCidade());
        enderecoAlterado.setUf(endereco.getUf());
        enderecoAlterado.setBairro(endereco.getBairro());
        enderecoAlterado.setPessoa(pessoaEndereco);

        return enderecoRepository.save(enderecoAlterado);
    }

    public Endereco findById(Long id) {
        return this.enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereco não Encontrado"));
    }

    public Long findPessoaByEndereco(Long id) {
        return this.enderecoRepository.findPessoaByEndereco(id);
    }

    public void deleteById(Long id) {
        this.enderecoRepository.deleteById(id);
    }
}