package com.objetivo.entities;

import com.objetivo.fixtures.EnderecoFixtures;
import com.objetivo.fixtures.PessoaFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PessoaTest {

    @Test
    public void pessoaCadastrada() {

        Pessoa pessoa = new Pessoa();
        pessoa = PessoaFixtures.pessoaLinusTorvalds();
        assertEquals(365L, pessoa.getId());
        assertSame("Linus Torvalds", pessoa.getNome());
        assertEquals(LocalDate.of(1969, 12, 28), pessoa.getDataNascimento());
        assertSame("06455083903", pessoa.getCpf());
        assertEquals("6189745612", pessoa.getTelefone());

        Endereco endereco = EnderecoFixtures.endereco();
        endereco.setPessoa(pessoa);
        pessoa.setEnderecos(List.of(endereco));

        assertEquals(365L, endereco.getPessoa().getId());
        assertEquals("06455083903", pessoa.getEnderecos().stream().findFirst().orElseThrow().getPessoa().getCpf());

        Assertions.assertThat(pessoa.getEnderecos())
                .extracting(end -> end.getPessoa().getCpf())
                .containsExactlyInAnyOrder("06455083903");

    }

    @Test
    public void getIdade() {
        Pessoa pessoa = new Pessoa();
        pessoa.setDataNascimento(LocalDate.of(1930, 8, 14));
        assertEquals(
                BigDecimal.valueOf(LocalDate.now().getYear()).subtract(new BigDecimal(pessoa.getDataNascimento().getYear())),
                pessoa.getIdade());
    }

}
