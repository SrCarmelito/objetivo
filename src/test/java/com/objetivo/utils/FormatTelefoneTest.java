package com.objetivo.utils;

import com.objetivo.entities.Pessoa;
import com.objetivo.fixtures.PessoaFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormatTelefoneTest {

    @Test
    public void formataTelefoneDezDigitos() {
        Pessoa pessoa = PessoaFixtures.pessoaLinusTorvalds();
        pessoa.setTelefone(FormataTelefone.format(pessoa.getTelefone()));
        assertEquals("(61) 8974-5612", pessoa.getTelefone());
    }

    @Test
    public void formataTelefoneOnzeDigitos() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        pessoa.setTelefone(FormataTelefone.format(pessoa.getTelefone()));
        assertEquals("(44) 98808-0437", pessoa.getTelefone());
    }

    @Test
    public void formataTelefonedigitadoIncorretamente() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        pessoa.setTelefone(FormataTelefone.format("1234"));
        assertEquals("1234     ", pessoa.getTelefone());
    }

    @Test
    public void formataTelefoneComErro() {
        Pessoa pessoa = PessoaFixtures.pessoaJamesGosling();
        assertThrows(IllegalArgumentException.class, () -> pessoa.setTelefone(FormataTelefone.format("jkÁ34")));
    }

}
