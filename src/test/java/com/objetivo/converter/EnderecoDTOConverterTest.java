package com.objetivo.converter;

import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.fixtures.EnderecoDTOFixtures;
import com.objetivo.fixtures.EnderecoFixtures;
import com.objetivo.fixtures.PessoaDTOFixtures;
import com.objetivo.fixtures.PessoaFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class EnderecoDTOConverterTest {

    @Autowired
    EnderecoDTOConverter enderecoDTOConverter;

    @Test
    public void from() {
        Endereco enderecoEntity = new Endereco();
        enderecoEntity.setPessoa(PessoaFixtures.pessoaLinusTorvalds());
        Endereco entity = enderecoDTOConverter.from(EnderecoDTOFixtures.enderecoDTOManhattan(), enderecoEntity);
        assertTrue(entity.getCidade().equals("Manhattan"));
        assertTrue(entity.getPessoa().getNome().equals("Linus Torvalds"));
    }

    @Test
    public void to() {
        EnderecoDTO dto = enderecoDTOConverter.to(EnderecoFixtures.endereco());
        assertTrue(dto.getPessoaDTO().getNome().equals("James Gosling"));
        assertTrue(dto.getCidade().equalsIgnoreCase("hollywood"));
        assertTrue(dto.getBairro().equalsIgnoreCase("centro"));
    }
}
