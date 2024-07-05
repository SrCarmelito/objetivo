package com.objetivo.utils;

import com.objetivo.utils.pesquisaporcep.ApiCep;
import com.objetivo.utils.pesquisaporcep.EnderecoJson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApiCepTest {

    @Test
    public void buscaCep() {
        EnderecoJson cepSearch = ApiCep.buscaCepRest("41950970");
        assertEquals("41950-970", cepSearch.getCep());
        assertEquals("Rua João Gomes", cepSearch.getLogradouro());
        assertEquals("Rio Vermelho", cepSearch.getBairro());
        assertEquals("Salvador", cepSearch.getLocalidade());
        assertEquals("BA", cepSearch.getUf());
    }

    @Test
    public void buscaCepComErro() {
        assertThrows(HttpClientErrorException.class, () -> ApiCep.buscaCepRest("1234"));
    }
}
