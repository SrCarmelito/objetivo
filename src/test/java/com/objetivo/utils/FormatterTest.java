package com.objetivo.utils;

import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatterTest {

    @Test
    public void formataTelefoneDezDigitos() {
        Pessoa pessoa = PessoaFixtures.pessoaLinusTorvalds();
        pessoa.setTelefone(Formatter.telephoneMask(pessoa.getTelefone()));
        assertEquals("(61) 8974-5612", pessoa.getTelefone());
    }

    @Test
    public void formataTelefoneOnzeDigitos() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        pessoa.setTelefone(Formatter.telephoneMask(pessoa.getTelefone()));
        assertEquals("(44) 98808-0437", pessoa.getTelefone());
    }

    @Test
    public void formataTelefonedigitadoIncorretamente() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        pessoa.setTelefone(Formatter.telephoneMask("1234"));
        assertEquals("1234     ", pessoa.getTelefone());
    }

    @Test
    public void formataTelefoneComErro() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        assertThrows(IllegalArgumentException.class, () -> pessoa.setTelefone(Formatter.telephoneMask("jkÁ34")));
    }

    @Test
    public void formataCpf() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        pessoa.setCpf(Formatter.cpfCnpjMask(pessoa.getCpf()));
        assertEquals("625.107.284-90", pessoa.getCpf());
    }

    @Test
    public void formataCnpj() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        pessoa.setCpf(Formatter.cpfCnpjMask("70487085000160"));
        assertEquals("70.487.085/0001-60", pessoa.getCpf());
    }

    @Test
    public void formatacpfCnpjComErro() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        assertThrows(IllegalArgumentException.class, () -> pessoa.setCpf(Formatter.cpfCnpjMask("jkÁ34")));
    }

    @Test
    public void formataCep() {
        Pessoa pessoa = PessoaFixtures.pessoaLinusTorvalds();
        pessoa.getEnderecos().forEach(endereco -> endereco.setCep(Formatter.cepMask(endereco.getCep())));

        assertEquals("75830-112", pessoa.getEnderecos().stream().findFirst().orElseThrow().getCep());
    }

    @Test
    public void formataCepDiferentede8() {
        Pessoa pessoa = PessoaFixtures.pessoaLinusTorvalds();
        pessoa.getEnderecos().forEach(endereco -> endereco.setCep(Formatter.cepMask("123456")));

        assertEquals("123456  ", pessoa.getEnderecos().stream().findFirst().orElseThrow().getCep());
    }

    @Test
    public void formataCepComErro() {
        Pessoa pessoa = PessoaFixtures.pessoaLinusTorvalds();
        assertThrows(IllegalArgumentException.class, () -> pessoa.getEnderecos().stream().findFirst().orElseThrow().setCep(Formatter.cepMask("1*ó:w")));
    }

}
