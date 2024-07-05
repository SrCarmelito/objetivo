package com.objetivo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/pessoa.sql",
        "/sql/endereco.sql"
})
public class EnderecoRepositoryTest {

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
