package com.objetivo.converter;

import com.objetivo.dto.EnderecoDTO;
import com.objetivo.dto.PessoaDTO;
import com.objetivo.entities.Endereco;
import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaDTOFixtures;
import com.objetivo.fixtures.PessoaFixtures;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PessoaDTOConverterTest {

    @Autowired
    PessoaDTOConverter pessoaDTOConverter;

    @Test
    public void from() {
        Pessoa entity = pessoaDTOConverter.from(PessoaDTOFixtures.pessoaJimiHendrix(), new Pessoa());
        assertTrue(entity.getNome().equals("Jimi Hendrix"));
        assertTrue(entity.getCpf().equals("123456789"));
        assertTrue(entity.getId().equals(71L));

        Assertions.assertThat(entity.getEnderecos())
                .extracting(Endereco::getCidade)
                .contains("Manhattan");
        Assertions.assertThat(entity.getEnderecos())
                .extracting(Endereco::getUf)
                .contains("RJ");
    }

    @Test
    public void to() {
        PessoaDTO dto = pessoaDTOConverter.to(PessoaFixtures.pessoaLinusTorvalds());
        assertTrue(dto.getNome().equals("Linus Torvalds"));
        assertTrue(dto.getCpf().equals("06455083903"));
        assertTrue(dto.getId().equals(365L));

        Assertions.assertThat(dto.getEnderecos())
                .extracting(EnderecoDTO::getCidade)
                .contains("HollyWood");
        Assertions.assertThat(dto.getEnderecos())
                .extracting(EnderecoDTO::getUf)
                .contains("SP");
    }
}
