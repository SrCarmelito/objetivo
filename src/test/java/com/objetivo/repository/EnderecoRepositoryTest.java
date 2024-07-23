package com.objetivo.repository;

import com.objetivo.DefaultTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EnderecoRepositoryTest extends DefaultTest {

    @Autowired
    public EnderecoRepository enderecoRepository;

    @Test
    void findPessoaByIdEndereco() {
        assertEquals("Jon Yang", enderecoRepository.findPessoaByIdEndereco(3L).getNome());
        assertEquals(1, enderecoRepository.findPessoaByIdEndereco(3L).getId());

        assertEquals("Lenny Kravitz", enderecoRepository.findPessoaByIdEndereco(2L).getNome());
        assertEquals(2, enderecoRepository.findPessoaByIdEndereco(2L).getId());
    }

}
