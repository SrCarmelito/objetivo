package com.objetivo.service;

import com.objetivo.entities.Pessoa;
import com.objetivo.repository.PessoaRepository;
import com.objetivo.utils.FormataTelefone;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa createPessoa(Pessoa pessoa) {
        if (!pessoaRepository.findByCpfContainingAndNomeIgnoreCaseContaining(pessoa.getCpf(), "", Pageable.ofSize(1)).isEmpty())
            throw new IllegalArgumentException("CPF informado já existe no cadastro");

        pessoa.setCpf(pessoa.getCpf());
        pessoa.setNome(pessoa.getNome());
        pessoa.setDataNascimento(pessoa.getDataNascimento());
        pessoa.setTelefone(pessoa.getTelefone());
        return this.pessoaRepository.saveAndFlush(pessoa);
    }

    public Pessoa editPessoa(Long id, Pessoa pessoa) {
        Pessoa pessoaAlterada = this.pessoaRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Pessoa não encontrada"));

        pessoaAlterada.setNome(pessoa.getNome());
        pessoaAlterada.setCpf(pessoa.getCpf());
        pessoaAlterada.setTelefone(pessoa.getTelefone());
        pessoaAlterada.setDataNascimento(pessoa.getDataNascimento());
        return pessoaRepository.save(pessoaAlterada);
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

    public Long count() {
        return this.pessoaRepository.count();
    }

    public void deleteById(Long id) {
        this.pessoaRepository.deleteById(id);
    }
}
