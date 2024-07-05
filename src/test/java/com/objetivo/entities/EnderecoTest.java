package com.objetivo.entities;

import com.objetivo.fixtures.EnderecoFixtures;
import com.objetivo.fixtures.PessoaFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnderecoTest {

    @Test
    public void enderecoCadastrado() {
        Endereco endereco = EnderecoFixtures.endereco();
        Pessoa pessoa = PessoaFixtures.pessoaLinusSemEndereco();
        endereco.setPessoa(pessoa);
        assertEquals(365L, endereco.getPessoa().getId());
        assertEquals(730L, endereco.getId());
        assertEquals("75830112", endereco.getCep());
        assertEquals("Rua das manções de Las Vegas", endereco.getLogradouro());
        assertEquals("6930", endereco.getNumero());
        assertEquals("HollyWood", endereco.getCidade());
        assertEquals("SP", endereco.getUf());
        assertEquals("Centro", endereco.getBairro());
        assertEquals(pessoa, endereco.getPessoa());
    }
}
