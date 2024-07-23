package com.objetivo.converter;

import com.objetivo.DefaultTest;
import com.objetivo.dto.EnderecoDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.fixtures.EnderecoDTOFixtures;
import com.objetivo.fixtures.EnderecoFixtures;
import com.objetivo.fixtures.PessoaFixtures;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class EnderecoDTOConverterTest extends DefaultTest {

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
