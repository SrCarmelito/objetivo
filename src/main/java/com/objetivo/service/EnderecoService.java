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
        endereco.setPessoa(pessoaService.obterPessoaPorId(pessoa));
        return enderecoRepository.saveAndFlush(enderecoDTOConverter.from(endereco));
    }

    @Transactional
    public Endereco save(Long id, Endereco endereco) {
        enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereço não Encontrado"));

        endereco.setPessoa(enderecoRepository.findPessoaByIdEndereco(endereco.getId()));

        return enderecoRepository.save(endereco);
    }

    @Transactional
    public void deleteById(Long id) {
        this.enderecoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Endereco não Encontrado"));
        this.enderecoRepository.deleteById(id);
    }
}
