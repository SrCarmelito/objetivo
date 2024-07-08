package com.objetivo.service;

import com.objetivo.converter.PessoaDTOConverter;
import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import com.objetivo.utils.FormataTelefone;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PessoaDTOConverter pessoaDTOConverter;

    public PessoaService(PessoaRepository pessoaRepository, PessoaDTOConverter pessoaDTOConverter) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaDTOConverter = pessoaDTOConverter;
    }

    public Long count() {
        return this.pessoaRepository.count();
    }

    public Pessoa obterPessoaPorId(Long id) {
        return this.pessoaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Id Informado não existe na base de dados!"));
    }

    public Page<Pessoa> findByIdCpfNomeContaining(String cpf, String nome, Pageable pageable) {
        Page<Pessoa> pessoaPage = this.pessoaRepository.findByCpfContainingAndNomeIgnoreCaseContaining(cpf, nome, pageable);
        pessoaPage.forEach(p -> p.setTelefone(FormataTelefone.format(p.getTelefone())));
        return pessoaPage;
    }

    @Transactional
    public Pessoa createPessoa(PessoaDTO pessoa) {
        if (!pessoaRepository.findByCpfContainingAndNomeIgnoreCaseContaining(pessoa.getCpf(), "", Pageable.ofSize(1)).isEmpty())
            throw new IllegalArgumentException("CPF informado já existe no cadastro");

        return pessoaRepository.saveAndFlush(pessoaDTOConverter.from(pessoa, new Pessoa()));
    }

    @Transactional
    public Pessoa editPessoa(Long id, PessoaDTO pessoa) {
        Pessoa pessoAlterada = pessoaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
        return pessoaRepository.save(pessoaDTOConverter.from(pessoa, pessoAlterada));
    }

    @Transactional
    public void deleteById(Long id) {
        this.pessoaRepository.deleteById(id);
    }
}
