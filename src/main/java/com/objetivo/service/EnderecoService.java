package com.objetivo.service;

import com.objetivo.converter.EnderecoDTOConverter;
import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.repository.EnderecoRepository;
import com.objetivo.utils.pesquisaporcep.ApiCep;
import com.objetivo.utils.pesquisaporcep.EnderecoJson;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final PessoaService pessoaService;

    private final EnderecoDTOConverter enderecoDTOConverter;

    public EnderecoService(EnderecoRepository enderecoRepository, PessoaService pessoaService, EnderecoDTOConverter enderecoDTOConverter) {
        this.enderecoRepository = enderecoRepository;
        this.pessoaService = pessoaService;
        this.enderecoDTOConverter = enderecoDTOConverter;
    }

    public EnderecoJson buscaCepRest(String cep) {
        return ApiCep.buscaCepRest(cep);
    }

    public Endereco findById(Long id) {
        return this.enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereco não Encontrado"));
    }

    public Long findPessoaByEndereco(Long id) {
        return this.enderecoRepository.findPessoaByIdEndereco(id).getId();
    }

    @Transactional
    public Endereco novoEndereco(Long pessoa, EnderecoDTO endereco) {
        Endereco novoEndereco = new Endereco();
        novoEndereco.setPessoa(pessoaService.obterPessoaPorId(pessoa));
        return enderecoRepository.saveAndFlush(enderecoDTOConverter.from(endereco, novoEndereco));
    }

    @Transactional
    public Endereco save(Long id, EnderecoDTO endereco) {
        Endereco enderecoAlterado = enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereço não Encontrado"));

        return enderecoRepository.save(enderecoDTOConverter.from(endereco, enderecoAlterado));
    }

    @Transactional
    public void deleteById(Long id) {
        this.enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereco não Encontrado"));
        this.enderecoRepository.deleteById(id);
    }
}
